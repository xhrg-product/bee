package com.github.xhrg.bee.gateway.load.mapper;

import com.github.xhrg.bee.basic.mapper.ApiMapper;
import com.github.xhrg.bee.basic.mapper.FilterMapper;
import com.github.xhrg.bee.basic.mapper.RouterMapper;
import com.github.xhrg.bee.basic.mapper.mo.ApiMo;
import com.github.xhrg.bee.basic.mapper.mo.FilterMo;
import com.github.xhrg.bee.basic.mapper.mo.RouterMo;
import com.github.xhrg.bee.gateway.load.data.ApiData;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import com.github.xhrg.bee.gateway.load.data.RouterData;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApiMapperExt {

    @Resource
    private ApiMapper apiMapper;

    @Resource
    private FilterMapper filterMapper;

    @Resource
    private RouterMapper routerMapper;

    public List<ApiData> getApis() {
        List<ApiMo> apiMos = apiMapper.getAll();
        List<ApiData> apiDataList = new ArrayList<>();

        for (ApiMo apiMo : apiMos) {
            ApiData apiData = new ApiData();
            BeanUtils.copyProperties(apiMo, apiData);
            apiDataList.add(apiData);
        }

        return apiDataList;
    }


    public Map<Integer, FilterData> getFilterMap() {
        List<FilterMo> filterMoList = filterMapper.getAll();
        Map<Integer, FilterData> map = new HashMap<>();

        for (FilterMo mo : filterMoList) {
            FilterData filterData = new FilterData();
            BeanUtils.copyProperties(mo, filterData);
            map.put(mo.getApiId(), filterData);
        }

        return null;
    }

    public Map<Integer, RouterData> getRouterMap() {
        List<RouterMo> routerMoList = routerMapper.getAll();

        Map<Integer, RouterData> map = new HashMap<>();

        for (RouterMo mo : routerMoList) {
            RouterData routerData = new RouterData();
            BeanUtils.copyProperties(mo, routerData);
            map.put(mo.getApiId(), routerData);
        }
        return map;
    }

}
