package com.shinsegae.ssg.apis.controllers;

import com.shinsegae.ssg.apis.containers.LoginResultContainer;
import com.shinsegae.ssg.apis.services.UserService;
import com.shinsegae.ssg.apis.vos.user.LoginVo;
import com.shinsegae.ssg.controllers.StandardRestController;
import com.shinsegae.ssg.utilities.Constant;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping(
        value = "/apis/user/",
        method = {RequestMethod.GET, RequestMethod.POST},
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends StandardRestController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "login")
    public String login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(name = "email", defaultValue = "") String email,
                        @RequestParam(name = "password", defaultValue = "") String password) throws
            SQLException {
        LoginVo loginVo = new LoginVo(email, password);
        LoginResultContainer loginResultContainer = this.userService.login(loginVo);
        request.getSession().setAttribute(
                Constant.Apis.ATTRIBUTE_ENTRY_USER_VO,
                loginResultContainer.getUserVo()
        );
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put(
                Constant.Common.JSON_ENTRY_RESULT,
                loginResultContainer.getLoginResult().name().toLowerCase()
        );
        response.setContentType("application/json");
        return jsonResponse.toString(4);
    }
}