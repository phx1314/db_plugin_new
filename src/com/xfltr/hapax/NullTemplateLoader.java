//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax;

import java.io.InputStream;

class NullTemplateLoader implements TemplateLoader {
    NullTemplateLoader() {
    }

    public Template getTemplate(String filename) throws TemplateException {
        throw new TemplateException("You must configure the Template with setLoader() prior to including hapax.");
    }

    public Template getTemplate(String filename, String templateDirectory) throws TemplateException {
        return this.getTemplate(filename);
    }

    public Template getTemplate(InputStream in, String filename) throws TemplateException {
        throw new TemplateException("You must configure the Template with setLoader() prior to including hapax.");
    }
}
