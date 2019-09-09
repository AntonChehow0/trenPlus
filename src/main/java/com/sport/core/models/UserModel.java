package com.sport.core.models;

/**
 * Модель описывающая данные пользователя
 */
public class UserModel {
    /**
     * Имя пользователя
     */
    String userName;
    /**
     * Логин пользователя для входа
     */
    String userLogin;
    /**
     * Пароль пользователя
     */
    String userPassword;
    /**
     * Токен герерируемый сервером
     * При регистрации пользователя он приходит пустым
     * В других случаях пустым быть не может
     */
    String token;

    public UserModel(String userName, String userLogin, String userPassword, String token) {
        this.userName = userName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.token = token;
    }

}
