package com.github.xhrg.bee.admin.controller;

import com.github.xhrg.bee.admin.bo.ApiBo;
import com.github.xhrg.bee.admin.bo.PageData;
import com.github.xhrg.bee.admin.service.ApiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/apis")
public class ApisController {

    @Resource
    private ApiService ApiService;

    @GetMapping("/list")
    public Object list(@RequestParam("page") int page, @RequestParam("limit") int limit,
                       @RequestParam(value = "keyword", required = false) String keyword) {
        PageData pageReturn = ApiService.getApisPage(page, limit);
        return pageReturn;
    }

    @PostMapping("/update")
    public Object update(@RequestBody ApiBo apiBo) {
        ApiService.updateById(apiBo);
        return "success";
    }

    @PostMapping("/insert")
    public Object insert(@RequestBody ApiBo apiBo) {
        ApiService.insert(apiBo);
        return "success";
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody ApiBo apiBo) {
        ApiService.deleteById(apiBo.getId());
        return "success";
    }
}
