//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EztParser implements TemplateParser {
    private static final Logger logger_ = Logger.getLogger(EztParser.class.getSimpleName());
    private static final String TOK_LITERAL = "([^\\[]+)";
    private static final String TOK_IDENT = "[a-z][a-z0-9_.-]*";
    private static final String TOK_DIRECTIVE = "[a-z_-]+";
    private static final String TOK_DIRECTIVE_ARGS = "(([a-z][a-z0-9_.-]*)|(\\\"[^\"]*\\\")|([a-z][a-z0-9_.-]* \\\"[^\"]*\\\"))";
    private static final String STMT_VARIABLE_DEREF = "(\\[[a-z][a-z0-9_.-]*\\])";
    private static final String STMT_DIRECTIVE = "(\\[([a-z_-]+)\\s+(([a-z][a-z0-9_.-]*)|(\\\"[^\"]*\\\")|([a-z][a-z0-9_.-]* \\\"[^\"]*\\\"))\\s*\\])";
    private static final String STMT_BRACKET = "(\\[\\[\\])";
    private static final String STMT_COMMENT = "(\\[#[^\\]]*\\])";
    private static final Pattern PATTERN = Pattern.compile("([^\\[]+)|(\\[\\[\\])|(\\[#[^\\]]*\\])|(\\[[a-z][a-z0-9_.-]*\\])|(\\[([a-z_-]+)\\s+(([a-z][a-z0-9_.-]*)|(\\\"[^\"]*\\\")|([a-z][a-z0-9_.-]* \\\"[^\"]*\\\"))\\s*\\])", 10);

    private EztParser() {
    }

    public static EztParser create() {
        return new EztParser();
    }

    public List<TemplateNode> parse(String input) throws TemplateParserException {
        List<TemplateNode> node_list = new LinkedList();
        if (input != null && input.length() != 0) {
            Matcher matcher = PATTERN.matcher(input);

            while(matcher.find()) {
                for(int i = 1; i <= matcher.groupCount(); ++i) {
                    if (matcher.group(i) != null) {
                        switch(i) {
                            case 1:
                                node_list.add(TextNode.create(matcher.group(i)));
                                break;
                            case 2:
                                node_list.add(TextNode.create("["));
                            case 3:
                            default:
                                break;
                            case 4:
                                this.handleVariable(node_list, matcher);
                                break;
                            case 5:
                                this.handleDirective(node_list, matcher);
                        }
                    }
                }
            }

            return node_list;
        } else {
            return node_list;
        }
    }

    private void handleVariable(List<TemplateNode> node_list, Matcher matcher) throws TemplateParserException {
        String text = matcher.group(4);
        if (text.matches("\\[(is|if-any|define|include|insertfile|format)\\]")) {
            throw new TemplateParserException("You cannot dereference variables named after reserved words: " + text);
        } else {
            if (text.equals("[end]")) {
                node_list.add(EztEndNode.create());
            } else if (text.equals("[else]")) {
                node_list.add(EztElseNode.create());
            } else {
                node_list.add(VariableNode.parse(matcher.group(4).replaceAll("(^\\[)|(\\]$)", "")));
            }

        }
    }

    private void handleDirective(List<TemplateNode> node_list, Matcher matcher) throws TemplateParserException {
        String directive = matcher.group(6);
        String one_parameter = matcher.group(8);
        String quoted_include_parameter = matcher.group(9);
        String two_parameters = matcher.group(10);
        if (!directive.equals("include") && !directive.equals("insertfile")) {
            if (directive.equals("define")) {
                node_list.add(EztDefineNode.parse(one_parameter));
            } else if (directive.equals("is")) {
                if (two_parameters == null) {
                    throw new TemplateParserException("[is] requires two parameters.");
                }

                String[] tokenized = two_parameters.split("\\s+");
                String varname = tokenized[0];
                String value = tokenized[1].replace("\"", "");
                node_list.add(EztConditionalNode.is(varname, value));
            } else if (directive.equals("if-any")) {
                if (one_parameter == null) {
                    throw new TemplateParserException("[if-any] requires one parameter.");
                }

                node_list.add(EztConditionalNode.ifAny(one_parameter));
            } else if (directive.equals("format")) {
                logger_.info("encountered a [format] directive; ignoring.");
            }
        } else if (one_parameter != null) {
            node_list.add(EztIncludeNode.parse(one_parameter));
        } else {
            node_list.add(EztIncludeNode.parse(quoted_include_parameter));
        }

    }
}
