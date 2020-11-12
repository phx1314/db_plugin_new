//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.common;

import com.intellij.openapi.vfs.VirtualFile;
import com.mdx.andplus.common.CreateFitXml.Dimclass;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class SearchXMLValues {
    public static final String DIMEN = "@dimen/";
    public static final String DP = "dp";
    public static final String SP = "sp";
    private Element rootele;
    private Document doc;
    private VirtualFile xmlFile;

    public SearchXMLValues(VirtualFile xmlFile) throws Exception {
        SAXBuilder sb = new SAXBuilder();
        this.doc = sb.build(xmlFile.getInputStream());
        this.xmlFile = xmlFile;
        this.rootele = this.doc.getRootElement();
    }

    public void searchdp(List<Dimclass> dplist, List<Dimclass> splist, Element ele) throws Exception {
        if (ele == null) {
            ele = this.rootele;
        }

        String v;
        if (ele.getName().equals("dimen")) {
            v = ele.getText();
            String s;
            float f;
            if (v.startsWith("@dimen/") || v.endsWith("dp")) {
                s = v.replaceAll("@dimen/", "").replaceAll("f", "-").replace("j", "").replaceAll("dp", "").replaceAll("_", ".");

                try {
                    f = Float.valueOf(s);
                    if (f != 0.0F) {
                        this.addListNoRepead(dplist, new Dimclass(ele.getAttributeValue("name"), f));
                    }
                } catch (Exception var13) {
                }
            }

            if (!v.startsWith("@dimen/") && v.endsWith("dp")) {
                s = v.substring(0, v.length() - 2);

                try {
                    f = Float.valueOf(s);
                    if (f != 0.0F) {
                        this.addListNoRepead(dplist, new Dimclass(ele.getAttributeValue("name"), f));
                    }
                } catch (Exception var12) {
                }
            }
        }

        Iterator var14 = ele.getAttributes().iterator();

        while(var14.hasNext()) {
            Object obj = var14.next();
            v = null;
            Attribute atb = null;
            if (obj instanceof Attribute) {
                atb = (Attribute)obj;
                v = atb.getValue();
                String n;
                float f;
                if (!v.startsWith("@dimen/") && v.endsWith("dp")) {
                    n = v.substring(0, v.length() - 2);

                    try {
                        f = Float.valueOf(n);
                        if (f != 0.0F) {
                            this.addListNoRepead(dplist, new Dimclass(f));
                            atb.setValue("@dimen/" + getDimName(f, "dp"));
                        }
                    } catch (Exception var11) {
                    }
                }

                if (v.startsWith("@dimen/") && v.endsWith("dp")) {
                    n = v.replaceAll("@dimen/", "").replaceAll("f", "-").replace("j", "").replaceAll("dp", "").replaceAll("_", ".");

                    try {
                        f = Float.valueOf(n);
                        if (f != 0.0F) {
                            this.addListNoRepead(dplist, new Dimclass(f));
                        }
                    } catch (Exception var10) {
                    }
                }
            }
        }

        var14 = ele.getChildren().iterator();

        while(var14.hasNext()) {
            Element obj = (Element)var14.next();
            this.searchdp(dplist, splist, obj);
        }

    }

    public void save() throws Exception {
        Format format = Format.getCompactFormat();
        format.setIndent("     ");
        format.setEncoding("UTF-8");
        XMLOutputter out = new XMLOutputter(format);
        OutputStreamWriter fw = new OutputStreamWriter(this.xmlFile.getOutputStream((Object)null), "UTF-8");
        out.output(this.doc, fw);
        fw.close();
    }

    public void addListNoRepead(List<Dimclass> list, Dimclass f) {
        Iterator var3 = list.iterator();

        Dimclass fl;
        do {
            do {
                if (!var3.hasNext()) {
                    list.add(f);
                    return;
                }

                fl = (Dimclass)var3.next();
            } while(fl.value != f.value);
        } while((fl.name != null || f.name != null) && !fl.name.equals(f.name));

    }

    public static String getDimName(float f, String dw) {
        float lw = f - (float)((int)f);
        return lw == 0.0F ? ((f > 0.0F ? "j" : "f") + (int)f + dw).replace(".", "_").replace("-", "") : ((f > 0.0F ? "j" : "f") + f + dw).replace(".", "_").replace("-", "");
    }
}
