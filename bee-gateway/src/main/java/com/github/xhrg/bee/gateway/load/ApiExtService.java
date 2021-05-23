package com.github.xhrg.bee.gateway.load;

import com.github.xhrg.bee.basic.bo.ApiBo;
import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.basic.bo.RouterBo;
import com.github.xhrg.bee.basic.service.ApiBoService;
import com.github.xhrg.bee.gateway.filter.FilterService;
import com.github.xhrg.bee.gateway.router.RouterHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class ApiExtService implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private ApiBoService apiBoService;

    @Resource
    private FilterService filterService;

    @Resource
    private RouterHandler routerHandler;

    private List<ApiRuntimeContext> list;

    public ApiRuntimeContext match(String path) {
        for (ApiRuntimeContext apiRunBo : list) {
            if (Objects.equals(apiRunBo.getApiBo().getPath(), path)) {
                return apiRunBo;
            }
        }
        return null;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<ApiRuntimeContext> list = this.getAll();
        this.list = list;
    }

    public List<ApiRuntimeContext> getAll() {
        List<ApiBo> apis = apiBoService.getAllApis();

        Map<Integer, FilterBo> filterBoMap = apiBoService.getAllFilter();
        Map<Integer, RouterBo> routerBoMap = apiBoService.getAllRouter();

        List<ApiRuntimeContext> apiRuntimeContextList = new ArrayList<>();

        for (ApiBo apiBo : apis) {
            try {
                ApiRuntimeContext apiRuntimeContext = new ApiRuntimeContext();
                apiRuntimeContext.setApiBo(apiBo);
                RouterBo routerBo = routerBoMap.get(apiBo.getId());
                if (routerBo == null) {
                    log.error(apiBo.getName() + ", not match router");
                    continue;
                }

                RouterBo routerExtBo = routerHandler.findRouter(routerBo.getName()).init(routerBo);
                apiRuntimeContext.setRouterBo(routerExtBo);

                FilterBo filterBo = filterBoMap.get(apiBo.getId());
                if (filterBo != null) {
                    boolean ok = filterService.isPre(filterBo.getName());
                    FilterBo extFilterBo = filterService.extFilterBo(filterBo);
                    if (ok) {
                        apiRuntimeContext.getPreFilter().add(extFilterBo);
                    } else {
                        apiRuntimeContext.getPostFilter().add(extFilterBo);
                    }
                }
                apiRuntimeContextList.add(apiRuntimeContext);
            } catch (Exception e) {
                log.error("load api error, skip this, api_name is " + apiBo.getName(), e);
            }
        }
        return apiRuntimeContextList;
    }

}
