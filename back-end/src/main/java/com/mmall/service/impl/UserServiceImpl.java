package com.mmall.service.impl;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.controller.exception.GlobalException;
import com.mmall.pojo.User;
import com.mmall.redis.RedisService;
import com.mmall.rediskey.UserKey;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import com.mmall.util.UUIDUtil;

/**
 * Created by LHT
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;
    
	public static final String COOKI_NAME_TOKEN = "token";

    @Override
    public User login(String username, String password,HttpServletResponse response) {
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user=userMapper.selectLogin(username,md5Password);
        if(user==null){
        	throw new GlobalException(ResponseCode.LOGIN_USERNAMEORPASSWORD_ERROR);
        }
        //生成cookie
        String token= UUIDUtil.uuid();
        addCookie(response, token, user);
        return user;
    }
    


    public boolean register(User user){
    	
        checkValid(user.getUsername(),Const.USERNAME);
        
        checkValid(user.getEmail(),Const.EMAIL);
       
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        return resultCount != 0;
    }

    public boolean checkValid(String str,String type){
        //开始校验
        if(Const.USERNAME.equals(type)){
            int resultCount = userMapper.checkUsername(str);
            if(resultCount > 0 ){
            	throw new GlobalException(ResponseCode.USER_NAME_ALREADY_EXISTS);
            }
        }else if(Const.EMAIL.equals(type)){
            int resultCount = userMapper.checkEmail(str);
            if(resultCount > 0 ){
            	throw new GlobalException(ResponseCode.USER_EMAIL_EXISTS);
            }
        }
        return true;
    }

    public String selectQuestion(String username){

        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0 ){
            throw new GlobalException(ResponseCode.USER_NAME_ALREADY_NOTEXISTS);
        }
        String questoion=userMapper.selectQuestionByUsername(username);
        if(StringUtils.isBlank(questoion)){
        	throw new GlobalException(ResponseCode.USER_QUESTION_NOTEXISTS);
        }
        return questoion;
    }

    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount>0){
            //说明问题及问题答案是这个用户的,并且是正确的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }



    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        if(org.apache.commons.lang3.StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误,token需要传递");
        }
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0 ){
            throw new GlobalException(ResponseCode.USER_NAME_ALREADY_NOTEXISTS);
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if(org.apache.commons.lang3.StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }

        if(org.apache.commons.lang3.StringUtils.equals(forgetToken,token)){
            String md5Password  = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username,md5Password);

            if(rowCount > 0){
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        }else{
            return ServerResponse.createByErrorMessage("token错误,请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }


    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user){
        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0){
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }


    public ServerResponse<User> updateInformation(User user){
        //username是不能被更新的
        //email也要进行一个校验,校验新的email是不是已经存在,并且存在的email如果相同的话,不能是我们当前的这个用户的.
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("email已存在,请更换email再尝试更新");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }



    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);

    }


    //backend

    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    public ServerResponse checkAdminRole(User user){
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    
    private void addCookie(HttpServletResponse response, String token,User user) {
		redisService.set(UserKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(UserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}
    
    @Override
    public User getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		String paramToken = request.getParameter(COOKI_NAME_TOKEN);
		String cookieToken =null;
		if(StringUtils.isEmpty(paramToken)) {
			cookieToken = getCookieValue(request, COOKI_NAME_TOKEN);
			if(StringUtils.isEmpty(cookieToken)){
				return null;
			}
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		return getByToken(response, token);
	}
	
	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[]  cookies = request.getCookies();
		if(cookies == null || cookies.length <= 0){
			return null;
		}
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(cookiName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	

	public User getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		User user = redisService.get(UserKey.token, token,User.class);
		//延长有效期
		if(user != null) {
			addCookie(response, token, user);
		}
		return user;
	}

	
}
