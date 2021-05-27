package com.github.xhrg.bee.admin.controller;

import com.github.xhrg.bee.admin.util.ResponseUtils;
import com.github.xhrg.bee.basic.bo.ApiBo;
import com.github.xhrg.bee.basic.service.ApiBoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/apis")
public class ApisController {

    @Resource
    private ApiBoService apiBoService;

    @RequestMapping("/query")
    public Object query() {
        List<ApiBo> list = apiBoService.getAllApis();
        return ResponseUtils.list(list);
    }
}
