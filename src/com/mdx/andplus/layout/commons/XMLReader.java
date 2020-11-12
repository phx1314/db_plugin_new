//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.layout.commons;

import com.intellij.openapi.vfs.VirtualFile;
import java.io.OutputStreamWriter;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.ProcessingInstruction;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

public class XMLReader {
    private static final String SCHEMA = "http://schemas.android.com/apk/res/android";
    private static final String NS = "android";
    public static Namespace ns = Namespace.getNamespace("android", "http://schemas.android.com/apk/res/android");
    private Document document;
    private VirtualFile file;

    public XMLReader(VirtualFile xmlFile) throws Exception {
        this.init(xmlFile);
    }

    public void init(VirtualFile xmlFile) throws Exception {
        SAXBuilder sb = new SAXBuilder();
        this.document = sb.build(xmlFile.getInputStream());
    }

    public Object remove(Object obj) {
        if (obj instanceof Attribute) {
            Attribute nod = (Attribute)obj;
            nod.getParent().removeAttribute(nod);
        } else if (obj instanceof Element) {
            Element ele = (Element)obj;
            ele.getParent().removeContent(ele);
        } else {
            if (!(obj instanceof ProcessingInstruction)) {
                return null;
            }

            ProcessingInstruction pi = (ProcessingInstruction)obj;
            pi.getParent().removeContent(pi);
        }

        return obj;
    }

    public List<?> getNode(String express) throws JDOMException {
        List<?> ele = XPath.selectNodes(this.document, express);
        return ele;
    }

    public List<?> getElement(String express) throws JDOMException {
        List<?> ele = XPath.selectNodes(this.document, express);
        return ele;
    }

    public Element getElementOne(String express) throws JDOMException {
        List<?> list = XPath.selectNodes(this.document, express);
        return list.size() > 0 ? (Element)list.get(0) : null;
    }

    public Attribute getNodeOne(String express) throws JDOMException {
        List<?> ele = XPath.selectNodes(this.document, express);
        return ele.size() > 0 ? (Attribute)ele.get(0) : null;
    }

    public void save(VirtualFile file) throws Exception {
        this.export(this.document, file);
    }

    private void export(Document document, VirtualFile file) throws Exception {
        Format format = Format.getCompactFormat();
        format.setIndent("     ");
        format.setEncoding("UTF-8");
        XMLOutputter out = new XMLOutputter(format);
        OutputStreamWriter fw = new OutputStreamWriter(file.getOutputStream((Object)null), "UTF-8");
        out.output(this.document, fw);
        fw.close();
    }

    public Document getDocument() {
        return this.document;
    }
}
