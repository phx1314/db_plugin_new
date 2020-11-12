//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax.parser;

public class Range {
    private final int start;
    private final int stop;
    private final int skipTo;

    public Range(int start, int stop, int skipTo) {
        this.start = start;
        this.stop = stop;
        this.skipTo = skipTo;
    }

    public int getStart() {
        return this.start;
    }

    public int getStop() {
        return this.stop;
    }

    public int getSkipTo() {
        return this.skipTo;
    }
}
