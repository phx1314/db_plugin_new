//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.layout.impl;

import com.intellij.openapi.vfs.VirtualFile;
import com.mdx.andplus.layout.LayoutRead;
import com.mdx.andplus.layout.MainFastRead;
import com.mdx.andplus.layout.commons.TempleSave;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivitySave extends LayoutRead {
    public ActivitySave(VirtualFile file, MainFastRead mfr) throws Exception {
        super(file, mfr);
    }

    public void save() throws Exception {
        super.save();
        String str = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
        String layout = this.name.replace(".xml", "");
        Pattern pattern = Pattern.compile("_(.)");

        for(Matcher matcher = pattern.matcher(str); matcher.find(); str = str.replace(matcher.group(0), matcher.group(1).toUpperCase())) {
        }

        this.classname = str.replace(".xml", "");
        String packagenames = this.mainFastRead.getAppackageName() + ".act";
        this.mAutoCode.activityname = this.classname;
        this.mAutoCode.activitypckage = packagenames;
        TempleSave ts = new TempleSave();
        ts.put("classname", this.classname);
        ts.put("mark", "");
        ts.put("package", packagenames);
        ts.put("viewsdeaclear", this.getViewDecaler());
        ts.put("layout", "R.layout." + layout);
        ts.put("viewsfind", this.getFindViewId());
        ts.put("clicks", this.getOnClick());
        ts.put("imports", this.getImport());
        ts.put("set", this.getSet());
        ts.put("clikcsListener", this.getOnClickListener());
        ts.put("R", this.mainFastRead.getAppackageName() + ".R");
        VirtualFile javapath = this.mainFastRead.getProjectpath().findChild("java");
        if (javapath == null || !javapath.exists()) {
            javapath = javapath.createChildDirectory((Object)null, "java");
        }

        VirtualFile pf = javapath;
        String[] var9 = packagenames.split("\\.");
        int var10 = var9.length;

        for(int var11 = 0; var11 < var10; ++var11) {
            String ps = var9[var11];
            VirtualFile sf = pf.findChild(ps);
            if (sf != null && sf.exists()) {
                pf = sf;
            } else {
                pf = pf.createChildDirectory((Object)null, ps);
            }
        }

        VirtualFile cfile = pf.findOrCreateChildData((Object)null, this.classname + ".java");
        ts.save("androidTemplate/android_activity_model.tpl", cfile);
        this.mainFastRead.addActivity(packagenames + "." + this.classname);
    }
}
