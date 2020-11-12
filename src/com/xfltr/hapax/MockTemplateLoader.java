//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax;

import com.xfltr.hapax.parser.CTemplateParser;
import com.xfltr.hapax.parser.TemplateParser;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

class MockTemplateLoader implements TemplateLoader {
    private final Map<String, String> mock_templates = new HashMap();
    private final TemplateParser parser_;

    public MockTemplateLoader() {
        this.parser_ = CTemplateParser.create();
    }

    public MockTemplateLoader(TemplateParser parser) {
        this.parser_ = parser;
    }

    public void put(String name, String template) {
        this.mock_templates.put(name, template);
    }

    public Template getTemplate(String filename) throws TemplateException {
        Template template = Template.parse(this.parser_, (String)this.mock_templates.get(filename));
        template.setLoader(this);
        return template;
    }

    public Template getTemplate(String filename, String templateDirectory) throws TemplateException {
        return this.getTemplate(filename);
    }

    public Template getTemplate(InputStream in, String filename) throws TemplateException {
        return this.getTemplate(filename);
    }
}
