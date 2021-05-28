package com.github.xhrg.bee.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xhrg.bee.basic.mapper.mo.ApiMo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ApiMapper extends BaseMapper<ApiMo> {

    @Select("select * from bee_api where status = 1 ")
    List<ApiMo> getAll();
}
