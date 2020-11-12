//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.common;

import com.intellij.openapi.vfs.VirtualFile;
import com.mdx.andplus.common.CreateFitXml.Dimclass;
import com.mdx.andplus.layout.commons.XMLReader;
import com.mdx.andplus.util.AdressUtil;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchXMLTextValues {
    private Element rootele;
    private Document doc;
    private VirtualFile xmlFile;
    public VirtualFile moduleFile;

    public SearchXMLTextValues(VirtualFile xmlFile, VirtualFile moduleFile) throws Exception {
        SAXBuilder sb = new SAXBuilder();
        this.doc = sb.build(xmlFile.getInputStream());
        this.xmlFile = xmlFile;
        this.moduleFile = moduleFile;
        this.rootele = this.doc.getRootElement();
    }

    public void searchText(Element ele) throws Exception {
        if (ele == null) {
            ele = this.rootele;
        }

        String v;
        Iterator var14 = ele.getAttributes().iterator();

        while (var14.hasNext()) {
            Object obj = var14.next();
            v = null;
            Attribute atb = null;
            if (obj instanceof Attribute) {
                atb = (Attribute) obj;
                v = atb.getValue();
                String n;
                float f;
                if ((atb.getName().equals("text") || atb.getName().equals("hint")) && !v.equals(AdressUtil.getPinyin(v))) {
                    atb.setValue("@string/" + xmlFile.getName().substring(0, xmlFile.getName().length() - 4) + "_" + stringFilter(AdressUtil.getPinyin(v)));
                    addString(xmlFile.getName().substring(0, xmlFile.getName().length() - 4) + "_" + stringFilter(AdressUtil.getPinyin(v)), v);
                }
            }
        }

        var14 = ele.getChildren().iterator();

        while (var14.hasNext()) {
            Element obj = (Element) var14.next();
            this.searchText(obj);
        }

    }

    public void addString(String name, String text) {
        VirtualFile srcFile = this.moduleFile.findChild("src");
        VirtualFile stringFile = srcFile.findChild("main").findChild("res").findChild("values").findChild("strings.xml");
        try {
            XMLReader xmlReader = new XMLReader(stringFile);
            Element application = xmlReader.getElementOne("//resources");
            for (Element e : application.getChildren()) {
                if (e.getAttributeValue("name").equals(name)) {
                    return;
                }
            }
            Element ele = new Element("string");
            ele.setAttribute("name", name);
            ele.setText(text);
            application.addContent(ele);
            xmlReader.save(stringFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() throws Exception {
        Format format = Format.getCompactFormat();
        format.setIndent("     ");
        format.setEncoding("UTF-8");
        XMLOutputter out = new XMLOutputter(format);
        OutputStreamWriter fw = new OutputStreamWriter(this.xmlFile.getOutputStream((Object) null), "UTF-8");
        out.output(this.doc, fw);
        fw.close();
    }

    /**
     * 过滤特殊字符
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
