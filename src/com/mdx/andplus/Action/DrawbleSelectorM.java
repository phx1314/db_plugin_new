//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.Action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.mdx.andplus.autowriter.DrawableSelectorAW;

public class DrawbleSelectorM extends DrawbleSelector {
    public DrawbleSelectorM() {
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project)event.getData(PlatformDataKeys.PROJECT);
        Module objets = (Module)event.getData(DataKeys.MODULE);
        VirtualFile selectedFile = (VirtualFile)DataKeys.VIRTUAL_FILE.getData(event.getDataContext());
        DrawableSelectorAW dsa = new DrawableSelectorAW(project, objets, selectedFile, true);
        dsa.execute();
    }
}
