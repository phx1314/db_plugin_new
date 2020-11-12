//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax;

import com.xfltr.hapax.parser.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public final class Template {
    private static final Logger logger_ = Logger.getLogger(Template.class.getSimpleName());
    private final List<TemplateNode> tmpl_;
    private TemplateLoader loader_ = new NullTemplateLoader();
    private TemplateLoaderContext context_;

    public static Template parse(String template) throws TemplateParserException {
        return parse(CTemplateParser.create(), template);
    }

    public static Template parse(TemplateParser parser, String template) throws TemplateParserException {
        List<TemplateNode> results = parser.parse(template);
        return new Template(results);
    }

    public void setLoaderContext(TemplateLoaderContext context) {
        this.context_ = context;
        this.loader_ = context.getLoader();
    }

    public void setLoader(TemplateLoader loader) {
        this.context_ = new TemplateLoaderContext(loader, (String) null);
        this.loader_ = this.context_.getLoader();
    }

    public String renderToString(TemplateDictionary td) throws TemplateException {
        return this.renderToString(this.tmpl_, td);
    }

    private Template(List<TemplateNode> tmpl) {
        this.context_ = new TemplateLoaderContext(this.loader_, (String) null);
        this.tmpl_ = tmpl;
    }

    private void render(List<TemplateNode> list, TemplateDictionary td, PrintWriter pw) throws TemplateException {
        for (int i = 0; i < list.size(); ++i) {
            TemplateNode node = (TemplateNode) list.get(i);
            if (node instanceof SectionNode) {
                i = this.handleSectionNode(list, td, i, pw);
            } else if (node instanceof EztDefineNode) {
                i = this.handleEztDefineNode(list, td, i);
            } else if (node instanceof EztConditionalNode) {
                i = this.handleEztConditionalNode(list, td, i, pw);
            } else {
                node.evaluate(td, this.context_, pw);
            }
        }

    }

    private int handleEztConditionalNode(List<TemplateNode> list, TemplateDictionary td, int is_node_idx, PrintWriter pw) throws TemplateException {
        EztConditionalNode ecn = (EztConditionalNode) list.get(is_node_idx);
        Range range = ecn.advise(list, is_node_idx, td);
        List<TemplateNode> view = list.subList(range.getStart(), range.getStop());
        this.render(view, td, pw);
        return range.getSkipTo();
    }

    private int handleEztDefineNode(List<TemplateNode> list, TemplateDictionary td, int define_node_idx) throws TemplateException {
        EztDefineNode edn = (EztDefineNode) list.get(define_node_idx);
        String var_name = edn.getVariableName();
        Range range = edn.advise(list, define_node_idx);
        List<TemplateNode> view = list.subList(range.getStart(), range.getStop());
        StringWriter sw = new StringWriter();
        this.render(view, td, new PrintWriter(sw));
        String new_value = sw.toString();
        td.put(var_name, new_value);
        return range.getSkipTo();
    }

    public void render(TemplateDictionary td, PrintWriter printWriter) throws TemplateException {
        this.render(this.tmpl_, td, printWriter);
    }

    private int handleSectionNode(List<TemplateNode> list, TemplateDictionary td, int open_tag_idx, PrintWriter collector) throws TemplateException {
        SectionNode sn = (SectionNode) list.get(open_tag_idx);
        String new_section_name = sn.getSectionName();
        int p = open_tag_idx + 1;

        for (int other_sections = 0; p < list.size(); ++p) {
            TemplateNode tp = (TemplateNode) list.get(p);
            if (tp instanceof SectionNode) {
                SectionNode maybe_close_tag = (SectionNode) tp;
                if (maybe_close_tag.isOpenSectionTag()) {
                    ++other_sections;
                }

                if (maybe_close_tag.isCloseSectionTag()) {
                    if (maybe_close_tag.getSectionName().equals(sn.getSectionName())) {
                        break;
                    }

                    if (other_sections == 0) {
                        String msg = MessageFormat.format("mismatched close tag: expecting a close tag for {0}, but got close tag for {1}", sn.getSectionName(), maybe_close_tag.getSectionName());
                        logger_.warning(msg);
                        throw new TemplateException(msg);
                    }

                    --other_sections;
                }
            }
        }

        if (p == list.size()) {
            throw new TemplateException("missing close tag for " + sn.getSectionName());
        } else if (td.isHiddenSection(sn.getSectionName())) {
            logger_.warning("Skipping section " + sn.getSectionName() + " because it is hidden");
            return p;
        } else {
            List<TemplateDictionary> subdicts = td.getChildDicts(new_section_name);
            if (subdicts.size() == 0) {
                this.render(list.subList(open_tag_idx + 1, p), td, collector);
            } else {
                Iterator var13 = subdicts.iterator();

                while (var13.hasNext()) {
                    TemplateDictionary subdict = (TemplateDictionary) var13.next();
                    this.render(list.subList(open_tag_idx + 1, p), subdict, collector);
                }
            }

            return p;
        }
    }

    private String renderToString(List<TemplateNode> list, TemplateDictionary td) throws TemplateException {
        StringWriter k = new StringWriter();
        PrintWriter pw = new PrintWriter(k);
        this.render(list, td, pw);
        pw.flush();
        return k.toString();
    }
}
