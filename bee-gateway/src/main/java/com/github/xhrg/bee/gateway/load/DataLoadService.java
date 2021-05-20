package com.github.xhrg.bee.gateway.load;

import com.github.xhrg.bee.basic.bo.ApiRunBo;
import com.github.xhrg.bee.basic.service.ApiService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DataLoadService implements InitializingBean {

    @Autowired
    private ApiService apiService;

    private static List<ApiRunBo> list;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<ApiRunBo> list = apiService.getAll();
        DataLoadService.list = list;
    }

    public ApiRunBo match(String path) {
        for (ApiRunBo apiRunBo : list) {
            if (Objects.equals(apiRunBo.getApiBo().getPath(), path)) {
                return apiRunBo;
            }
        }
        return null;
    }
}
