//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax.parser;

import com.xfltr.hapax.TemplateDictionary;
import com.xfltr.hapax.TemplateException;
import com.xfltr.hapax.TemplateLoaderContext;
import java.io.PrintWriter;

public abstract class TemplateNode {
    public TemplateNode() {
    }

    public abstract void evaluate(TemplateDictionary var1, TemplateLoaderContext var2, PrintWriter var3) throws TemplateException;
}
