package com.mmall.dao;

import java.util.List;

import com.mmall.pojo.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

	List<Product> selectList();

	List<Product> selectByNameAndProductId(String productName, Integer productId);

	List<Product> selectByNameAndCategoryIds(String string, List<Integer> list);
}