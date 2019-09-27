package com.com.sport.spnig.server.userData;

import com.com.sport.dataBase.userDb.UserManagerDb;
import com.com.sport.spnig.server.Mock.AccauntMock;
import com.com.sport.spnig.server.trainAPI.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
public class UserDataController {

    private final AccauntMock accauntMock;

    /**
     * Контроллер базы данных
     */
    private UserManagerDb userDataBaseController = new UserManagerDb();

    @Autowired
    public UserDataController(AccauntMock mock ) {
        this.accauntMock = mock;
    }


    @GetMapping
    public UserModel getAccaunt(@RequestHeader Map<String, String> headers) {
        String userName = headers.get("name");
        String userLogin = headers.get("login");
        String userPassword = headers.get("passwd");
        String token = headers.get("token");

        UserModel model = new UserModel();

        model.setUserName(userName);
        model.setUserLogin(userLogin);
        model.setUserPassword(userPassword);
        model.setToken(token);

        if (token == null) {
            return userDataBaseController.createNewUser(model);
        } else {
            return userDataBaseController.getUser(model);
        }
    }

    @GetMapping(value = "/token")
     public UserModel getAccauntFromToken(@RequestHeader Map<String, String> headers) {
        String token = headers.get("token");
        UserModel model = new UserModel();
        model.setToken(token);
        return userDataBaseController.getUser(model);
    }

    @GetMapping(value = "/auth")
    public UserModel autoryse(@RequestHeader Map<String, String> headers) {
        String login = headers.get("login");
        String passwd = headers.get("password");

        UserModel model = new UserModel();
        model.setUserLogin(login);
        model.setUserPassword(passwd);

        model.getLog();

        return userDataBaseController.autoryse(model);
    }

    @GetMapping(value = "/rm")
    public void removeAllUsers() {
        userDataBaseController.removeAllUser();
    }

}
