package com.shinsegae.ssg.apis.vos.user;

import com.shinsegae.ssg.utilities.cryptography.Sha512;

public class LoginVo {
    private final String email;
    private final String password;
    private final String hashedPassword;

    public LoginVo(String email, String password) {
        this.email = email;
        this.password = password;
        this.hashedPassword = Sha512.hash(password, "");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
}