package com.shinsegae.ssg.utilities;

import com.shinsegae.ssg.apis.vos.user.UserVo;

import javax.servlet.http.HttpSession;

public class Converter {
    private Converter(){}

    public static int toInt(String input, int fallback) {
        try {
            return Integer.parseInt(input);
        } catch (Exception ignored) {
            return fallback;
        }
    }

    public static UserVo getUserVo(HttpSession session) {
        Object userVoObject = session.getAttribute(Constant.Apis.ATTRIBUTE_ENTRY_USER_VO);
        UserVo userVo = null;
        if (userVoObject instanceof UserVo) {
            userVo = (UserVo) userVoObject;
        }
        return userVo;
    }
}