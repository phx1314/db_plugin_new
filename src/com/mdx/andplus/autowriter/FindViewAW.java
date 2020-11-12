//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.autowriter;

import com.intellij.openapi.command.WriteCommandAction.Simple;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiStatement;
import com.mdx.andplus.common.MElement;
import com.mdx.andplus.common.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FindViewAW extends Simple {
    protected PsiFile mFile;
    protected Project mProject;
    protected PsiClass mClass;
    protected PsiElementFactory mFactory;
    protected PsiFile mlayout;
    protected int offset;

    public FindViewAW(Project project, Editor editor, PsiFile file) {
        super(project, new PsiFile[0]);
        this.mClass = Utils.getTargetClass(editor, file);
        this.mProject = project;
        this.mFactory = JavaPsiFacade.getElementFactory(this.mProject);
        this.mFile = file;
        this.mlayout = Utils.getLayoutFileFromCaret(editor, file);
        this.offset = editor.getCaretModel().getOffset();
    }

    protected void run() throws Throwable {
        ArrayList<MElement> elements = new ArrayList();
        Utils.getIDsFromLayout(this.mlayout, elements);
        this.getDeclerParam(elements);
        this.getFindViewId(elements);
    }

    public void getDeclerParam(ArrayList<MElement> elements) {
        Iterator var2 = elements.iterator();

        while(true) {
            MElement av;
            String str;
            PsiField psiField;
            do {
                do {
                    do {
                        if (!var2.hasNext()) {
                            return;
                        }

                        av = (MElement)var2.next();
                    } while(av.id == null);
                } while(av.id.trim().length() == 0);

                str = "public " + (av.nameFull == null ? av.name : av.nameFull) + " " + av.fieldName + ";";
                psiField = this.mClass.findFieldByName(av.fieldName, true);
            } while(psiField != null && psiField.getName().equals(av.fieldName));

            this.mClass.add(this.mFactory.createFieldFromText(str, this.mClass));
        }
    }

    public void getFindViewId(ArrayList<MElement> elements) {
        StringBuffer sb = new StringBuffer();
        HashMap<String, String> setmap = new HashMap();
        PsiMethod[] findvMethods = this.mClass.findMethodsByName("findVMethod", true);
        PsiMethod findvMethod = null;
        PsiStatement psiStatementParam = null;
        PsiStatement nstatement;
        if (findvMethods.length > 0) {
            findvMethod = findvMethods[0];
            PsiStatement[] statements = findvMethod.getBody().getStatements();
            PsiStatement[] var8 = statements;
            int var9 = statements.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                nstatement = var8[var10];
                String value = nstatement.getText();
                if (value.indexOf("findViewById") >= 0) {
                    String key = value.substring(0, value.indexOf(61)).trim();
                    setmap.put(key, nstatement.getText());
                    psiStatementParam = nstatement;
                }
            }
        }

        Iterator var14;
        MElement av;
        String vname;
        if (findvMethods.length == 0) {
            sb.append("private void findVMethod(){");
            var14 = elements.iterator();

            while(var14.hasNext()) {
                av = (MElement)var14.next();
                vname = av.nameFull == null ? av.name : av.nameFull;
                sb.append(av.fieldName);
                sb.append("=");
                sb.append("(").append(vname).append(")").append("findViewById(").append(av.getFullID()).append(");\n");
            }
        } else {
            String str;
            if (psiStatementParam != null) {
                for(int i = elements.size() - 1; i >= 0; --i) {
                    av = (MElement)elements.get(i);
                    if (!setmap.containsKey(av.fieldName)) {
                        vname = av.nameFull == null ? av.name : av.nameFull;
                        str = av.fieldName + "=(" + vname + ")findViewById(" + av.getFullID() + ");";
                        nstatement = this.mFactory.createStatementFromText(str, this.mClass);
                        findvMethod.getBody().addAfter(nstatement, psiStatementParam);
                    }
                }
            } else {
                var14 = elements.iterator();

                while(var14.hasNext()) {
                    av = (MElement)var14.next();
                    vname = av.nameFull == null ? av.name : av.nameFull;
                    str = av.fieldName + "=(" + vname + ")findViewById(" + av.getFullID() + ");";
                    nstatement = this.mFactory.createStatementFromText(str, this.mClass);
                    findvMethod.getBody().add(nstatement);
                }
            }
        }

        if (findvMethods.length == 0) {
            sb.append("}");
            this.mClass.add(this.mFactory.createMethodFromText(sb.toString(), this.mClass));
        }

    }
}
