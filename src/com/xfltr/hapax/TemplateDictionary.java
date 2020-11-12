//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class TemplateDictionary {
    private static final Logger logger = Logger.getLogger(TemplateDictionary.class.getSimpleName());
    private final Map<String, String> dict = new HashMap();
    private final Map<String, List<TemplateDictionary>> subs = new HashMap();
    private final Set<String> shownSections = new HashSet();
    private final TemplateDictionary parent;

    public static TemplateDictionary create() {
        return new TemplateDictionary((TemplateDictionary)null);
    }

    public void put(String key, String val) {
        if (this.dict.containsKey(key)) {
            logger.warning("put(" + key + ") called, but there is already a value for this key.");
        }

        this.dict.put(key.toUpperCase(), val);
    }

    public void put(String key, int val) {
        this.put(key.toUpperCase(), String.valueOf(val));
    }

    public boolean contains(String key) {
        if (this.dict.containsKey(key.toUpperCase())) {
            return true;
        } else {
            return this.parent != null ? this.parent.contains(key.toUpperCase()) : false;
        }
    }

    public String get(String key) {
        if (this.dict.containsKey(key.toUpperCase())) {
            return (String)this.dict.get(key.toUpperCase());
        } else if (this.parent != null) {
            return this.parent.get(key.toUpperCase());
        } else {
            logger.warning("Unable to find a value for '" + key + "', returning empty string!");
            return "";
        }
    }

    public List<TemplateDictionary> getChildDicts(String key) {
        return this.subs.containsKey(key.toUpperCase()) ? (List)this.subs.get(key.toUpperCase()) : Collections.emptyList();
    }

    public TemplateDictionary addChildDict(String key) {
        TemplateDictionary td = new TemplateDictionary(this);
        if (!this.subs.containsKey(key.toUpperCase())) {
            List<TemplateDictionary> dicts = new LinkedList();
            dicts.add(td);
            this.subs.put(key.toUpperCase(), dicts);
        } else {
            ((List)this.subs.get(key.toUpperCase())).add(td);
        }

        return td;
    }

    public TemplateDictionary addChildDictAndShowSection(String section_name) {
        this.showSection(section_name);
        return this.addChildDict(section_name);
    }

    public void hideSection(String section) {
        this.shownSections.remove(section);
    }

    public void showSection(String section) {
        this.shownSections.add(section);
    }

    boolean isHiddenSection(String sectionName) {
        return !this.shownSections.contains(sectionName);
    }

    private TemplateDictionary(TemplateDictionary parent) {
        this.parent = parent;
    }
}
