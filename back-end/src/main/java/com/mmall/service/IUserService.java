package com.mmall.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by geely
 */
public interface IUserService {

    User login(String username, String password, HttpServletResponse response);

    boolean register(User user);

    boolean checkValid(String str,String type);

    String selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String question,String answer);

    ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);

	User getUserInfo(HttpServletRequest request, HttpServletResponse response);
}
