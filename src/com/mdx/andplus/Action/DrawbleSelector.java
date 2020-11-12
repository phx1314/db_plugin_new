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
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.Balloon.Position;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import com.mdx.andplus.autowriter.DrawableSelectorAW;
import javax.swing.event.HyperlinkListener;

public class DrawbleSelector extends AnAction {
    public DrawbleSelector() {
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project)event.getData(PlatformDataKeys.PROJECT);
        Module objets = (Module)event.getData(DataKeys.MODULE);
        VirtualFile selectedFile = (VirtualFile)DataKeys.VIRTUAL_FILE.getData(event.getDataContext());
        DrawableSelectorAW dsa = new DrawableSelectorAW(project, objets, selectedFile, false);
        dsa.execute();
    }

    protected void showInfoDialog(String text, AnActionEvent e) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar((Project)DataKeys.PROJECT.getData(e.getDataContext()));
        if (statusBar != null) {
            JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(text, MessageType.INFO, (HyperlinkListener)null).setFadeoutTime(10000L).createBalloon().show(RelativePoint.getCenterOf(statusBar.getComponent()), Position.atRight);
        }

    }

    protected void showErrorDialog(String text, AnActionEvent e) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar((Project)DataKeys.PROJECT.getData(e.getDataContext()));
        if (statusBar != null) {
            JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(text, MessageType.ERROR, (HyperlinkListener)null).setFadeoutTime(10000L).createBalloon().show(RelativePoint.getCenterOf(statusBar.getComponent()), Position.atRight);
        }

    }
}
