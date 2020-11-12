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

public class EztConditionalNode extends TemplateNode {
    private final EztConditionalNode.Behavior behavior;

    private EztConditionalNode(EztConditionalNode.Behavior b) {
        this.behavior = b;
    }

    public static EztConditionalNode ifAny(final String varname) {
        return new EztConditionalNode(new EztConditionalNode.Behavior() {
            public boolean trueBranch(TemplateDictionary td) {
                return td.contains(varname) && td.get(varname).length() > 0;
            }
        });
    }

    public static EztConditionalNode is(final String varname, final String expected) {
        return new EztConditionalNode(new EztConditionalNode.Behavior() {
            public boolean trueBranch(TemplateDictionary td) {
                return td.contains(varname) && td.get(varname).equals(expected);
            }
        });
    }

    public Range advise(List<TemplateNode> nodes, int is_node_idx, TemplateDictionary dict) throws TemplateException {
        int other_sections = 0;
        int end_node = -1;
        int else_node = -1;

        for(int i = is_node_idx; i < nodes.size(); ++i) {
            TemplateNode node = (TemplateNode)nodes.get(i);
            if (node instanceof EztConditionalNode) {
                ++other_sections;
            } else if (node instanceof EztElseNode && other_sections == 1) {
                else_node = i;
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
        } else if (this.behavior.trueBranch(dict)) {
            return else_node == -1 ? new Range(is_node_idx + 1, end_node, end_node) : new Range(is_node_idx + 1, else_node, end_node);
        } else {
            return else_node == -1 ? new Range(end_node, end_node, end_node) : new Range(else_node, end_node, end_node);
        }
    }

    public void evaluate(TemplateDictionary dict, TemplateLoaderContext context, PrintWriter collector) throws TemplateException {
    }

    private interface Behavior {
        boolean trueBranch(TemplateDictionary var1);
    }
}
