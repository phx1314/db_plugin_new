//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax.parser;

import com.xfltr.hapax.Modifiers;
import com.xfltr.hapax.Template;
import com.xfltr.hapax.TemplateDictionary;
import com.xfltr.hapax.TemplateException;
import com.xfltr.hapax.TemplateLoaderContext;
import com.xfltr.hapax.Modifiers.FLAGS;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

public class IncludeNode extends TemplateNode {
    private final String includeName;
    private final List<FLAGS> modifiers;

    private IncludeNode(String includeName, List<FLAGS> modifiers) {
        this.includeName = includeName;
        this.modifiers = modifiers;
    }

    public static IncludeNode parse(String spec) {
        String[] split = spec.split(":");
        return new IncludeNode(split[0], Modifiers.parseModifiers(split));
    }

    public final void evaluate(TemplateDictionary dict, TemplateLoaderContext context, PrintWriter collector) throws TemplateException {
        String filename = dict.get(this.includeName);
        if (filename == null) {
            throw new TemplateException("The template identifier for included section " + this.includeName + " is not set!");
        } else {
            Template incl_tmpl = context.getLoader().getTemplate(filename, context.getTemplateDirectory());
            incl_tmpl.setLoaderContext(context);
            PrintWriter previous_printwriter = null;
            StringWriter sw = null;
            if (this.modifiers.size() > 0) {
                previous_printwriter = collector;
                sw = new StringWriter();
                collector = new PrintWriter(sw);
            }

            List<TemplateDictionary> child_dicts = dict.getChildDicts(this.includeName);
            if (child_dicts.size() == 0) {
                incl_tmpl.render(dict, collector);
            } else {
                Iterator var9 = child_dicts.iterator();

                while(var9.hasNext()) {
                    TemplateDictionary subdict = (TemplateDictionary)var9.next();
                    incl_tmpl.render(subdict, collector);
                }
            }

            if (previous_printwriter != null) {
                String results = sw.toString();
                previous_printwriter.write(Modifiers.applyModifiers(results, this.modifiers));
            }

        }
    }
}
