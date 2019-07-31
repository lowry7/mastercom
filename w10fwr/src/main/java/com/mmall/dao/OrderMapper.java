package com.mmall.dao;

import java.util.List;

import com.mmall.pojo.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

	Order selectByUserIdAndOrderNo(Integer userId, Long orderNo);

	List<Order> selectByUserId(Integer userId);

	Order selectByOrderNo(Long orderNo);

	List<Order> selectAllOrder();
}