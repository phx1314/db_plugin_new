package com.mdx.andplus.Action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.mdx.andplus.autowriter.CreateClassAW;

public class CreateClass extends AnAction {
    public CreateClass() {
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project)event.getData(PlatformDataKeys.PROJECT);
        Module module = (Module)event.getData(DataKeys.MODULE);
        VirtualFile[] selectedFiles = (VirtualFile[])DataKeys.VIRTUAL_FILE_ARRAY.getData(event.getDataContext());
        CreateClassAW classAW = new CreateClassAW(project, module, selectedFiles);
        classAW.execute();
    }
}
