package com.github.xhrg.bee.gateway.util;

import org.springframework.util.AntPathMatcher;

public abstract class PathMatcher {

    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    public static boolean match(String pattern, String uri) {
        boolean ok = antPathMatcher.match(pattern, uri);
        return ok;
    }

    public static String toNewPath(String pattern, String sourceUri, String targetUri) {
        // 1个星号的必须放在2个星下面，因为2个星号包含了1个星号
        pattern = pattern.replace("/**", "");
        pattern = pattern.replace("/*", "");
        String result = targetUri + sourceUri.replace(pattern, "");
        return result;
    }

    public static void main(String[] args) {
        System.out.println(toNewPath("/ping/**", "/ping/aab/aac?aa=bb", "/ping2"));
    }
}
