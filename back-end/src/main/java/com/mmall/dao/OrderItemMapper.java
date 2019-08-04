package com.mmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mmall.pojo.OrderItem;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

	List<OrderItem> getByOrderNoUserId(@Param(value="orderNo")Long orderNo, @Param(value="userId")Integer userId);

	List<OrderItem> getByOrderNo(Long orderNo);

	void batchInsert(@Param(value="orderItemList")List<OrderItem> orderItemList);

}