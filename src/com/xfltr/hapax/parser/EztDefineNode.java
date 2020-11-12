//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax.parser;

import com.xfltr.hapax.TemplateDictionary;
import com.xfltr.hapax.TemplateException;
import com.xfltr.hapax.TemplateLoaderContext;
import java.io.PrintWriter;
import java.util.List;

public class EztDefineNode extends TemplateNode {
    private final String variableName_;

    public String getVariableName() {
        return this.variableName_;
    }

    private EztDefineNode(String s) {
        this.variableName_ = s;
    }

    public static TemplateNode parse(String s) {
        return new EztDefineNode(s);
    }

    public void evaluate(TemplateDictionary dict, TemplateLoaderContext context, PrintWriter collector) throws TemplateException {
    }

    public Range advise(List<TemplateNode> nodes, int define_node_idx) throws TemplateException {
        int other_sections = 0;
        int end_node = -1;

        for(int i = define_node_idx; i < nodes.size(); ++i) {
            TemplateNode node = (TemplateNode)nodes.get(i);
            if (node instanceof EztDefineNode) {
                ++other_sections;
            } else if (node instanceof EztEndNode) {
                --other_sections;
                if (other_sections == 0) {
                    end_node = i;
                    break;
                }
            }
        }

        if (end_node == -1) {
            throw new TemplateException("Unable to find matching [end] node.");
        } else {
            return new Range(define_node_idx + 1, end_node, end_node);
        }
    }
}
