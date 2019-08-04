package com.mmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mmall.pojo.Cart;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

	Cart selectCartByUserIdProductId(@Param(value="userId")Integer userId, @Param(value="productId")Integer productId);

	void deleteByUserIdProductIds(@Param(value="userId")Integer userId, @Param(value="productList")List<String> productList);

	void checkedOrUncheckedProduct(@Param(value="userId")Integer userId, @Param(value="productId")Integer productId,  @Param(value="checked")Integer checked);

	Integer selectCartProductCount(Integer userId);

	List<Cart> selectCartByUserId(Integer userId);

	int selectCartProductCheckedStatusByUserId(Integer userId);

	List<Cart> selectCheckedCartByUserId(Integer userId);

}