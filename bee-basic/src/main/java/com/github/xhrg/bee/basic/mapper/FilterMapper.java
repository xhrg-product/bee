package com.github.xhrg.bee.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xhrg.bee.basic.mapper.mo.FilterMo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FilterMapper extends BaseMapper<FilterMo> {
    @Select("select * from bee_filter")
    List<FilterMo> getAll();
}
