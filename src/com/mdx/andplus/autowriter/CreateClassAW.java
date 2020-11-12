//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.autowriter;

import com.intellij.openapi.command.WriteCommandAction.Simple;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.hash.HashMap;
import com.mdx.andplus.common.ImageSelecter;
import com.mdx.andplus.layout.CardModife;
import com.mdx.andplus.layout.MainFastRead;
import com.mdx.andplus.layout.commons.TempleSave;
import com.mdx.andplus.layout.impl.ActivitySave;
import com.mdx.andplus.layout.impl.DialogSave;
import com.mdx.andplus.layout.impl.FragmentSave;
import com.mdx.andplus.layout.impl.ItemSave;

public class CreateClassAW extends Simple {
    public Project project;
    public Module module;
    public VirtualFile moduleFile;
    public VirtualFile mainfastFile;
    public VirtualFile[] selectedFiles;
    public HashMap<String, ImageSelecter> map = new HashMap();
    public java.util.HashMap<String, String> baseCreate = new java.util.HashMap();

    public CreateClassAW(Project project, Module module, VirtualFile[] selectedFiles) {
        super(project, new PsiFile[0]);
        this.project = project;
        this.module = module;
        this.selectedFiles = selectedFiles;
        this.moduleFile = module.getModuleFile();
        if (!this.moduleFile.isDirectory()) {
            this.moduleFile = this.moduleFile.getParent();
        }

        VirtualFile srcFile = this.moduleFile.findChild("src");
        this.mainfastFile = srcFile.findChild("main").findChild("AndroidManifest.xml");
    }

    public void init() throws Exception {
        MainFastRead mfr = new MainFastRead(this.mainfastFile, this.mainfastFile.getParent());
        VirtualFile[] var3 = this.selectedFiles;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            VirtualFile selectedFile = var3[var5];
            if (selectedFile.isDirectory() && selectedFile.getName().startsWith("layout")) {
                VirtualFile[] var7 = selectedFile.getChildren();
                int var8 = var7.length;

                for (int var9 = 0; var9 < var8; ++var9) {
                    VirtualFile vf = var7[var9];
                    this.createJava(vf, mfr);
                }
            } else {
                this.createJava(selectedFile, mfr);
            }
        }

        if (this.baseCreate.containsKey("act")) {
            saveBaseAct(mfr);
        }

        if (this.baseCreate.containsKey("dia")) {
            saveBaseDia(mfr);
        }

        if (this.baseCreate.containsKey("frg")) {
            saveBaseFrg(mfr);
        }

        if (this.baseCreate.containsKey("item")) {
            saveBaseItem(mfr);
//            cardModife.save();
        }

        if (this.baseCreate.containsKey("act")) {
            mfr.save(this.mainfastFile);
        }

    }

    public void createJava(VirtualFile selectedFile, MainFastRead mfr) throws Exception {
        if (selectedFile.getName().startsWith("act_")) {
            (new ActivitySave(selectedFile, mfr)).save();
            this.baseCreate.put("act", "");
        } else if (selectedFile.getName().startsWith("frg_")) {
            (new FragmentSave(selectedFile, mfr)).save();
            this.baseCreate.put("frg", "");
        } else if (selectedFile.getName().startsWith("item_")) {
            (new ItemSave(selectedFile, mfr)).save();
            this.baseCreate.put("item", "");
        } else if (selectedFile.getName().startsWith("dia_")) {
            (new DialogSave(selectedFile, mfr)).save();
            this.baseCreate.put("dia", "");
        } else if (selectedFile.getName().startsWith("pop_")) {
        }

    }

    public static void saveBaseAct(MainFastRead mfr) throws Exception {
        String packagenames = mfr.getAppackageName() + ".act";
        String classname = "BaseAct";
        TempleSave ts = new TempleSave();
        ts.put("classname", classname);
        ts.put("mark", "");
        ts.put("package", packagenames);
        VirtualFile javapath = mfr.getProjectpath().findChild("java");
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

        VirtualFile cfile = pf.findChild(classname + ".java");
        if (cfile == null || !cfile.exists()) {
            cfile = pf.findOrCreateChildData((Object) null, classname + ".java");
            ts.save("androidTemplate/android_baseAct_model.tpl", cfile);
        }
    }

    public static void saveBaseFrg(MainFastRead mfr) throws Exception {
        String packagenames = mfr.getAppackageName() + ".frg";
        String classname = "BaseFrg";
        TempleSave ts = new TempleSave();
        ts.put("classname", classname);
        ts.put("mark", "");
        ts.put("package", packagenames);
        VirtualFile javapath = mfr.getProjectpath().findChild("java");
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

        VirtualFile cfile = pf.findChild(classname + ".kt");
        if (cfile == null || !cfile.exists()) {
            cfile = pf.findOrCreateChildData((Object) null, classname + ".kt");
            ts.save("androidTemplate/android_baseFrg_model_kt.tpl", cfile);
        }
    }

    public static void saveBaseItem(MainFastRead mfr) throws Exception {
        String packagenames = mfr.getAppackageName() + ".item";
        String classname = "BaseItem";
        TempleSave ts = new TempleSave();
        ts.put("classname", classname);
        ts.put("mark", "");
        ts.put("package", packagenames);
        VirtualFile javapath = mfr.getProjectpath().findChild("java");
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

        VirtualFile cfile = pf.findChild(classname + ".kt");
        if (cfile == null || !cfile.exists()) {
            cfile = pf.findOrCreateChildData((Object) null, classname + ".kt");
            ts.save("androidTemplate/android_baseItem_model_kt.tpl", cfile);
        }
    }

    public static void saveBaseDia(MainFastRead mfr) throws Exception {
        String packagenames = mfr.getAppackageName() + ".dialog";
        String classname = "BaseDia";
        TempleSave ts = new TempleSave();
        ts.put("classname", classname);
        ts.put("mark", "");
        ts.put("package", packagenames);
        VirtualFile javapath = mfr.getProjectpath().findChild("java");
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

        VirtualFile cfile = pf.findChild(classname + ".java");
        if (cfile == null || !cfile.exists()) {
            cfile = pf.findOrCreateChildData((Object) null, classname + ".java");
            ts.save("androidTemplate/android_baseDia_model.tpl", cfile);
        }
    }

    protected void run() throws Throwable {
        this.init();
    }

    public void checkXml(VirtualFile resFile, VirtualFile xmlFile) throws Exception {
    }
}
