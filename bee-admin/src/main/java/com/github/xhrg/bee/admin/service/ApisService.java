package com.github.xhrg.bee.admin.service;

import com.github.xhrg.bee.basic.bo.ApiBo;
import com.github.xhrg.bee.basic.mapper.ApiMapper;
import com.github.xhrg.bee.basic.po.ApiPo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ApisService {

    @Resource
    private ApiMapper apiMapper;

    public List<ApiBo> query() {

        List<ApiPo> list = apiMapper.getAll();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<ApiBo> listBo = new ArrayList<>();

        for (ApiPo po : list) {
            ApiBo bo = new ApiBo();
            BeanUtils.copyProperties(po, bo);
            listBo.add(bo);
        }
        return listBo;
    }
}
