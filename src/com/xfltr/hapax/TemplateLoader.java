//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax;

import java.io.InputStream;

public interface TemplateLoader {
    Template getTemplate(String var1) throws TemplateException;

    Template getTemplate(String var1, String var2) throws TemplateException;

    Template getTemplate(InputStream var1, String var2) throws TemplateException;
}
