//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax.parser;

import com.xfltr.hapax.PathUtil;
import com.xfltr.hapax.Template;
import com.xfltr.hapax.TemplateDictionary;
import com.xfltr.hapax.TemplateException;
import com.xfltr.hapax.TemplateLoaderContext;
import java.io.PrintWriter;

public class EztIncludeNode extends TemplateNode {
    private final String variableName_;

    private EztIncludeNode(String s) {
        this.variableName_ = s;
    }

    public static TemplateNode parse(String s) {
        return new EztIncludeNode(s);
    }

    public void evaluate(TemplateDictionary dict, TemplateLoaderContext context, PrintWriter collector) throws TemplateException {
        String include_filename;
        if (this.variableName_.startsWith("\"")) {
            include_filename = this.variableName_.replaceAll("\"", "");
        } else {
            include_filename = dict.get(this.variableName_);
        }

        Template template;
        String search_filename;
        if (PathUtil.isAbsolute(include_filename)) {
            include_filename = PathUtil.join(new String[]{"/", PathUtil.makeRelative("/html", include_filename)});
            template = context.getLoader().getTemplate(include_filename);
            search_filename = include_filename;
        } else {
            template = context.getLoader().getTemplate(include_filename, context.getTemplateDirectory());
            if (context.getTemplateDirectory() != null) {
                search_filename = PathUtil.join(new String[]{context.getTemplateDirectory(), include_filename});
            } else {
                search_filename = PathUtil.join(new String[]{"", include_filename});
            }
        }

        String warning_flag = "__already__included__" + search_filename;
        if (dict.contains(warning_flag)) {
            throw new CyclicIncludeException("Cyclic include loop detected: " + search_filename + " has been included multiple times.");
        } else {
            dict.put(warning_flag, "");
            template.render(dict, collector);
        }
    }
}
