package com.github.xhrg.bee.gateway.load.data;

import lombok.Data;

//FilterData存放的是Filter的元数据，这些元数据的载体，比如map是所有的请求共享的，
//所以不能用这个类让业务放传递会话数据，会话数据应该使用request相关对象
@Data
public class FilterDataReader {

    private FilterData filterData;

    @SuppressWarnings("unchecked")
    public <E> E getDynaObject() {
        return (E) filterData.getDynaObject();
    }

    @SuppressWarnings("unchecked")
    public <E> E getMapExtValue(String key) {
        return filterData.getMapExtValue(key);
    }

    public String getData() {
        return filterData.getData();
    }
}
