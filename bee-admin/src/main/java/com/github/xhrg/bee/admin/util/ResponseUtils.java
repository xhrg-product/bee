package com.github.xhrg.bee.admin.util;

import com.github.xhrg.bee.admin.dto.Response;
import com.github.xhrg.bee.admin.dto.ResponseList;

import java.util.List;

public abstract class ResponseUtils {

    public static Response list(List<?> list) {
        Response response = new Response();
        ResponseList responseList = new ResponseList();
        responseList.setItems(list);
        responseList.setTotal(list.size());
        response.setData(responseList);
        return response;
    }

    public static Response data(Object data) {
        Response response = new Response();
        response.setData(data);
        return response;
    }
}
