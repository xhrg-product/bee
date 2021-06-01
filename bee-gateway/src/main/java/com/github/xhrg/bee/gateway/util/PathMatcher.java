package com.github.xhrg.bee.gateway.util;

import org.springframework.util.AntPathMatcher;

public abstract class PathMatcher {

    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    public static boolean match(String pattern, String path) {
        boolean ok = antPathMatcher.match(pattern, path);
        return ok;
    }

    public static String toNewPath(String pattern, String path, String target) {
        // 1个星号的必须放在2个星下面，因为2个星号包含了1个星号
        pattern = pattern.replace("/**", "");
        pattern = pattern.replace("/*", "");
        String result = target + path.replace(pattern, "");
        return result;
    }

}
