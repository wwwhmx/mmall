package com.mmall.conctroller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookiesUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by www on 2019/8/13.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /*
    * 用户登录ro
    * */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            //session.setAttribute(Const.CURRENT_USER, response.getData());  	3E89C7F2E78E6563613615C537DEDDF4
            // 8AC25D241F09D3ADA8CDD1A9A4BCD826
            // 8AC25D241F09D3ADA8CDD1A9A4BCD826
            CookiesUtil.writeLoginToken(httpServletResponse, session.getId());
            //CookiesUtil.readLoginToken(httpServletRequest);
            //CookiesUtil.delLoginToken(httpServletRequest, httpServletResponse);

            RedisPoolUtil.setEx(session.getId(), JsonUtil.objToString(response.getData()), Const.RedisCashExtime.REDIS_SESSION_EXITME);
        }
        return response;
    }

    /*
    * 用户退出登录
    * */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String token = CookiesUtil.readLoginToken(httpServletRequest);
        CookiesUtil.delLoginToken(httpServletRequest, httpServletResponse);
        RedisPoolUtil.del(token);
        return ServerResponse.createBysuccess();
    }

    /*
   * 用户注册
   * */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /*
  * 用户校验
  * */
    @RequestMapping(value = "checkValid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /*
 * 获取用户信息
 * */
    @RequestMapping(value = "getUserInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpServletRequest httpServletRequest) {
        //User user = (User) session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookiesUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息");
        }

        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.stringToObj(userJsonStr, User.class);

        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息");
    }

    /*
 * 忘记密码
 * */
    @RequestMapping(value = "forgetGetQuestion.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    /*
* 核对忘记密码问题答案
* */
    @RequestMapping(value = "forgetCheckAnswer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {

        return iUserService.checkAnswer(username, question, answer);
    }


    /*
   * 忘记密码重置
   * */
    @RequestMapping(value = "forgetResetPassword.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username, String newPassWord, String forgetToken) {
        return iUserService.forgetResetPassword(username, newPassWord, forgetToken);
    }

    /*
  * 用户设置新密码
  * */
    @RequestMapping(value = "resetPassword.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session, String passwordold, String newPassword){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordold, newPassword, user);
    }

    /*
* 更新个人用户信息
* */
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(HttpSession session,User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /*
* 获取个人用户信息
* */
    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录，需要强制登陆Status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }




}
