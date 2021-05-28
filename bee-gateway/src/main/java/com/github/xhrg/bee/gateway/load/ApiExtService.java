package com.github.xhrg.bee.gateway.load;

import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.filter.FilterHandler;
import com.github.xhrg.bee.gateway.load.data.ApiData;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import com.github.xhrg.bee.gateway.load.data.RouterData;
import com.github.xhrg.bee.gateway.load.mapper.ApiMapperExt;
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
    private FilterHandler filterHandler;

    @Resource
    private RouterHandler routerHandler;

    @Resource
    private ApiMapperExt apiMapperExt;

    private List<ApiRuntimeContext> list;

    public ApiRuntimeContext match(String path) {
        for (ApiRuntimeContext apiRunBo : list) {
            if (Objects.equals(apiRunBo.getApiData().getPath(), path)) {
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
        List<ApiData> apis = apiMapperExt.getApis();

        Map<Integer, List<FilterData>> filterDataListMap = apiMapperExt.getFilterMap();
        Map<Integer, RouterData> routerBoMap = apiMapperExt.getRouterMap();
        List<ApiRuntimeContext> apiRuntimeContextList = new ArrayList<>();

        for (ApiData apiData : apis) {
            try {
                ApiRuntimeContext apiRuntimeContext = new ApiRuntimeContext();
                apiRuntimeContext.setApiData(apiData);
                //设置router
                RouterData routerData = routerBoMap.get(apiData.getId());
                if (routerData == null) {
                    log.error(routerData.getName() + ", not match router");
                    continue;
                }
                routerHandler.findRouter(routerData.getName()).init(routerData);
                apiRuntimeContext.setRouterData(routerData);

                //设置filter
                List<FilterData> filterDataList = filterDataListMap.get(apiData.getId());

                for (FilterData filterData : filterDataList) {
                    Filter filter = filterHandler.findFilter(filterData.getName());
                    filter.init(filterData);
                    if (filter.type() == FilterType.PRE) {
                        apiRuntimeContext.getPreFilter().add(filterData);
                    } else {
                        apiRuntimeContext.getPostFilter().add(filterData);
                    }
                }
                //对过滤器进行排序
                Collections.sort(apiRuntimeContext.getPreFilter(), new FilterComparator());
                Collections.sort(apiRuntimeContext.getPreFilter(), new FilterComparator());
                apiRuntimeContextList.add(apiRuntimeContext);
            } catch (Exception e) {
                log.error("load api error, skip this, api_name is " + apiData.getName(), e);
            }
        }
        return apiRuntimeContextList;
    }

    static class FilterComparator implements Comparator<FilterData> {
        @Override
        public int compare(FilterData o1, FilterData o2) {
            return o1.getFilter().sort() - o2.getFilter().sort();
        }
    }
}
