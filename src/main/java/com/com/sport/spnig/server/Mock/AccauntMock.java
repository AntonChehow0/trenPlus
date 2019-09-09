package com.com.sport.spnig.server.Mock;


import com.sport.core.models.UserModel;
import org.springframework.stereotype.Service;

/**
 * Имитирует работу с базой данных
 */
@Service
public class AccauntMock {

    /**
     * Метод создающий тестовый аккаунт
     * @return тестовый аккаунт
     */
    public UserModel getUserModel() {
        UserModel model = new UserModel();
        model.setUserName("Иван");
        model.setUserLogin("L");
        model.setUserPassword("P");
        model.setToken("askjhfbajsbkdgasljnkhj");
        return model;
    }

    public UserModel getUserModel(String name, String login, String passwod, String token) {
        UserModel model = new UserModel();
        model.setUserName(name);
        model.setUserLogin(login);
        model.setUserPassword(passwod);
        model.setToken(token);
        return model;
    }

}
