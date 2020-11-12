//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax;

public class TemplateLoaderContext {
    private final TemplateLoader loader_;
    private final String template_directory_;

    public TemplateLoaderContext(TemplateLoader loader, String template_directory) {
        this.loader_ = loader;
        this.template_directory_ = template_directory;
    }

    public TemplateLoader getLoader() {
        return this.loader_;
    }

    public String getTemplateDirectory() {
        return this.template_directory_;
    }
}
