//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.common;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.SyntheticElement;
import com.intellij.psi.XmlRecursiveElementVisitor;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;
import com.intellij.psi.search.EverythingGlobalScope;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;

public class Utils {
    public Utils() {
    }

    public static PsiFile getLayoutFileFromCaret(Editor editor, PsiFile file) {
        int offset = editor.getCaretModel().getOffset();
        PsiElement candidateA = file.findElementAt(offset);
        PsiElement candidateB = file.findElementAt(offset - 1);

        PsiElement pe;
        for(pe = file.findElementAt(offset); !(pe instanceof PsiStatement); pe = pe.getParent()) {
        }

        pe.getText();
        PsiFile layout = findLayoutResource(candidateA);
        return layout != null ? layout : findLayoutResource(candidateB);
    }

    public static PsiFile findLayoutResource(PsiElement element) {
        if (element == null) {
            return null;
        } else if (!(element instanceof PsiIdentifier)) {
            return null;
        } else {
            element.getStartOffsetInParent();
            PsiElement layout = element.getParent().getFirstChild();
            if (layout == null) {
                return null;
            } else if (!"R.layout".equals(layout.getText())) {
                return null;
            } else {
                Project project = element.getProject();
                String name = String.format("%s.xml", element.getText());
                return resolveLayoutResourceFile(element, project, name);
            }
        }
    }

    private static PsiFile resolveLayoutResourceFile(PsiElement element, Project project, String name) {
        Module module = ModuleUtil.findModuleForPsiElement(element);
        PsiFile[] files = null;
        if (module != null) {
            GlobalSearchScope moduleScope = module.getModuleWithDependenciesAndLibrariesScope(false);
            String[] strs = FilenameIndex.getAllFilenames(project);
            files = FilenameIndex.getFilesByName(project, name, moduleScope);
        }

        if (files == null || files.length <= 0) {
            files = FilenameIndex.getFilesByName(project, name, new EverythingGlobalScope(project));
            if (files.length <= 0) {
                return null;
            }
        }

        return files[0];
    }

    @Nullable
    public static PsiClass getTargetClass(Editor editor, PsiFile file) {
        int offset = editor.getCaretModel().getOffset();
        PsiElement element = file.findElementAt(offset);
        if (element == null) {
            return null;
        } else {
            PsiClass target = (PsiClass)PsiTreeUtil.getParentOfType(element, PsiClass.class);
            return target instanceof SyntheticElement ? null : target;
        }
    }

    public static ArrayList<MElement> getIDsFromLayout(final PsiFile file, final ArrayList<MElement> elements) {
        file.accept(new XmlRecursiveElementVisitor() {
            public void visitElement(PsiElement element) {
                super.visitElement(element);
                if (element instanceof XmlTag) {
                    XmlTag tag = (XmlTag)element;
                    XmlAttribute id;
                    if (tag.getName().equalsIgnoreCase("include")) {
                        id = tag.getAttribute("layout", (String)null);
                        if (id != null) {
                            Project project = file.getProject();
                            PsiFile include = Utils.findLayoutResource(file, project, Utils.getLayoutName(id.getValue()));
                            if (include != null) {
                                Utils.getIDsFromLayout(include, elements);
                                return;
                            }
                        }
                    }

                    id = tag.getAttribute("android:id", (String)null);
                    if (id == null) {
                        return;
                    }

                    String value = id.getValue();
                    if (value == null) {
                        return;
                    }

                    XmlAttribute onclick = tag.getAttribute("android:onClick", (String)null);
                    String clickstr = null;
                    if (onclick != null) {
                        clickstr = onclick.getValue();
                    }

                    XmlAttribute text = tag.getAttribute("android:text", (String)null);
                    String textstr = null;
                    if (onclick != null) {
                        textstr = text.getValue();
                    }

                    String name = tag.getName();
                    XmlAttribute clazz = tag.getAttribute("class", (String)null);
                    if (clazz != null) {
                        name = clazz.getValue();
                    }

                    try {
                        MElement nele = new MElement(name, value);
                        nele.onClick = clickstr;
                        nele.text = textstr;
                        elements.add(nele);
                    } catch (IllegalArgumentException var12) {
                    }
                }

            }
        });
        return elements;
    }

    public static String getLayoutName(String layout) {
        if (layout != null && layout.startsWith("@") && layout.contains("/")) {
            String[] parts = layout.split("/");
            return parts.length != 2 ? null : parts[1];
        } else {
            return null;
        }
    }

    public static PsiFile findLayoutResource(PsiFile file, Project project, String fileName) {
        String name = String.format("%s.xml", fileName);
        return resolveLayoutResourceFile(file, project, name);
    }

    public static String getPrefix() {
        if (PropertiesComponent.getInstance().isValueSet("mdxandplus_prefix")) {
            return PropertiesComponent.getInstance().getValue("mdxandplus_prefix");
        } else {
            CodeStyleSettingsManager manager = CodeStyleSettingsManager.getInstance();
            CodeStyleSettings settings = manager.getCurrentSettings();
            return settings.FIELD_NAME_PREFIX;
        }
    }

    public static boolean isEmptyString(String text) {
        return text == null || text.trim().length() == 0;
    }

    public static String getViewHolderClassName() {
        return PropertiesComponent.getInstance().getValue("mdxandplus_viewholder_class_name", "ViewHolder");
    }
}
