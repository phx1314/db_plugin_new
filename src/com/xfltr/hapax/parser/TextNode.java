//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax.parser;

import com.xfltr.hapax.TemplateDictionary;
import com.xfltr.hapax.TemplateLoaderContext;
import java.io.PrintWriter;

public class TextNode extends TemplateNode {
    private final String text;

    public static TextNode create(String t) {
        return new TextNode(t);
    }

    private TextNode(String text) {
        this.text = text;
    }

    public void evaluate(TemplateDictionary dict, TemplateLoaderContext context, PrintWriter collector) {
        collector.write(this.text);
    }
}
