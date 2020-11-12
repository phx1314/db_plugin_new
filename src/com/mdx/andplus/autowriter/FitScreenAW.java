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
import com.mdx.andplus.common.CreateFitXml;
import com.mdx.andplus.common.ImageSelecter;
import com.mdx.andplus.common.SearchXMLValues;
import com.mdx.andplus.common.CreateFitXml.Dimclass;
import java.util.ArrayList;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class FitScreenAW extends Simple {
    public Project project;
    public Module module;
    public VirtualFile selectedFile;
    public VirtualFile moduleFile;
    public VirtualFile resFile;
    public HashMap<String, ImageSelecter> map = new HashMap();
    public boolean needmove = false;
    public ArrayList<Dimclass> dplist = new ArrayList();
    public ArrayList<Dimclass> splist = new ArrayList();

    public FitScreenAW(Project project, Module module, VirtualFile selectedFile, boolean needmove) {
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
    }

    protected void run() throws Throwable {
        VirtualFile[] var1 = this.resFile.getChildren();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            VirtualFile f = var1[var3];
            if (f.isDirectory() && !f.getName().startsWith("values-sw") && !f.getName().startsWith("values-w") && !f.getName().equals("xml")) {
                VirtualFile[] var5 = f.getChildren();
                int var6 = var5.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    VirtualFile vf = var5[var7];
                    if (vf.getName().endsWith(".xml")) {
                        try {
                            SearchXMLValues sxv = new SearchXMLValues(vf);
                            sxv.searchdp(this.dplist, this.splist, (Element)null);
                            sxv.save();
                        } catch (Exception var10) {
                            var10.printStackTrace();
                            vf.delete((Object)null);
                        }
                    }
                }
            }
        }

        new CreateFitXml(this.dplist, this.splist, this.resFile, 0.0F, module );
    }

    public void checkXml(VirtualFile resFile, VirtualFile xmlFile) throws Exception {
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(xmlFile.getInputStream());
        Element root = doc.getRootElement();
    }
}
