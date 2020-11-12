//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.Action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon.Position;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import com.mdx.andplus.autowriter.FitScreenAW;
import com.mdx.andplus.autowriter.FitStringAW;

import javax.swing.event.HyperlinkListener;

public class FitString extends AnAction {
    public FitString() {
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project) event.getData(PlatformDataKeys.PROJECT);
        Module objets = (Module) event.getData(DataKeys.MODULE);
        VirtualFile selectedFile = (VirtualFile) DataKeys.VIRTUAL_FILE.getData(event.getDataContext());
        FitStringAW dsa = new FitStringAW(project, objets, selectedFile);
        dsa.execute();
    }
}
