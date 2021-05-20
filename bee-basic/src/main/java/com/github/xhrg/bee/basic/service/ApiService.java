package com.github.xhrg.bee.basic.service;

import com.github.xhrg.bee.basic.bo.ApiBo;
import com.github.xhrg.bee.basic.bo.ApiRunBo;
import com.github.xhrg.bee.basic.bo.RouterBo;
import com.github.xhrg.bee.basic.mapper.ApiMapper;
import com.github.xhrg.bee.basic.mapper.RouterMapper;
import com.github.xhrg.bee.basic.po.ApiPo;
import com.github.xhrg.bee.basic.po.RouterPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ApiService {

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private RouterMapper routerMockMapper;

    public List<ApiRunBo> getAll() {
        List<ApiPo> apis = apiMapper.getAll();
        List<RouterPo> routers = routerMockMapper.getAll();

        Map<Integer, RouterPo> map = new HashMap<>();
        for (RouterPo po : routers) {
            map.put(po.getApiId(), po);
        }

        List<ApiRunBo> list = new ArrayList<>();
        for (ApiPo a : apis) {
            ApiRunBo bo = new ApiRunBo();

            ApiBo b = new ApiBo();
            BeanUtils.copyProperties(a, b);
            bo.setApiBo(b);
            RouterBo routerBo = new RouterBo();
            RouterPo po = map.get(a.getId());
            if (po == null) {
                log.error(a.getName() + ", not match router");
                continue;
            }
            BeanUtils.copyProperties(po, routerBo);
            bo.setRouterBo(routerBo);
            list.add(bo);
        }

        return list;
    }
}
