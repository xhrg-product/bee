package com.github.xhrg.bee.admin.service;

import com.github.xhrg.bee.basic.bo.ApiBo;
import com.github.xhrg.bee.basic.service.ApiBoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApisService {

    @Resource
    private ApiBoService apiBoService;

    public List<ApiBo> query() {
        return apiBoService.getAllApis();
    }
}
