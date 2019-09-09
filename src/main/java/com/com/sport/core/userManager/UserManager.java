package com.com.sport.core.userManager;


import com.sport.core.models.UserModel;

/**
 * Создает новых пользователей и удаляет старых пользователей
 */
public class UserManager {

    /**
     * Создает нового пользователя в базе данных
     * @param userInfo данные для создаваемого пользователя
     * @return новый пользователь с токеном
     */
    UserModel createNewUser(UserModel userInfo) {
        UserModel model = new UserModel();
        return model;
    }

    void removeUser(UserModel userInfo) {

    }

}
