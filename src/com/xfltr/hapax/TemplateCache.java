//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax;

import com.xfltr.hapax.parser.CTemplateParser;
import com.xfltr.hapax.parser.TemplateParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class TemplateCache implements TemplateLoader {
    private final Map<String, Template> templates_ = new HashMap();
    private final Map<String, Long> lastUpdated_ = new HashMap();
    private final String basePath_;
    private final TemplateParser parser_;

    public static TemplateLoader create(String base_path) {
        return new TemplateCache(CTemplateParser.create(), base_path);
    }

    public static TemplateLoader createForParser(String base_path, TemplateParser parser) {
        return new TemplateCache(parser, base_path);
    }

    public Template getTemplate(String filename) throws TemplateException {
        filename = PathUtil.join(new String[]{this.basePath_, filename});
        File file = new File(filename);
        long last_modified = file.lastModified();
        if (this.inCache(filename, last_modified)) {
            return (Template)this.templates_.get(filename);
        } else {
            FileReader reader;
            try {
                reader = new FileReader(filename);
            } catch (FileNotFoundException var9) {
                throw new TemplateException(var9);
            }

            String contents;
            try {
                contents = this.readToString(reader);
            } catch (IOException var8) {
                throw new TemplateException(var8);
            }

            Template results = Template.parse(this.parser_, contents);
            results.setLoaderContext(new TemplateLoaderContext(this, file.getParent()));
            this.updateCache(filename, results, last_modified);
            return results;
        }
    }

    public Template getTemplate(InputStream inputStream, String filename) throws TemplateException {
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(inputStream);
        } catch (Exception var7) {
            throw new TemplateException(var7);
        }

        String contents;
        try {
            contents = this.readToString(reader);
        } catch (IOException var6) {
            throw new TemplateException(var6);
        }

        Template results = Template.parse(this.parser_, contents);
        results.setLoaderContext(new TemplateLoaderContext(this, filename));
        this.updateCache(filename, results, System.currentTimeMillis());
        return results;
    }

    public Template getTemplate(String filename, String templateDirectory) throws TemplateException {
        assert templateDirectory.startsWith(this.basePath_);

        String directory_relative_to_template_directory = PathUtil.makeRelative(this.basePath_, "");
        filename = PathUtil.join(new String[]{directory_relative_to_template_directory, filename});
        return this.getTemplate(filename);
    }

    private TemplateCache(TemplateParser parser, String templateDirectory) {
        this.basePath_ = templateDirectory;
        this.parser_ = parser;
    }

    private String readToString(Reader in) throws IOException {
        StringBuilder buf = new StringBuilder();

        try {
            for(int c = in.read(); -1 != c; c = in.read()) {
                buf.append((char)c);
            }

            String var7 = buf.toString();
            return var7;
        } finally {
            in.close();
        }
    }

    private boolean inCache(String filename, long last_modified) {
        return this.lastUpdated_.containsKey(filename) && (Long)this.lastUpdated_.get(filename) >= last_modified;
    }

    private void updateCache(String filename, Template results, long last_modified) {
        this.templates_.put(filename, results);
        this.lastUpdated_.put(filename, last_modified);
    }
}
