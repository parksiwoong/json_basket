package com.shinsegae.ssg.apis.vos.user;

public class UserVo {
    private final int index;
    private final String email;
    private final String password;
    private final String nickname;
    private final boolean isAdmin;

    public UserVo(int index, String email, String password, String nickname, boolean isAdmin) {
        this.index = index;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isAdmin = isAdmin;
    }

    public int getIndex() {
        return index;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}