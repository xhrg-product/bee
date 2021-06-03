package com.github.xhrg.bee.admin.controller;

import com.github.xhrg.bee.admin.bo.ApiBo;
import com.github.xhrg.bee.admin.bo.PageData;
import com.github.xhrg.bee.admin.util.ResponseUtils;
import com.github.xhrg.bee.admin.service.ApiService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/apis")
public class ApisController {

    @Resource
    private ApiService ApiService;

    //apis/list?page=1&limit=20
    @RequestMapping("/list")
    public Object list(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("keyword") String keyword) {
        PageData pageReturn = ApiService.getApisPage(page, limit);
        return ResponseUtils.page(pageReturn.getList(), pageReturn.getTotal());
    }

    public Object update(@RequestBody ApiBo apiBo) {
        ApiService.updateById(apiBo);
        return "success";
    }

    public Object delete(@RequestBody Integer id) {
        ApiService.deleteById(id);
        return "success";
    }
}
