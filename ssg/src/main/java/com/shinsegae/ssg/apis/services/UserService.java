package com.shinsegae.ssg.apis.services;

import com.shinsegae.ssg.apis.containers.LoginResultContainer;
import com.shinsegae.ssg.apis.daos.UserDao;
import com.shinsegae.ssg.apis.enums.user.LoginResult;
import com.shinsegae.ssg.apis.vos.user.LoginVo;
import com.shinsegae.ssg.apis.vos.user.UserVo;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class UserService {
    private final DataSource dataSource;
    private final UserDao userDao;

    private static final String EMAIL_REGEX = "^(?=.{4,100}.$)([0-9a-zA-Z][0-9a-zA-Z_]*[0-9a-zA-Z])@([0-9a-z][0-9a-z\\-]*[0-9a-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$";
    private static final String PASSWORD_REGEX = "^([0-9a-f]{128})$";

    public UserService(DataSource dataSource, UserDao userDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
    }

    public LoginResultContainer login(LoginVo loginVo) throws
            SQLException {
        if (!loginVo.getEmail().matches(EMAIL_REGEX) ||
                !loginVo.getHashedPassword().matches(PASSWORD_REGEX)) {
            return new LoginResultContainer(LoginResult.INVALID_INPUT);
        }
        try (Connection connection = this.dataSource.getConnection()) {
            UserVo userVo = this.userDao.selectUser(connection, loginVo);
            if (userVo == null) {
                return new LoginResultContainer(LoginResult.FAILURE);
            } else {
                return new LoginResultContainer(LoginResult.SUCCESS, userVo);
            }
        }
    }
}