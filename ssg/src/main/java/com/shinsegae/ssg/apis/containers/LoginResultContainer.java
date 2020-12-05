package com.shinsegae.ssg.apis.containers;

import com.shinsegae.ssg.apis.enums.user.LoginResult;
import com.shinsegae.ssg.apis.vos.user.UserVo;

public class LoginResultContainer {
    private final LoginResult loginResult;
    private final UserVo userVo;

    public LoginResultContainer(LoginResult loginResult) {
        this.loginResult = loginResult;
        this.userVo = null;
    }

    public LoginResultContainer(LoginResult loginResult, UserVo userVo) {
        this.loginResult = loginResult;
        this.userVo = userVo;
    }

    public LoginResult getLoginResult() {
        return loginResult;
    }

    public UserVo getUserVo() { return userVo; }
}