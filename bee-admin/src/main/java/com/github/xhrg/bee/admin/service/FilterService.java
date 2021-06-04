package com.github.xhrg.bee.admin.service;

import com.github.xhrg.bee.admin.bo.FilterBo;
import com.github.xhrg.bee.basic.mapper.FilterMapper;
import com.github.xhrg.bee.basic.mapper.mo.FilterMo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FilterService {

    @Resource
    private FilterMapper filterMapper;

    public void insert(FilterBo filterBo) {
        FilterMo filterMo = new FilterMo();
        BeanUtils.copyProperties(filterBo, filterMo);
        filterMapper.insert(filterMo);
    }

    public void deleteById(int id) {
        filterMapper.deleteById(id);
    }

    public void updateById(FilterBo filterBo) {
        FilterMo filterMo = new FilterMo();
        BeanUtils.copyProperties(filterBo, filterMo);
        filterMapper.updateById(filterMo);
    }

    public List<FilterBo> queryByApiId(int apiId) {
        List<FilterMo> listMo = filterMapper.getByApiId(apiId);
        List<FilterBo> listBo = new ArrayList<>();
        for (FilterMo mo : listMo) {
            FilterBo bo = new FilterBo();
            BeanUtils.copyProperties(mo, bo);
            listBo.add(bo);
        }
        return listBo;
    }
}
