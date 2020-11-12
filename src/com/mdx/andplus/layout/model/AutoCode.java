//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.layout.model;

import com.mdx.andplus.layout.MainFastRead;
import com.mdx.andplus.layout.commons.TempleSave;
import java.util.ArrayList;

public class AutoCode {
    public ArrayList<AutoView> autoviews = new ArrayList();
    public String adaname;
    public String adapackage;
    public String cardname;
    public String cardpackage;
    public String itemname;
    public String itempackage;
    public String frgname;
    public String frgpacage;
    public String dianame;
    public String diapackage;
    public String activityname;
    public String activitypckage;
    public MainFastRead mainFastRead;
    public String name;
    public String classname;
    public int type = 0;

    public AutoCode() {
    }

    public void save(MainFastRead mfr) {
        String packagenames = mfr.getAppackageName() + ".debug.card";
        String classname = null;
        if (!n(this.itemname)) {
            classname = this.itemname;
            TempleSave ts = new TempleSave();
            ts.put("classname", classname);
            ts.put("mark", "");
            ts.put("package", packagenames);
        }

    }

    public static boolean n(String str) {
        return str == null || str.length() == 0;
    }
}
