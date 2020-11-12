//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.layout.model;

public class AutoView {
    public String name;
    public String type;
    public String clas;
    public String id;
    public String onclick;
    public String text;

    public AutoView() {
    }

    public AutoView(String name, String type) {
        this.name = name.substring(name.indexOf("/") + 1);
        this.id = "R." + name.substring(2, name.indexOf("/")) + "." + this.name;
        this.type = type;
        if (type.indexOf(".") > 0) {
            this.clas = type;
            this.type = type.substring(type.lastIndexOf(".") + 1);
        } else if ("View".equals(type)) {
            this.clas = "android.view.View";
        } else {
            this.clas = "android.widget." + type;
        }

    }

    public void set(String name, String type) {
        this.name = name.substring(name.indexOf("/") + 1);
        this.id = "R." + name.substring(2, name.indexOf("/")) + "." + this.name;
        this.type = type;
        if (type.indexOf(".") > 0) {
            this.clas = type;
            this.type = type.substring(type.lastIndexOf(".") + 1);
        } else if ("View".equals(type)) {
            this.clas = "android.view.View";
        } else if (type.equals("WebView")) {
            this.clas = "android.webkit." + type;
        } else {
            this.clas = "android.widget." + type;
        }

    }
}
