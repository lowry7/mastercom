package com.mmall.controller.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.controller.config.UserVo;
import com.mmall.controller.config.Token;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;

/**
 * Created by geely
 */
@Controller
@RequestMapping("/user/")
public class UserController {


    @Autowired
    private IUserService iUserService;


    /**
             * 用户登录
     * @param username
     * @param password
     * @param response
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(@NotNull @RequestParam String username, @NotNull @RequestParam String password, HttpServletResponse response){
    	iUserService.login(username,password,response);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(User user,@Token String token){
    	if(user!=null){
    		iUserService.logout(token);
    	}
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(@UserVo User user){
    	if(iUserService.register(user)){
    		return ServerResponse.createBySuccess();
    	}
        return ServerResponse.createByErrorMessage("注册失败");
    }


    @RequestMapping(value = "check_valid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(@NotNull String str,@NotNull String type){
    	if(iUserService.checkValid(str,type)){
    		return ServerResponse.createBySuccess();
    	}
        return ServerResponse.createByErrorMessage("校验失败");
    }


    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(User user,HttpServletRequest request,HttpServletResponse response){
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
    }


    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return ServerResponse.createBySuccess(iUserService.selectQuestion(username));
    }


    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        if(iUserService.checkAnswer(username,question,answer)){
        	 return ServerResponse.createBySuccess();
        }
		return ServerResponse.createByError(ResponseCode.USER_QUESTION_ERROR);
    }


    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }



    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(User user,String passwordOld,String passwordNew){
        if(user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }


    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(User user,@UserVo User userVo,@Token String token){
        if(user == null){
        	return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }
        //username是不能被更新的
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        if(iUserService.updateInformation(userVo,token)){
            return ServerResponse.createBySuccess("更新个人信息成功",user);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @RequestMapping(value = "get_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(User user){
    	 if(user != null){
             return ServerResponse.createBySuccess(user);
         }
         return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
    }






























}
