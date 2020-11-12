//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.layout.commons;

import com.intellij.openapi.vfs.VirtualFile;
import com.xfltr.hapax.Template;
import com.xfltr.hapax.TemplateCache;
import com.xfltr.hapax.TemplateDictionary;
import com.xfltr.hapax.TemplateLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TempleSave {
    private TemplateDictionary dict = TemplateDictionary.create();
    private String tempPath = "";

    public TempleSave() {
        if (this.tempPath != null) {
            this.tempPath = this.tempPath;
        }

        String username = System.getProperty("user.name");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        this.dict.put("creater", username);
        this.dict.put("author", username);
        this.dict.put("time", time);
    }

    public void put(String key, String value) {
        if (value == null) {
            value = " ";
        }

        this.dict.put(key, value);
    }

    public void put(String key, int value) {
        this.dict.put(key, value);
    }

    public void print(String tmplateFile) throws Exception {
        TemplateLoader loader = TemplateCache.create("");
        Template tmpl = loader.getTemplate(tmplateFile);
        System.out.println(tmpl.renderToString(this.dict));
    }

    public void save(String tmplateFile, VirtualFile file) throws Exception {
        InputStream inp = this.getClass().getResourceAsStream("/" + tmplateFile);
        OutputStream minput = file.getOutputStream((Object)null);
        TemplateLoader loader = TemplateCache.create("");
        Template tmpl = loader.getTemplate(inp, this.tempPath + tmplateFile);
        minput.write(tmpl.renderToString(this.dict).getBytes("UTF-8"));
        minput.flush();
        if (minput != null) {
            minput.close();
        }

    }

    public void saveByJar(String tmplateFile, File file) throws Exception {
        FileOutputStream minput = null;
        minput = new FileOutputStream(file);
        TemplateLoader loader = TemplateCache.create("");
        Template tmpl = loader.getTemplate(this.tempPath + tmplateFile);
        minput.write(tmpl.renderToString(this.dict).getBytes("UTF-8"));
        minput.flush();
        if (minput != null) {
            minput.close();
        }

    }
}
