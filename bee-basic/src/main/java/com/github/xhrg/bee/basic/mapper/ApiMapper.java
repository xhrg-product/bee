package com.github.xhrg.bee.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xhrg.bee.basic.po.ApiPo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

public interface ApiMapper extends BaseMapper<ApiPo> {

    @Select("select * from bee_api")
    List<ApiPo> getAll();
}
