package com.github.xhrg.bee.basic.service;

import com.github.xhrg.bee.basic.bo.*;
import com.github.xhrg.bee.basic.mapper.ApiMapper;
import com.github.xhrg.bee.basic.mapper.FilterMapper;
import com.github.xhrg.bee.basic.mapper.RouterMapper;
import com.github.xhrg.bee.basic.po.ApiPo;
import com.github.xhrg.bee.basic.po.FilterPo;
import com.github.xhrg.bee.basic.po.RouterPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ApiService {

    @Resource
    private ApiMapper apiMapper;

    @Resource
    private RouterMapper routerMockMapper;

    @Resource
    private FilterMapper filterMapper;

    public List<ApiRuntimeContext> getAll() {
        List<ApiPo> apis = apiMapper.getAll();
        List<RouterPo> routers = routerMockMapper.getAll();
        List<FilterPo> filters = filterMapper.getAll();

        Map<Integer, RouterPo> map = new HashMap<>();
        for (RouterPo po : routers) {
            map.put(po.getApiId(), po);
        }

        Map<Integer, FilterPo> mapFilter = new HashMap<>();
        for (FilterPo po : filters) {
            mapFilter.put(po.getApiId(), po);
        }

        List<ApiRuntimeContext> list = new ArrayList<>();
        for (ApiPo a : apis) {
            ApiRuntimeContext bo = new ApiRuntimeContext();

            ApiBo b = new ApiBo();
            BeanUtils.copyProperties(a, b);
            bo.setApiBo(b);

            RouterPo po = map.get(a.getId());
            if (po == null) {
                log.error(a.getName() + ", not match router");
                continue;
            }
            if ("http".equals(po.getType())) {
                RouterBo routerBo = new HttpRouterBo();
                BeanUtils.copyProperties(po, routerBo);
                bo.setRouterBo(routerBo);
            }
            FilterBo filterBo = new FilterBo();
            FilterPo fib = mapFilter.get(a.getId());
            if (fib != null) {
                BeanUtils.copyProperties(fib, filterBo);
                bo.setFilterBo(filterBo);
            }
            list.add(bo);
        }
        return list;
    }
}
