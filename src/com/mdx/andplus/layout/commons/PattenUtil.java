//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.layout.commons;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PattenUtil {
    public ArrayList<String> mPatten = new ArrayList();
    public boolean isPatten = false;

    public PattenUtil() {
    }

    public boolean isPatten(String str, String patten) {
        this.mPatten.clear();
        Pattern pattern = Pattern.compile(patten);
        Matcher matcher = pattern.matcher(str);
        if (!matcher.find()) {
            return false;
        } else {
            for(int i = 1; i <= matcher.groupCount(); ++i) {
                this.mPatten.add(matcher.group(i));
            }

            this.isPatten = true;
            return true;
        }
    }

    public ArrayList<String> getPattens() {
        return this.isPatten ? this.mPatten : null;
    }

    public String getPatten() {
        return this.isPatten && this.mPatten.size() > 0 ? (String)this.mPatten.get(0) : null;
    }
}
