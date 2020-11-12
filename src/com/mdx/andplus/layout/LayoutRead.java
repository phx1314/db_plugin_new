//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mdx.andplus.layout;

import com.intellij.openapi.vfs.VirtualFile;
import com.mdx.andplus.layout.commons.XMLReader;
import com.mdx.andplus.layout.model.AutoCode;
import com.mdx.andplus.layout.model.AutoView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;

public class LayoutRead {
	public static boolean isDebug = false;
	protected XMLReader xmlReader;
	protected ArrayList<AutoView> autoviews = new ArrayList();
	protected String name;
	protected String classname;
	protected MainFastRead mainFastRead;
	protected AutoCode mAutoCode;
	protected HashMap<String, Boolean> clasMap = new HashMap();

	public LayoutRead(VirtualFile file, MainFastRead mfr) throws Exception {
		this.name = file.getName();
		this.xmlReader = new XMLReader(file);
		this.mainFastRead = mfr;
		Element ele = this.xmlReader.getDocument().getRootElement();
		this.calcEle(ele);
		this.mAutoCode = new AutoCode();
		this.mAutoCode.autoviews = this.autoviews;
		this.mAutoCode.name = this.name;
		this.mAutoCode.mainFastRead = this.mainFastRead;
	}

	public AutoCode getAutoCode() {
		return this.mAutoCode;
	}

	public void calcEle(Element ele) throws JDOMException {
		AutoView autoView = new AutoView();
		boolean isadd = false;
		Attribute ida = ele.getAttribute("id", XMLReader.ns);
		if (ida != null) {
			autoView.set(ida.getValue(), ele.getName());
			isadd = true;
			Attribute clicka = ele.getAttribute("onClick", XMLReader.ns);
			if (clicka != null) {
				autoView.onclick = clicka.getValue();
				isadd = true;
			}

			Attribute txta = ele.getAttribute("text", XMLReader.ns);
			if (txta != null) {
				autoView.text = txta.getValue();
				isadd = true;
			}
		}

		if (isadd) {
			this.autoviews.add(autoView);
		}

		Iterator var7 = ele.getChildren().iterator();

		while(var7.hasNext()) {
			Object el = var7.next();
			this.calcEle((Element)el);
		}

	}

	public void save() throws Exception {
	}

	public String getImport() {
		StringBuffer sb = new StringBuffer();
		Iterator var2 = this.autoviews.iterator();

		while(var2.hasNext()) {
			AutoView av = (AutoView)var2.next();
			if (!this.clasMap.containsKey(av.clas)) {
				this.clasMap.put(av.clas, true);
				if (av.id != null && av.id.length() != 0) {
					sb.append("import " + av.clas + ";\n");
				}
			}
		}

		return sb.toString();
	}

	public String getSet() {
		return "";
	}

	public String getFindViewId() {
		StringBuffer sb = new StringBuffer();
		Iterator var2 = this.autoviews.iterator();

		while(var2.hasNext()) {
			AutoView av = (AutoView)var2.next();
			if (av.id != null && av.id.length() != 0) {
				sb.append("        ");
				sb.append(av.name);
				sb.append("=");
				sb.append("(").append(av.type).append(")").append("findViewById(").append(av.id).append(");\n");
			}
		}

		return sb.toString();
	}

	public String getViewDecaler() {
		StringBuffer sb = new StringBuffer();
		Iterator var2 = this.autoviews.iterator();

		while(var2.hasNext()) {
			AutoView av = (AutoView)var2.next();
			if (av.id != null && av.id.length() != 0) {
				sb.append("    public ");
				sb.append(av.type);
				sb.append(" ");
				sb.append(av.name);
				sb.append(";\r\n");
			}
		}

		return sb.toString();
	}

	public String getOnClick() {
		StringBuffer sb = new StringBuffer();
		Boolean have = false;
		Boolean isWrite = false;
		Iterator var4 = this.autoviews.iterator();

		while(true) {
			AutoView av;
			do {
				do {
					do {
						if (!var4.hasNext()) {
							if (have) {
								sb.append("\n\t}");
							}

							return sb.toString();
						}

						av = (AutoView)var4.next();
					} while(av.id == null);
				} while(av.id.length() == 0);
			} while((av.onclick == null || av.onclick.length() == 0) && !av.name.toUpperCase().startsWith("CLK") && !av.type.toUpperCase().equals("BUTTON"));

			if (!isWrite) {
				have = true;
				sb.append("\t@Override\n\tpublic void onClick(android.view.View v) {\n");
				sb.append("\n        if(" + av.id + "==v.getId()){");
				isWrite = true;
			} else {
				sb.append("else if(" + av.id + "==v.getId()){");
			}

			sb.append("\n\n        }");
		}
	}

	public String getOnClickListener() {
		StringBuffer sb = new StringBuffer();
		Iterator var2 = this.autoviews.iterator();

		while(true) {
			AutoView av;
			do {
				do {
					do {
						if (!var2.hasNext()) {
							return sb.toString();
						}

						av = (AutoView)var2.next();
					} while(av.id == null);
				} while(av.id.length() == 0);
			} while((av.onclick == null || av.onclick.length() == 0) && !av.name.toUpperCase().startsWith("CLK") && !av.type.toUpperCase().equals("BUTTON"));

			sb.append("        " + av.name + ".setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));\n");
		}
	}
}
