package com.github.xhrg.bee.admin.util;

import com.github.xhrg.bee.admin.config.Response;
import com.github.xhrg.bee.admin.config.ResponseList;

import java.util.List;

public abstract class ResponseUtils {

    public static Response page(List<?> list, long total) {
        Response response = new Response();
        ResponseList responseList = new ResponseList();
        responseList.setItems(list);
        responseList.setTotal(total);
        response.setData(responseList);
        return response;
    }

    public static Response data(Object data) {
        Response response = new Response();
        response.setData(data);
        return response;
    }
}
