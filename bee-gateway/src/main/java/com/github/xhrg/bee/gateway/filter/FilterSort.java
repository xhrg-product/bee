package com.github.xhrg.bee.gateway.filter;


public abstract class FilterSort {

    public static final int MockFilter = getSort();

    public static final int CorsFilter = getSort();

    public static final int BodyChangeFilter = getSort();

    private static int curSort = 0;

    private static int getSort() {
        return curSort++;
    }

    public static void main(String[] args) {
        System.out.println(MockFilter);
        System.out.println(CorsFilter);
    }
}
