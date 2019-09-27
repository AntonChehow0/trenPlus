package com.com.sport.spnig.server.trainAPI.models;

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

    private String error;

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

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void getLog() {
        String log = String.format("ЛОГ МОДЕЛИ \n" +
                "Имя пользователя --> %s\n" +
                "Пароль пользователя --> %s\n" +
                "Токен пользователя --> %s\n" +
                "Ошибка ---> %s\n" +
                "КОНЕЦ ЛОГА", userLogin, userPassword, token, error);

        System.out.println(log);
    }
}
