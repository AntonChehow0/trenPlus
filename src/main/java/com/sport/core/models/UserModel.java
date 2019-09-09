package com.sport.core.models;

/**
 * Модель описывающая данные пользователя
 */
public class UserModel {
    /**
     * Имя пользователя
     */
    private String userName;
    /**
     * Логин пользователя для входа
     */
    private String userLogin;
    /**
     * Пароль пользователя
     */
    private String userPassword;
    /**
     * Токен герерируемый сервером
     * При регистрации пользователя он приходит пустым
     * В других случаях пустым быть не может
     */
    private String token;


    public String getToken() {
        return token;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
