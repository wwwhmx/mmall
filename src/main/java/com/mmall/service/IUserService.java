package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by www on 2019/8/13.
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);

    public ServerResponse<String> register(User user);

    public ServerResponse<String> checkValid(String str, String type);

    public ServerResponse<String> selectQuestion(String username);

    public ServerResponse<String> checkAnswer(String username, String question, String answer);

    public ServerResponse<String> forgetResetPassword(String username, String newPassWord, String forgetToken);

    public ServerResponse<String> resetPassword(String passwordold, String newPassword, User user);

    public ServerResponse<User> updateInformation(User user);

    public ServerResponse<User> getInformation(Integer userId);

    //backend
    public ServerResponse checkAdminRole(User user);
}

