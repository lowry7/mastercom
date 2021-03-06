package com.mmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mmall.pojo.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

	List<Product> selectList();

	List<Product> selectByNameAndProductId(@Param(value="productName")String productName, @Param(value="productId")Integer productId);

	List<Product> selectByNameAndCategoryIds(@Param(value="productName")String productName, @Param(value="categoryIdList")List<Integer> categoryIdList);
}