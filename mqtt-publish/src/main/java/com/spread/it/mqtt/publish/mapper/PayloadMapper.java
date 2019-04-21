package com.spread.it.mqtt.publish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spread.it.mqtt.publish.entity.Payload;

import java.util.List;

/**
 * 用於存payload 持久層
 * @author lsy
 * @version 1.0.0
 */

public interface PayloadMapper extends BaseMapper<Payload> {
    /**
     * 查询所有
     * @return
     */
    List<Payload> findAll();
}
