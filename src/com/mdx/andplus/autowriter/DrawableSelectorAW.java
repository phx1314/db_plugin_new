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
import com.mdx.andplus.common.ImageSelecter.Item;
import com.mdx.andplus.layout.commons.XMLReader;
import java.io.IOException;
import java.util.Iterator;
import org.jdom.Attribute;
import org.jdom.Element;

public class DrawableSelectorAW extends Simple {
    public Project project;
    public Module module;
    public VirtualFile selectedFile;
    public VirtualFile moduleFile;
    public VirtualFile resFile;
    public HashMap<String, ImageSelecter> map = new HashMap();
    public HashMap<String, String> moveMip = new HashMap();
    public boolean needmove = false;

    public DrawableSelectorAW(Project project, Module module, VirtualFile selectedFile, boolean needmove) {
        super(project, new PsiFile[0]);
        this.project = project;
        this.module = module;
        this.selectedFile = selectedFile;
        this.needmove = needmove;
        VirtualFile srcFile;
        if (selectedFile.toString().indexOf("/res") >= 0) {
            for(srcFile = selectedFile; !srcFile.getName().equals("res"); srcFile = srcFile.getParent()) {
            }

            this.resFile = srcFile;
        }

        this.moduleFile = module.getModuleFile();
        if (!this.moduleFile.isDirectory()) {
            this.moduleFile = this.moduleFile.getParent();
        }

        if (this.resFile == null) {
            srcFile = this.moduleFile.findChild("src");
            this.resFile = srcFile.findChild("main").findChild("res");
        }

    }

    public void init() {
        VirtualFile[] var1 = this.resFile.getChildren();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            VirtualFile vf = var1[var3];
            String vn = vf.getName();
            if (vn.startsWith("drawable") || vn.startsWith("mipmap")) {
                VirtualFile[] var6 = vf.getChildren();
                int var7 = var6.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    VirtualFile cf = var6[var8];
                    if (cf.getName().endsWith(".png") || cf.getName().endsWith(".jpg") || cf.getName().endsWith(".gif")) {
                        Item item = new Item();
                        String name = cf.getName();
                        String rname = name.replaceAll("\\..*", "");
                        String xpath = vn.replace("drawable", "").replace("mipmap", "");
                        if (vn.startsWith("drawable")) {
                            if (this.needmove) {
                                if (name.indexOf(".9.") >= 0) {
                                    item.path = "drawable";
                                } else if (this.move(this.resFile, "mipmap" + xpath, cf)) {
                                    this.moveMip.put("@drawable/" + rname, "@mipmap/" + rname);
                                    item.path = "mipmap";
                                } else {
                                    item.path = "drawable";
                                }
                            } else {
                                item.path = "drawable";
                            }
                        } else if (vn.startsWith("mipmap")) {
                            if (name.indexOf(".9.") >= 0) {
                                item.path = "drawable";
                                if (this.move(this.resFile, "drawable" + xpath, cf)) {
                                    this.moveMip.put("@mipmap/" + rname, "@drawable/" + rname);
                                }
                            } else {
                                item.path = "mipmap";
                            }
                        }

                        item.img = rname;
                        if (rname.endsWith("_n")) {
                            item.name = rname.substring(0, rname.length() - 2);
                            item.state = "n";
                        } else if (rname.endsWith("_h")) {
                            item.name = rname.substring(0, rname.length() - 2);
                            item.state = "h";
                        } else if (rname.endsWith("_d")) {
                            item.name = rname.substring(0, rname.length() - 2);
                            item.state = "d";
                        } else if (rname.endsWith("_s")) {
                            item.name = rname.substring(0, rname.length() - 2);
                            item.state = "s";
                        } else if (rname.endsWith("_p")) {
                            item.name = rname.substring(0, rname.length() - 2);
                            item.state = "p";
                        }

                        if (item.name.length() > 0) {
                            item.vfile = cf;
                            if (this.map.containsKey(item.name)) {
                                ((ImageSelecter)this.map.get(item.name)).add(item);
                            } else {
                                ImageSelecter hm = new ImageSelecter();
                                hm.add(item);
                                this.map.put(item.name, hm);
                            }
                        }
                    }
                }
            }
        }

    }

    protected void run() throws Throwable {
        VirtualFile dfile = this.resFile.findChild("drawable");
        if (dfile == null || !dfile.exists()) {
            dfile = this.resFile.createChildDirectory((Object)null, "drawable");
        }

        this.init();
        Iterator var2 = this.map.keySet().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            ((ImageSelecter)this.map.get(key)).createXml(this.resFile, dfile);
        }

        this.changXmls();
    }

    public void changXmls() throws Exception {
        VirtualFile[] var1 = this.resFile.getChildren();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            VirtualFile df = var1[var3];
            if (!df.getName().startsWith("xml") && df.isDirectory()) {
                VirtualFile[] var5 = df.getChildren();
                int var6 = var5.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    VirtualFile vf = var5[var7];
                    if (vf.getName().endsWith(".xml")) {
                        XMLReader xmlReader = new XMLReader(vf);
                        Element ele = xmlReader.getDocument().getRootElement();
                        this.changXml(ele);
                        xmlReader.save(vf);
                    }
                }
            }
        }

    }

    public boolean changXml(Element ele) throws Exception {
        boolean retn = false;
        Iterator var3 = ele.getAttributes().iterator();

        while(var3.hasNext()) {
            Attribute ab = (Attribute)var3.next();
            if (this.moveMip.containsKey(ab.getValue())) {
                String movet = (String)this.moveMip.get(ab.getValue());
                ab.setValue(movet);
                retn = true;
            }
        }

        Element e;
        for(var3 = ele.getChildren().iterator(); var3.hasNext(); retn = this.changXml(e) || retn) {
            e = (Element)var3.next();
        }

        return retn;
    }

    public boolean move(VirtualFile resFile, String xpath, VirtualFile vfile) {
        try {
            VirtualFile mp = resFile.findChild(xpath);
            if (mp == null || !mp.exists()) {
                mp = resFile.createChildDirectory((Object)null, xpath);
            }

            vfile.move((Object)null, mp);
            return true;
        } catch (IOException var5) {
            var5.printStackTrace();
            return false;
        }
    }
}
