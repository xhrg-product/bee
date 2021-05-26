package com.github.xhrg.bee.gateway.load;

import com.github.xhrg.bee.basic.bo.ApiBo;
import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.basic.bo.RouterBo;
import com.github.xhrg.bee.basic.service.ApiBoService;
import com.github.xhrg.bee.gateway.filter.FilterHandler;
import com.github.xhrg.bee.gateway.router.RouterHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
@Slf4j
public class ApiExtService implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private ApiBoService apiBoService;

    @Resource
    private FilterHandler filterHandler;

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
        try {
            List<ApiRuntimeContext> list = this.getAll();
            this.list = list;
        } catch (Exception e) {
            this.list = new ArrayList<>();
            e.printStackTrace();
            log.error("load api error, please check mysql connection");
        }
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
                    boolean ok = filterHandler.isPre(filterBo.getName());
                    filterHandler.initFilterBo(filterBo);
                    if (ok) {
                        apiRuntimeContext.getPreFilter().add(filterBo);
                    } else {
                        apiRuntimeContext.getPostFilter().add(filterBo);
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
