//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax.parser;

import com.xfltr.hapax.TemplateDictionary;
import com.xfltr.hapax.TemplateLoaderContext;
import java.io.PrintWriter;

public class SectionNode extends TemplateNode {
    private final String sectionName_;
    private final SectionNode.TYPE type_;

    public void evaluate(TemplateDictionary dict, TemplateLoaderContext context, PrintWriter collector) {
    }

    public boolean isOpenSectionTag() {
        return this.type_ == SectionNode.TYPE.OPEN;
    }

    public boolean isCloseSectionTag() {
        return this.type_ == SectionNode.TYPE.CLOSE;
    }

    private SectionNode(String nodeName, SectionNode.TYPE node_type) {
        this.sectionName_ = nodeName;
        this.type_ = node_type;
    }

    public String getSectionName() {
        return this.sectionName_;
    }

    public static SectionNode open(String nodeName) {
        return new SectionNode(nodeName, SectionNode.TYPE.OPEN);
    }

    public static SectionNode close(String nodeName) {
        return new SectionNode(nodeName, SectionNode.TYPE.CLOSE);
    }

    static enum TYPE {
        OPEN,
        CLOSE;

        private TYPE() {
        }
    }
}
