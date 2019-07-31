package com.mmall.dao;

import java.util.List;

import com.mmall.pojo.OrderItem;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

	List<OrderItem> getByOrderNoUserId(Long orderNo, Integer userId);

	List<OrderItem> getByOrderNo(Long orderNo);

	void batchInsert(List<OrderItem> orderItemList);

}