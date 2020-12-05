package com.shinsegae.ssg.apis.daos;

import com.shinsegae.ssg.apis.vos.user.LoginVo;
import com.shinsegae.ssg.apis.vos.user.UserVo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {
    public UserVo selectUser(Connection connection, LoginVo loginVo) throws
            SQLException {
        UserVo userVo = null;
        String query = "" +
                "SELECT `user_index`    AS `userIndex`, " +
                "       `user_email`    AS `userEmail`, " +
                "       `user_password` AS `userPassword`, " +
                "       `user_nickname` AS `userNickname`, " +
                "       `admin_flag`    AS `isAdmin`" +
                "FROM `ssg`.`users` " +
                "WHERE `user_email` = ? " +
                "  AND `user_password` = ? " +
                "LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, loginVo.getEmail());
            preparedStatement.setString(2, loginVo.getHashedPassword());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userVo = new UserVo(
                            resultSet.getInt("userIndex"),
                            resultSet.getString("userEmail"),
                            resultSet.getString("userPassword"),
                            resultSet.getString("userNickname"),
                            resultSet.getBoolean("isAdmin"));
                }
            }
        }
        return userVo;
    }
}