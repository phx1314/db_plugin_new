//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax.parser;

import com.xfltr.hapax.Modifiers;
import com.xfltr.hapax.TemplateDictionary;
import com.xfltr.hapax.TemplateLoaderContext;
import com.xfltr.hapax.Modifiers.FLAGS;
import java.io.PrintWriter;
import java.util.List;

public class VariableNode extends TemplateNode {
    private final String variable;
    private final List<FLAGS> modifiers;

    private VariableNode(String variable, List<FLAGS> modifiers) {
        this.variable = variable;
        this.modifiers = modifiers;
    }

    public void evaluate(TemplateDictionary dict, TemplateLoaderContext context, PrintWriter collector) {
        String t = dict.get(this.variable);
        if (!dict.contains(this.variable)) {
            t = "";
        } else {
            t = Modifiers.applyModifiers(t, this.modifiers);
        }

        collector.write(t);
    }

    public static VariableNode parse(String spec) {
        String[] split = spec.split(":");
        List<FLAGS> modifiers = Modifiers.parseModifiers(split);
        return new VariableNode(split[0], modifiers);
    }
}
