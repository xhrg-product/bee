package com.github.xhrg.bee.gateway.load;

import com.github.xhrg.bee.basic.bo.ApiBo;
import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.basic.bo.RouterBo;
import com.github.xhrg.bee.basic.service.ApiBoService;
import com.github.xhrg.bee.gateway.extbo.ApiRuntimeContext;
import com.github.xhrg.bee.gateway.extbo.HttpRouterBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ApiExtService {

    @Resource
    private ApiBoService apiBoService;

    public List<ApiRuntimeContext> getAll() {
        List<ApiBo> apis = apiBoService.getAllApis();

        Map<Integer, FilterBo> filterBoMap = apiBoService.getAllFilter();
        Map<Integer, RouterBo> routerBoMap = apiBoService.getAllRouter();

        List<ApiRuntimeContext> apiRuntimeContextList = new ArrayList<>();

        for (ApiBo apiBo : apis) {
            ApiRuntimeContext apiRuntimeContext = new ApiRuntimeContext();
            apiRuntimeContext.setApiBo(apiBo);
            RouterBo routerBo = routerBoMap.get(apiBo.getId());
            if (routerBo == null) {
                log.error(apiBo.getName() + ", not match router");
                continue;
            }
            if ("http".equals(routerBo.getName())) {
                apiRuntimeContext.setRouterBo(HttpRouterBo.toMe(routerBo));
            }
            FilterBo filterBo = filterBoMap.get(apiBo.getId());
            if (filterBo != null) {
                apiRuntimeContext.setFilterBo(filterBo);
            }
            apiRuntimeContextList.add(apiRuntimeContext);
        }
        return apiRuntimeContextList;
    }
}
