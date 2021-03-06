package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	int checkUsername(String username);

	User selectLogin(@Param(value="username") String username,@Param(value="password") String password);

	int checkEmail(String str);

	String selectQuestionByUsername(String username);

	int checkAnswer(@Param(value="username")String username, @Param(value="question")String question, @Param(value="answer")String answer);

	int updatePasswordByUsername(@Param(value="username")String username, @Param(value="password")String password);

	int checkPassword(@Param(value="password")String password, @Param(value="id")Integer id);

	int checkEmailByUserId(@Param(value="password")String email, @Param(value="id")Integer id);
}