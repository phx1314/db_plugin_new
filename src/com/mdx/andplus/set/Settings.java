//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.set;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.mdx.andplus.common.Utils;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

public class Settings implements Configurable {
    public static final String PREFIX = "mdxandplus_prefix";
    public static final String VIEWHOLDER_CLASS_NAME = "mdxandplus_viewholder_class_name";
    private JPanel mPanel;
    private JTextField mHolderName;
    private JTextField mPrefix;

    public Settings() {
    }

    @Nls
    public String getDisplayName() {
        return "ButterKnifeZelezny";
    }

    @Nullable
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    public JComponent createComponent() {
        this.reset();
        return this.mPanel;
    }

    public boolean isModified() {
        return true;
    }

    public void apply() throws ConfigurationException {
        PropertiesComponent.getInstance().setValue("mdxandplus_prefix", this.mPrefix.getText());
        PropertiesComponent.getInstance().setValue("mdxandplus_viewholder_class_name", this.mHolderName.getText());
    }

    public void reset() {
        this.mPrefix.setText(Utils.getPrefix());
        this.mHolderName.setText(Utils.getViewHolderClassName());
    }

    public void disposeUIResources() {
    }
}
