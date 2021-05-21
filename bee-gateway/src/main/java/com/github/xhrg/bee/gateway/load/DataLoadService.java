package com.github.xhrg.bee.gateway.load;

import com.github.xhrg.bee.basic.bo.ApiRunBo;
import com.github.xhrg.bee.basic.service.ApiService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class DataLoadService implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private ApiService apiService;

    private static List<ApiRunBo> list;

    public ApiRunBo match(String path) {
        for (ApiRunBo apiRunBo : list) {
            if (Objects.equals(apiRunBo.getApiBo().getPath(), path)) {
                return apiRunBo;
            }
        }
        return null;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<ApiRunBo> list = apiService.getAll();
        DataLoadService.list = list;
    }
}
