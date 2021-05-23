package com.github.xhrg.bee.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xhrg.bee.basic.mapper.mo.RouterMo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RouterMapper extends BaseMapper<RouterMo> {

    @Select("select * from bee_router")
    List<RouterMo> getAll();
}
