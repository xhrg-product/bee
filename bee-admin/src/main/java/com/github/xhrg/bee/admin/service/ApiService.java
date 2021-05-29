package com.github.xhrg.bee.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xhrg.bee.admin.bo.ApiBo;
import com.github.xhrg.bee.admin.bo.FilterBo;
import com.github.xhrg.bee.admin.bo.PageData;
import com.github.xhrg.bee.admin.bo.RouterBo;
import com.github.xhrg.bee.basic.mapper.ApiMapper;
import com.github.xhrg.bee.basic.mapper.FilterMapper;
import com.github.xhrg.bee.basic.mapper.RouterMapper;
import com.github.xhrg.bee.basic.mapper.mo.ApiMo;
import com.github.xhrg.bee.basic.mapper.mo.FilterMo;
import com.github.xhrg.bee.basic.mapper.mo.RouterMo;
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
    private RouterMapper routerMapper;

    @Resource
    private FilterMapper filterMapper;


    public Map<Integer, FilterBo> getAllFilter() {
        List<FilterMo> filterBos = filterMapper.getAll();
        Map<Integer, FilterBo> map = new HashMap<>();

        for (FilterMo filterPo : filterBos) {
            FilterBo filterBo = new FilterBo();
            BeanUtils.copyProperties(filterPo, filterBo);
            map.put(filterPo.getApiId(), filterBo);
        }
        return map;
    }

    public Map<Integer, RouterBo> getAllRouter() {
        List<RouterMo> routerPos = routerMapper.getAll();
        Map<Integer, RouterBo> map = new HashMap<>();

        for (RouterMo routerpo : routerPos) {
            RouterBo routerBo = new RouterBo();
            BeanUtils.copyProperties(routerpo, routerBo);
            map.put(routerBo.getApiId(), routerBo);
        }
        return map;
    }

    public List<ApiBo> getAllApis() {
        List<ApiMo> apis = apiMapper.getAll();
        List<ApiBo> apiBos = new ArrayList<>();
        for (ApiMo apiPo : apis) {
            ApiBo apiBo = new ApiBo();
            BeanUtils.copyProperties(apiPo, apiBo);
            apiBos.add(apiBo);
        }
        return apiBos;
    }

    public PageData getApisPage(int pageSize, int limitSize) {

        QueryWrapper<ApiMo> wrapper = new QueryWrapper();
        Page<ApiMo> page = new Page<>(pageSize, limitSize);
        page = apiMapper.selectPage(page, wrapper);
        List<ApiBo> listBo = new ArrayList<>();
        for (ApiMo mo : page.getRecords()) {
            ApiBo apiBo = new ApiBo();
            BeanUtils.copyProperties(mo, apiBo);
            listBo.add(apiBo);
        }

        PageData pageData = new PageData();
        pageData.setList(listBo);
        pageData.setTotal((int) page.getTotal());

        return pageData;
    }

}
