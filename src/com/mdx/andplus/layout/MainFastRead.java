//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.layout;

import com.intellij.openapi.vfs.VirtualFile;
import com.mdx.andplus.layout.commons.XMLReader;
import java.util.Iterator;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.xpath.XPath;

public class MainFastRead {
    private XMLReader xmlReader;
    private Attribute appackage;
    private VirtualFile projectpath;
    private String apppackagePath;
    private Element application;
    private VirtualFile file;

    public MainFastRead(VirtualFile file, VirtualFile projectpath) throws Exception {
        this.projectpath = projectpath;
        this.file = file;
        this.xmlReader = new XMLReader(file);
        this.appackage = this.xmlReader.getNodeOne("//manifest/@package");
        this.application = this.xmlReader.getElementOne("//application");
    }

    public Attribute getAppackage() {
        return this.appackage;
    }

    public void setAppackage(Attribute appackage) {
        this.appackage = appackage;
    }

    public String getAppackageName() {
        return this.appackage.getValue();
    }

    public String getApppackagePath() {
        return this.apppackagePath;
    }

    public void setApppackagePath(String apppackagePath) {
        this.apppackagePath = apppackagePath;
    }

    public VirtualFile getProjectpath() {
        return this.projectpath;
    }

    public void setProjectpath(VirtualFile projectpath) {
        this.projectpath = projectpath;
    }

    public void addActivity(String classname) {
        if (!this.findAppackage(classname)) {
            Element ele = new Element("activity");
            ele.setAttribute("name", classname, XMLReader.ns);
            ele.setAttribute("screenOrientation", "portrait", XMLReader.ns);
            this.application.addContent(ele);
        }
    }

    public void addMainActivity(String classname) {
        if (!this.findAppackage(classname)) {
            Element ele = new Element("activity");
            ele.setAttribute("name", classname, XMLReader.ns);
            ele.setAttribute("screenOrientation", "portrait", XMLReader.ns);
            Element ifi = new Element("intent-filter");
            Element act = new Element("action");
            act.setAttribute("name", "android.intent.action.MAIN", XMLReader.ns);
            ifi.addContent(act);
            act = new Element("category");
            act.setAttribute("name", "android.intent.category.LAUNCHER", XMLReader.ns);
            ifi.addContent(act);
            ele.addContent(ifi);
            this.application.addContent(ele);
        }
    }

    public void save(VirtualFile file) throws Exception {
        this.xmlReader.save(file);
    }

    public boolean findAppackage(String appackage) {
        List<?> subList = this.application.getChildren();

        for(int i = 0; i < subList.size(); ++i) {
            Object node = subList.get(i);
            if (node instanceof Element) {
                Element ele = (Element)node;

                try {
                    List<?> list = XPath.selectNodes(ele, "//activity/@android:name");
                    Iterator var7 = list.iterator();

                    while(var7.hasNext()) {
                        Object obj = var7.next();
                        Attribute nod = (Attribute)obj;
                        if (nod.getValue().startsWith(".")) {
                            if ((this.getAppackageName() + nod.getValue()).equals(appackage)) {
                                return false;
                            }
                        } else if (nod.getValue().startsWith(appackage)) {
                            return true;
                        }
                    }
                } catch (Exception var10) {
                }
            }
        }

        return false;
    }
}
