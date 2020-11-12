//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CTemplateParser implements TemplateParser {
    private static final Logger logger = Logger.getLogger(CTemplateParser.class.getSimpleName());
    private static final int RE_FLAGS = 10;
    private static final String OPEN_SQUIGGLE = Pattern.quote("{{");
    private static final String CLOSE_SQUIGGLE = Pattern.quote("}}");
    private static final String VARIABLE_RE = "([a-zA-Z_]+(:[a-zA-Z]+)*)*";
    private final Pattern RE_OPEN_SECTION;
    private final Pattern RE_CLOSE_SECTION;
    private final Pattern RE_VARIABLE;
    private final Pattern RE_INCLUDE;

    public static CTemplateParser create() {
        return new CTemplateParser();
    }

    private CTemplateParser() {
        this.RE_OPEN_SECTION = Pattern.compile(OPEN_SQUIGGLE + "#([a-zA-Z_]+)" + CLOSE_SQUIGGLE, 10);
        this.RE_CLOSE_SECTION = Pattern.compile(OPEN_SQUIGGLE + "/([a-zA-Z_]+)" + CLOSE_SQUIGGLE, 10);
        this.RE_VARIABLE = Pattern.compile(OPEN_SQUIGGLE + "([a-zA-Z_]+(:[a-zA-Z]+)*)*" + CLOSE_SQUIGGLE, 10);
        this.RE_INCLUDE = Pattern.compile(OPEN_SQUIGGLE + ">" + "([a-zA-Z_]+(:[a-zA-Z]+)*)*" + CLOSE_SQUIGGLE, 10);
    }

    private CTemplateParser.NODE_TYPE next(StringBuilder input) {
        if (input.toString().startsWith("{{#")) {
            return CTemplateParser.NODE_TYPE.OPEN_SECTION;
        } else if (input.toString().startsWith("{{/")) {
            return CTemplateParser.NODE_TYPE.CLOSE_SECTION;
        } else if (input.toString().startsWith("{{>")) {
            return CTemplateParser.NODE_TYPE.INCLUDE_SECTION;
        } else {
            return input.toString().startsWith("{{") ? CTemplateParser.NODE_TYPE.VARIABLE : CTemplateParser.NODE_TYPE.TEXT_NODE;
        }
    }

    private String consume(Pattern p, StringBuilder input) throws TemplateParserException {
        Matcher m = p.matcher(input);
        if (m.lookingAt()) {
            String string_to_return = m.group(1);
            input.delete(0, m.end());
            logger.finest("consumed '" + string_to_return + "'");
            return string_to_return;
        } else {
            throw new TemplateParserException("Unexpected or malformed input: " + input);
        }
    }

    private void handleTextNode(StringBuilder input, List<TemplateNode> nodes) {
        int next_braces = input.indexOf("{{");
        String text;
        if (next_braces == -1) {
            text = input.toString();
            input.setLength(0);
            input.trimToSize();
        } else {
            text = input.substring(0, next_braces);
            input.delete(0, next_braces);
        }

        if (text.length() > 0) {
            logger.finest("found text node '" + text + "'");
            nodes.add(TextNode.create(text));
        }

    }

    public List<TemplateNode> parse(String template) throws TemplateParserException {
        List<TemplateNode> nodes = new ArrayList();
        StringBuilder input = new StringBuilder(template);

        while(input.length() > 0) {
            logger.finest("looking ahead at '" + input + "'");
            switch(this.next(input)) {
                case OPEN_SECTION:
                    this.handleOpenSection(input, nodes);
                    break;
                case CLOSE_SECTION:
                    this.handleCloseSection(input, nodes);
                    break;
                case VARIABLE:
                    this.handleVariable(input, nodes);
                    break;
                case TEXT_NODE:
                    this.handleTextNode(input, nodes);
                    break;
                case INCLUDE_SECTION:
                    this.handleInclude(input, nodes);
                    break;
                default:
                    throw new RuntimeException("Internal error parsing template.");
            }
        }

        return nodes;
    }

    private void handleInclude(StringBuilder input, List<TemplateNode> nodes) throws TemplateParserException {
        String consumed = this.consume(this.RE_INCLUDE, input);
        nodes.add(IncludeNode.parse(consumed));
    }

    private void handleVariable(StringBuilder input, List<TemplateNode> nodes) throws TemplateParserException {
        logger.finest("consuming VARIABLE with regex " + this.RE_VARIABLE.pattern());
        String consumed = this.consume(this.RE_VARIABLE, input);
        nodes.add(VariableNode.parse(consumed));
    }

    private void handleCloseSection(StringBuilder input, List<TemplateNode> nodes) throws TemplateParserException {
        logger.finest("consuming CLOSE SECTION with regex " + this.RE_CLOSE_SECTION.pattern());
        String consumed = this.consume(this.RE_CLOSE_SECTION, input);
        logger.finest("CLOSING " + consumed);
        nodes.add(SectionNode.close(consumed));
    }

    private void handleOpenSection(StringBuilder input, List<TemplateNode> nodes) throws TemplateParserException {
        logger.finest("consuming OPEN SECTION with regex " + this.RE_OPEN_SECTION.pattern());
        String consumed = this.consume(this.RE_OPEN_SECTION, input);
        logger.finest("OPENING " + consumed);
        nodes.add(SectionNode.open(consumed));
    }

    private static enum NODE_TYPE {
        OPEN_SECTION,
        CLOSE_SECTION,
        VARIABLE,
        TEXT_NODE,
        INCLUDE_SECTION;

        private NODE_TYPE() {
        }
    }
}
