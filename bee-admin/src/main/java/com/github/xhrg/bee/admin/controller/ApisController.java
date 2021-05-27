package com.github.xhrg.bee.admin.controller;

import com.github.xhrg.bee.admin.util.ResponseUtils;
import com.github.xhrg.bee.basic.bo.PageData;
import com.github.xhrg.bee.basic.service.ApiBoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/apis")
public class ApisController {

    @Resource
    private ApiBoService apiBoService;

    //apis/list?page=1&limit=20
    @RequestMapping("/list")
    public Object list(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("keyword") String keyword) {
        PageData pageReturn = apiBoService.getApisPage(page, limit);
        return ResponseUtils.page(pageReturn.getList(), pageReturn.getTotal());
    }
}
