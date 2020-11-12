//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xfltr.hapax;

public final class PathUtil {
    private PathUtil() {
    }

    public static boolean isAbsolute(String path) {
        return path.startsWith("/");
    }

    public static String join(String... components) {
        StringBuilder path = new StringBuilder();

        for(int i = 0; i < components.length; ++i) {
            path.append(components[i]);
            if (i < components.length - 1 && components[i + 1].length() > 0 && (i != 0 || components[0].length() != 0)) {
                path.append("/");
            }
        }

        return path.toString().replaceAll("[/]+", "/");
    }

    public static String makeRelative(String dir, String fullPath) {
        dir = removeExtraneousSlashes(dir);
        fullPath = removeExtraneousSlashes(fullPath);
        String relativePath;
        if (fullPath.startsWith(dir)) {
            relativePath = fullPath.substring(dir.length());
        } else {
            relativePath = fullPath;
        }

        String clean = relativePath.replace("/../", "/").replace("/..", "/").replace("../", "/");
        relativePath = join(clean.split("/"));
        relativePath = removeLeadingSlashes(relativePath);
        return relativePath;
    }

    public static String removeLeadingSlashes(String path) {
        return path.replaceFirst("[/]*", "");
    }

    public static String removeExtraneousSlashes(String s) {
        s = s.replaceAll("(.)[/]+$", "$1");
        return s.replaceAll("[/]+", "/");
    }
}
