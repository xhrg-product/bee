package com.github.xhrg.bee.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xhrg.bee.basic.po.FilterPo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FilterMapper extends BaseMapper<FilterPo> {
    @Select("select * from bee_filter")
    List<FilterPo> getAll();
}
