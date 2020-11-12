//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.layout.impl;

import com.intellij.openapi.vfs.VirtualFile;
import com.mdx.andplus.layout.CardModife;
import com.mdx.andplus.layout.LayoutRead;
import com.mdx.andplus.layout.MainFastRead;
import com.mdx.andplus.layout.commons.TempleSave;
import com.mdx.andplus.layout.model.AutoView;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemSave extends LayoutRead {

    public ItemSave(VirtualFile file, MainFastRead mfr) throws Exception {
        super(file, mfr);
    }

    public void save() throws Exception {
        super.save();
        String str = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
        String layout = this.name.replace(".xml", "");
        Pattern pattern = Pattern.compile("_(.)");

        for (Matcher matcher = pattern.matcher(str); matcher.find(); str = str.replace(matcher.group(0), matcher.group(1).toUpperCase())) {
        }

        this.classname = str.replace(".xml", "").replaceFirst("Item", "");
        TempleSave ts = new TempleSave();
        ts.put("classname", this.classname);
        ts.put("mark", "");
        ts.put("viewsdeaclear", this.getViewDecaler());
        ts.put("layout", "R.layout." + layout);
        ts.put("viewsfind", this.getFindViewId());
        ts.put("imports", this.getImport());
        ts.put("R", this.mainFastRead.getAppackageName() + ".R");
        ts.put("clicks", this.getOnClick());
        ts.put("clikcsListener", this.getOnClickListener());
        ts.put("set", this.getSet());
        this.saveItem(ts);
        this.saveAda(ts);
//        this.saveCard(ts);
//        this.saveDataFormat(ts);
    }

    public void saveDataFormat(TempleSave ts) throws Exception {
        String packagenames = this.mainFastRead.getAppackageName() + ".dataformat";
        String itemClass = this.mainFastRead.getAppackageName() + ".ada.Ada" + this.classname;
        ts.put("imports", "import " + itemClass + ";");
        ts.put("classname", "Df" + this.classname);
        ts.put("package", packagenames);
        ts.put("adaper", "Ada" + this.classname);
        VirtualFile javapath = this.mainFastRead.getProjectpath().findChild("java");
        if (javapath == null || !javapath.exists()) {
            javapath = javapath.createChildDirectory((Object) null, "java");
        }

        VirtualFile pf = javapath;
        String[] var6 = packagenames.split("\\.");
        int var7 = var6.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            String ps = var6[var8];
            VirtualFile sf = pf.findChild(ps);
            if (sf != null && sf.exists()) {
                pf = sf;
            } else {
                pf = pf.createChildDirectory((Object) null, ps);
            }
        }

        VirtualFile cfile = pf.findOrCreateChildData((Object) null, "Df" + this.classname + ".java");
        ts.save("androidTemplate/android_dataformat_model.tpl", cfile);
    }

    public void saveCard(TempleSave ts) throws Exception {
        String packagenames = this.mainFastRead.getAppackageName() + ".card";
        String itemClass = this.mainFastRead.getAppackageName() + ".item." + this.classname;
        this.mAutoCode.cardname = "Card" + this.classname;
        this.mAutoCode.cardpackage = packagenames;
        ts.put("imports", this.getImport() + "\r\nimport " + itemClass + ";");
        ts.put("classname", "Card" + this.classname);
        ts.put("package", packagenames);
        ts.put("itemView", this.classname);
        ts.put("clicks", "");
        ts.put("set", "");
        ts.put("clikcsListener", "");
        ts.put("cardtype", this.mainFastRead.getAppackageName() + ".commons." + "CardIDS.CARDID_" + this.classname.toUpperCase());
        VirtualFile javapath = this.mainFastRead.getProjectpath().findChild("java");
        if (javapath == null || !javapath.exists()) {
            javapath = javapath.createChildDirectory((Object) null, "java");
        }

        VirtualFile pf = javapath;
        String[] var6 = packagenames.split("\\.");
        int var7 = var6.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            String ps = var6[var8];
            VirtualFile sf = pf.findChild(ps);
            if (sf != null && sf.exists()) {
                pf = sf;
            } else {
                pf = pf.createChildDirectory((Object) null, ps);
            }
        }

        VirtualFile cfile = pf.findOrCreateChildData((Object) null, "Card" + this.classname + ".java");
        ts.save("androidTemplate/android_card_model.tpl", cfile);
    }

    public void saveAda(TempleSave ts) throws Exception {
        String packagenames = this.mainFastRead.getAppackageName() + ".ada";
        String itemClass = this.mainFastRead.getAppackageName() + ".item." + this.classname;
        ts.put("imports", this.getImport() + "\r\nimport " + itemClass + ";");
        ts.put("classname", "Ada" + this.classname);
        ts.put("package", packagenames);
        ts.put("itemView", this.classname);
        this.mAutoCode.adaname = "Ada" + this.classname;
        this.mAutoCode.adapackage = packagenames;
        VirtualFile javapath = this.mainFastRead.getProjectpath().findChild("java");
        if (javapath == null || !javapath.exists()) {
            javapath = javapath.createChildDirectory((Object) null, "java");
        }

        VirtualFile pf = javapath;
        String[] var6 = packagenames.split("\\.");
        int var7 = var6.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            String ps = var6[var8];
            VirtualFile sf = pf.findChild(ps);
            if (sf != null && sf.exists()) {
                pf = sf;
            } else {
                pf = pf.createChildDirectory((Object) null, ps);
            }
        }
        VirtualFile cfile = pf.findChild("Ada" + this.classname + ".kt");
        if (cfile == null || !cfile.exists()) {
            cfile = pf.findOrCreateChildData((Object) null, "Ada" + this.classname + ".kt");
            ts.save("androidTemplate/android_adapter_model_kt.tpl", cfile);
        }
    }

    public void saveItem(TempleSave ts) throws Exception {
        String packagenames = this.mainFastRead.getAppackageName() + ".item";
        this.mAutoCode.itemname = this.classname;
        this.mAutoCode.itempackage = packagenames;
        ts.put("package", packagenames);
        VirtualFile javapath = this.mainFastRead.getProjectpath().findChild("java");
        if (javapath == null || !javapath.exists()) {
            javapath = javapath.createChildDirectory((Object) null, "java");
        }

        VirtualFile pf = javapath;
        String[] var5 = packagenames.split("\\.");
        int var6 = var5.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            String ps = var5[var7];
            VirtualFile sf = pf.findChild(ps);
            if (sf != null && sf.exists()) {
                pf = sf;
            } else {
                pf = pf.createChildDirectory((Object) null, ps);
            }
        }
        VirtualFile cfile = pf.findChild(classname + ".kt");
        if (cfile == null || !cfile.exists()) {
            cfile = pf.findOrCreateChildData((Object) null, this.classname + ".kt");
            ts.save("androidTemplate/android_item_model_kt.tpl", cfile);
        }
    }

    public String getImport() {
        StringBuffer sb = new StringBuffer();
        sb.append("import android.view.View;\r\n");
        this.clasMap.put("android.view.View", true);
        Iterator var2 = this.autoviews.iterator();

        while (var2.hasNext()) {
            AutoView av = (AutoView) var2.next();
            if (!this.clasMap.containsKey(av.clas)) {
                this.clasMap.put(av.clas, true);
                sb.append("import " + av.clas + ";\n");
            }
        }

        return sb.toString();
    }

    public String getFindViewId() {
        StringBuffer sb = new StringBuffer();
        Iterator var2 = this.autoviews.iterator();

        while (var2.hasNext()) {
            AutoView av = (AutoView) var2.next();
            sb.append("        ");
            sb.append(av.name);
            sb.append("=");
            sb.append("(").append(av.type).append(")").append("contentview.findViewById(").append(av.id).append(");\n");
        }

        return sb.toString();
    }
}
