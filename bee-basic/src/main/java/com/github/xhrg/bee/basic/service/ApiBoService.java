package com.github.xhrg.bee.basic.service;

import com.github.xhrg.bee.basic.bo.ApiBo;
import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.basic.bo.RouterBo;
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
public class ApiBoService {

    @Resource
    private ApiMapper apiMapper;

    @Resource
    private RouterMapper routerMapper;

    @Resource
    private FilterMapper filterMapper;


    public Map<Integer, FilterBo> getAllFilter() {
        List<FilterPo> filterBos = filterMapper.getAll();

        Map<Integer, FilterBo> map = new HashMap<>();

        for (FilterPo filterPo : filterBos) {
            FilterBo filterBo = new FilterBo();
            BeanUtils.copyProperties(filterPo, filterBo);
            map.put(filterPo.getApiId(), filterBo);
        }

        return map;
    }

    public Map<Integer, RouterBo> getAllRouter() {
        List<RouterPo> routerPos = routerMapper.getAll();
        Map<Integer, RouterBo> map = new HashMap<>();

        for (RouterPo routerpo : routerPos) {
            RouterBo routerBo = new RouterBo();
            BeanUtils.copyProperties(routerpo, routerBo);
            map.put(routerBo.getApiId(), routerBo);
        }
        return map;
    }

    public List<ApiBo> getAllApis() {
        List<ApiPo> apis = apiMapper.getAll();
        List<ApiBo> apiBos = new ArrayList<>();
        for (ApiPo apiPo : apis) {
            ApiBo apiBo = new ApiBo();
            BeanUtils.copyProperties(apiPo, apiBo);
            apiBos.add(apiBo);
        }
        return apiBos;
    }

}
