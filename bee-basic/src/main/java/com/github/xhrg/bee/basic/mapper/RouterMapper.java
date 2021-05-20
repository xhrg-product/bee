package com.github.xhrg.bee.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xhrg.bee.basic.po.RouterPo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RouterMapper extends BaseMapper<RouterPo> {

    @Select("select * from bee_router")
    List<RouterPo> getAll();
}
