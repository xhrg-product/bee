package com.github.xhrg.bee.admin.controller;

import com.github.xhrg.bee.admin.bo.RouterBo;
import com.github.xhrg.bee.admin.service.RouterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/apis")
public class RouterController {

    @Resource
    private RouterService routerService;

    @PostMapping("/update")
    public Object queryByApiId(@RequestBody RouterBo routerBo) {
        return routerService.queryByApiId(routerBo.getApiId());
    }

    @PostMapping("/update")
    public Object update(@RequestBody RouterBo routerBo) {
        routerService.updateById(routerBo);
        return "success";
    }

    @PostMapping("/insert")
    public Object insert(@RequestBody RouterBo routerBo) {
        routerService.insert(routerBo);
        return "success";
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody RouterBo routerBo) {
        routerService.deleteById(routerBo.getId());
        return "success";
    }
}
