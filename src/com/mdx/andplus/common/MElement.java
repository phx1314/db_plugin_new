//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MElement {
    private static final Pattern sIdPattern = Pattern.compile("@\\+?(android:)?id/([^$]+)$", 2);
    private static final Pattern sValidityPattern = Pattern.compile("^([a-zA-Z_\\$][\\w\\$]*)$", 2);
    public String id;
    public boolean isAndroidNS = false;
    public String nameFull;
    public String name;
    public String fieldName;
    public String onClick;
    public String text;
    public boolean isValid = false;
    public boolean used = true;
    public boolean isClick = true;

    public MElement(String name, String id) {
        Matcher matcher = sIdPattern.matcher(id);
        if (matcher.find() && matcher.groupCount() > 0) {
            this.id = matcher.group(2);
            String androidNS = matcher.group(1);
            this.isAndroidNS = androidNS != null && androidNS.length() != 0;
        }

        String[] packages = name.split("\\.");
        if (packages.length > 1) {
            this.nameFull = name;
            this.name = packages[packages.length - 1];
        } else {
            this.nameFull = null;
            this.name = name;
        }

        this.fieldName = this.getFieldName();
    }

    public String getFullID() {
        StringBuilder fullID = new StringBuilder();
        String rPrefix;
        if (this.isAndroidNS) {
            rPrefix = "android.R.id.";
        } else {
            rPrefix = "R.id.";
        }

        fullID.append(rPrefix);
        fullID.append(this.id);
        return fullID.toString();
    }

    private String getFieldName() {
        return this.id;
    }

    public boolean checkValidity() {
        Matcher matcher = sValidityPattern.matcher(this.fieldName);
        this.isValid = matcher.find();
        return this.isValid;
    }
}
