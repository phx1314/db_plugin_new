//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.Action;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.mdx.andplus.autowriter.FindViewAW;
import com.mdx.andplus.common.Utils;

public class FindVAction extends BaseGenerateAction {
    private PsiElementFactory mFactory;

    public FindVAction() {
        super((CodeInsightActionHandler)null);
    }

    public FindVAction(CodeInsightActionHandler handler) {
        super(handler);
    }

    public FindVAction(CodeInsightActionHandler handler, PsiElementFactory mFactory) {
        super(handler);
        this.mFactory = mFactory;
    }

    protected boolean isValidForClass(PsiClass targetClass) {
        return true;
    }

    public boolean isValidForFile(Project project, Editor editor, PsiFile file) {
        this.mFactory = JavaPsiFacade.getElementFactory(project);

        try {
            PsiFile layout = Utils.getLayoutFileFromCaret(editor, file);
            return layout != null;
        } catch (Exception var5) {
            return false;
        }
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project)event.getData(PlatformDataKeys.PROJECT);
        Editor editor = (Editor)event.getData(PlatformDataKeys.EDITOR);
        this.mFactory = JavaPsiFacade.getElementFactory(project);
        PsiFile file = PsiUtilBase.getPsiFileInEditor(editor, project);
        FindViewAW aaw = new FindViewAW(project, editor, file);
        aaw.execute();
    }
}
