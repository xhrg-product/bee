package com.github.xhrg.bee.admin.util;

import com.github.xhrg.bee.admin.bo.ResponseBo;
import com.github.xhrg.bee.admin.bo.ResponseListBo;

import java.util.List;

public abstract class ResponseUtils {

    public static ResponseBo page(List<?> list, long total) {
        ResponseBo response = new ResponseBo();
        ResponseListBo responseList = new ResponseListBo();
        responseList.setItems(list);
        responseList.setTotal(total);
        response.setData(responseList);
        return response;
    }

    public static ResponseBo data(Object data) {
        ResponseBo response = new ResponseBo();
        response.setData(data);
        return response;
    }
}
