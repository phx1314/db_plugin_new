//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.common;

import com.intellij.openapi.vfs.VirtualFile;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ImageSelecter {
    private static final String SCHEMA = "http://schemas.android.com/apk/res/android";
    private static final String NS = "android";
    public ArrayList<ImageSelecter.Item> selecter = new ArrayList();
    public HashMap<String, ImageSelecter.Item> states = new HashMap();
    public String name;

    public ImageSelecter() {
    }

    public void add(ImageSelecter.Item item) {
        this.name = item.name;
        this.states.put(item.state, item);
        this.selecter.add(item);
    }

    public void createXml(VirtualFile resFile, VirtualFile dford) throws Exception {
        Element root = new Element("selector");
        Namespace ns = Namespace.getNamespace("android", "http://schemas.android.com/apk/res/android");
        root.addNamespaceDeclaration(ns);
        Iterator var5 = this.selecter.iterator();

        while(true) {
            while(var5.hasNext()) {
                ImageSelecter.Item it = (ImageSelecter.Item)var5.next();
                String drn = "@" + it.path + "/" + it.img;
                Element item;
                if (it.state.equals("n")) {
                    item = new Element("item");
                    item.setAttribute("drawable", drn, ns);
                    root.addContent(item);
                } else if (!it.state.equals("p") && !it.state.equals("h")) {
                    if (it.state.equals("s")) {
                        item = new Element("item");
                        item.setAttribute("drawable", drn, ns);
                        item.setAttribute("state_checked", "true", ns);
                        root.addContent(item);
                        item = new Element("item");
                        item.setAttribute("drawable", drn, ns);
                        item.setAttribute("state_selected", "true", ns);
                        root.addContent(item);
                        if (!this.states.containsKey("f")) {
                            item = new Element("item");
                            item.setAttribute("drawable", drn, ns);
                            item.setAttribute("state_focused", "true", ns);
                            root.addContent(item);
                        }
                    } else if (it.state.equals("f")) {
                        item = new Element("item");
                        item.setAttribute("drawable", drn, ns);
                        item.setAttribute("state_focused", "true", ns);
                        root.addContent(item);
                    }
                } else {
                    item = new Element("item");
                    item.setAttribute("drawable", drn, ns);
                    item.setAttribute("state_pressed", "true", ns);
                    root.addContent(item);
                    if (!this.states.containsKey("s")) {
                        item = new Element("item");
                        item.setAttribute("drawable", drn, ns);
                        item.setAttribute("state_checked", "true", ns);
                        root.addContent(item);
                        item = new Element("item");
                        item.setAttribute("drawable", drn, ns);
                        item.setAttribute("state_selected", "true", ns);
                        root.addContent(item);
                        if (!this.states.containsKey("f")) {
                            item = new Element("item");
                            item.setAttribute("drawable", drn, ns);
                            item.setAttribute("state_focused", "true", ns);
                            root.addContent(item);
                        }
                    }
                }
            }

            VirtualFile xmlfile = dford.findOrCreateChildData((Object)null, this.name + ".xml");
            Document doc = new Document();
            doc.addContent(root);
            Format format = Format.getCompactFormat();
            format.setIndent("     ");
            format.setEncoding("UTF-8");
            XMLOutputter out = new XMLOutputter(format);
            OutputStreamWriter fw = new OutputStreamWriter(xmlfile.getOutputStream((Object)null), "UTF-8");
            out.output(doc, fw);
            fw.close();
            return;
        }
    }

    public static class Item {
        public String name = "";
        public String img = "";
        public String state = "";
        public String path = "";
        public String xpath = "";
        public boolean mmove = false;
        public VirtualFile vfile;

        public Item() {
        }
    }
}
