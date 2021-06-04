package com.github.xhrg.bee.admin.service;

import com.github.xhrg.bee.admin.bo.FilterBo;
import com.github.xhrg.bee.admin.bo.RouterBo;
import com.github.xhrg.bee.basic.mapper.FilterMapper;
import com.github.xhrg.bee.basic.mapper.RouterMapper;
import com.github.xhrg.bee.basic.mapper.mo.FilterMo;
import com.github.xhrg.bee.basic.mapper.mo.RouterMo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RouterService {

    @Resource
    private RouterMapper routerMapper;

    public void insert(RouterBo routerBo) {
        RouterMo routerMo = new RouterMo();
        BeanUtils.copyProperties(routerBo, routerMo);
        routerMapper.insert(routerMo);
    }

    public void deleteById(int id) {
        routerMapper.deleteById(id);
    }

    public void updateById(RouterBo routerBo) {
        RouterMo routerMo = new RouterMo();
        BeanUtils.copyProperties(routerBo, routerMo);
        routerMapper.updateById(routerMo);
    }

    public List<RouterBo> queryByApiId(int apiId) {
        List<RouterMo> listMo = routerMapper.getByApiId(apiId);
        List<RouterBo> listBo = new ArrayList<>();
        for (RouterMo mo : listMo) {
            RouterBo bo = new RouterBo();
            BeanUtils.copyProperties(mo, bo);
            listBo.add(bo);
        }
        return listBo;
    }
}
