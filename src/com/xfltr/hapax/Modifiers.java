//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Modifiers {
    private Modifiers() {
    }

    public static String jsEscape(String unescaped) {
        String also_safe = "_ ";
        StringBuilder escaped = new StringBuilder();

        for(int i = 0; i < unescaped.length(); ++i) {
            char ch = unescaped.charAt(i);
            if (!Character.isLetterOrDigit(ch) && "_ ".indexOf(ch) == -1) {
                if (ch < 256) {
                    escaped.append("\\x").append(Integer.toHexString(ch));
                } else {
                    escaped.append("\\u").append(String.format("%04x", Integer.valueOf(ch)));
                }
            } else {
                escaped.append(ch);
            }
        }

        return escaped.toString();
    }

    private static String urlEncode(String unescaped) {
        try {
            return URLEncoder.encode(unescaped, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new RuntimeException(var2);
        }
    }

    private static String xmlEscape(String unescaped) {
        StringBuilder escaped = new StringBuilder();

        for(int i = 0; i < unescaped.length(); ++i) {
            char ch = unescaped.charAt(i);
            if (!Character.isLetterOrDigit(ch) && ch != ' ') {
                escaped.append("&#").append(ch).append(";");
            } else {
                escaped.append(ch);
            }
        }

        return escaped.toString();
    }

    private static String htmlEscape(String unescaped) {
        StringBuilder escaped = new StringBuilder();

        for(int i = 0; i < unescaped.length(); ++i) {
            char ch = unescaped.charAt(i);
            if (!Character.isLetterOrDigit(ch) && ch != ' ') {
                if (ch == '&') {
                    escaped.append("&amp;");
                } else if (ch == '"') {
                    escaped.append("&quot;");
                } else if (ch != '\r' && ch != '\n' && ch != '\t') {
                    escaped.append("&#").append(ch).append(";");
                } else {
                    escaped.append(ch);
                }
            } else {
                escaped.append(ch);
            }
        }

        return escaped.toString();
    }

    public static String newlinesToBreaks(String t) {
        return t.replaceAll("\n", "<br/>");
    }

    public static String applyModifiers(String input, List<Modifiers.FLAGS> modifiers) {
        Iterator var2 = modifiers.iterator();

        while(var2.hasNext()) {
            Modifiers.FLAGS modifier = (Modifiers.FLAGS)var2.next();
            switch(modifier) {
                case H:
                    input = htmlEscape(input);
                    break;
                case X:
                    input = xmlEscape(input);
                    break;
                case J:
                    input = jsEscape(input);
                    break;
                case U:
                    input = urlEncode(input);
                    break;
                case B:
                    input = newlinesToBreaks(input);
            }
        }

        return input;
    }

    public static List<Modifiers.FLAGS> parseModifiers(String[] split) {
        List<Modifiers.FLAGS> list = new ArrayList(10);

        for(int i = 1; i < split.length; ++i) {
            list.add(Modifiers.FLAGS.valueOf(split[i].toUpperCase()));
        }

        return list;
    }

    public static enum FLAGS {
        H,
        X,
        J,
        U,
        B;

        private FLAGS() {
        }
    }
}
