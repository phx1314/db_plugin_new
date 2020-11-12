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
import com.mdx.andplus.common.CreateFitXml.Dimclass;
import com.mdx.andplus.common.ImageSelecter;
import com.mdx.andplus.common.SearchXMLTextValues;
import com.mdx.andplus.common.SearchXMLValues;
import com.mdx.andplus.layout.commons.XMLReader;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.util.ArrayList;

public class FitStringAW extends Simple {
    public Project project;
    public VirtualFile selectedFile;
    public VirtualFile moduleFile;

    public FitStringAW(Project project, Module module, VirtualFile selectedFile) {
        super(project, new PsiFile[0]);
        this.project = project;
        this.selectedFile = selectedFile;
        this.moduleFile = module.getModuleFile();
        if (!this.moduleFile.isDirectory()) {
            this.moduleFile = this.moduleFile.getParent();
        }


    }


    public void init() {
    }

    protected void run() throws Throwable {
        if (selectedFile.isDirectory() && selectedFile.getName().startsWith("layout")) {
            VirtualFile[] var7 = selectedFile.getChildren();
            int var8 = var7.length;
            for (int var9 = 0; var9 < var8; ++var9) {
                VirtualFile vf = var7[var9];
                if (vf.getName().endsWith(".xml")) {
                    try {
                        SearchXMLTextValues sxv = new SearchXMLTextValues(vf, moduleFile);
                        sxv.searchText((Element) null);
                        sxv.save();
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }
                }
            }
        } else if (selectedFile.getParent().getName().startsWith("layout") && selectedFile.getName().endsWith(".xml")) {
            try {
                SearchXMLTextValues sxv = new SearchXMLTextValues(selectedFile, moduleFile);
                sxv.searchText((Element) null);
                sxv.save();
            } catch (Exception var10) {
                var10.printStackTrace();
            }
        }


    }


}
