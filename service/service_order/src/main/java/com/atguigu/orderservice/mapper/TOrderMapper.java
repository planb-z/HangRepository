package com.atguigu.orderservice.mapper;

import com.atguigu.orderservice.entity.TOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-12-19
 */
@Mapper
public interface TOrderMapper extends BaseMapper<TOrder> {

}
