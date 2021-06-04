package com.github.xhrg.bee.admin.controller;

import com.github.xhrg.bee.admin.bo.ApiBo;
import com.github.xhrg.bee.admin.bo.FilterBo;
import com.github.xhrg.bee.admin.service.FilterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/filter")
public class FilterController {

    @Resource
    private FilterService filterService;

    @PostMapping("/queryByApiId")
    public Object queryByApiId(@RequestBody FilterBo filterBo) {
        return filterService.queryByApiId(filterBo.getApiId());
    }

    @PostMapping("/update")
    public Object update(@RequestBody FilterBo filterBo) {
        filterService.updateById(filterBo);
        return "success";
    }

    @PostMapping("/insert")
    public Object insert(@RequestBody FilterBo filterBo) {
        filterService.insert(filterBo);
        return "success";
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody ApiBo apiBo) {
        filterService.deleteById(apiBo.getId());
        return "success";
    }
}
