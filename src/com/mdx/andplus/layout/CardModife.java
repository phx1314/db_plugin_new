//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.layout;

import com.intellij.openapi.vfs.VirtualFile;
import com.mdx.andplus.layout.commons.PattenUtil;
import com.mdx.andplus.layout.commons.TempleSave;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class CardModife {
    public MainFastRead mainFastRead;
    public int typenow = 8000;
    public VirtualFile cardidsjava;
    public VirtualFile parentPath;
    public HashMap<String, String> ids = new HashMap();
    public String packagenames;

    public CardModife(MainFastRead mainFastRead) throws Exception {
        this.mainFastRead = mainFastRead;
        mainFastRead.getApppackagePath();
        this.packagenames = mainFastRead.getAppackageName() + ".commons";
        VirtualFile javapath = mainFastRead.getProjectpath().findChild("java");
        if (javapath == null || !javapath.exists()) {
            javapath = javapath.createChildDirectory((Object)null, "java");
        }

        VirtualFile pf = javapath;
        String[] var4 = this.packagenames.split("\\.");
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String ps = var4[var6];
            VirtualFile sf = pf.findChild(ps);
            if (sf != null && sf.exists()) {
                pf = sf;
            } else {
                pf = pf.createChildDirectory((Object)null, ps);
            }
        }

        this.parentPath = pf;
        this.cardidsjava = pf.findChild("CardIDS.java");
    }

    public void read() throws Exception {
        if (this.cardidsjava != null && this.cardidsjava.exists()) {
            InputStream in = this.cardidsjava.getInputStream();
            new StringBuffer();
            BufferedReader inr = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String lin = "";

            while((lin = inr.readLine()) != null) {
                PattenUtil pu = new PattenUtil();
                if (pu.isPatten(lin, " *public *static *final *int *(CARDID_[A-Z_]*) *= *([0-9]*) *;")) {
                    this.ids.put(pu.getPattens().get(0), "");
                }
            }

            in.close();
            inr.close();
        }
    }

    public void add(String id) {
        this.ids.put(id, "");
    }

    public void save() throws Exception {
        if (this.cardidsjava == null || !this.cardidsjava.exists()) {
            this.cardidsjava = this.parentPath.findOrCreateChildData((Object)null, "CardIDS.java");
        }

        TempleSave ts = new TempleSave();
        StringBuffer sb = new StringBuffer();
        Iterator var3 = this.ids.keySet().iterator();

        while(var3.hasNext()) {
            String id = (String)var3.next();
            ++this.typenow;
            sb.append("    public static final int " + id + " = " + this.typenow + " ;\n");
        }

        ts.put("package", this.packagenames);
        ts.put("cardids", sb.toString());
        ts.save("androidTemplate/android_cardids_model.tpl", this.cardidsjava);
    }
}
