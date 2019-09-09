package com.com.sport.spnig.server.userData;

import com.com.sport.spnig.server.Mock.AccauntMock;
import com.sport.core.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
public class UserDataController {

    private final AccauntMock accauntMock;

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
        return accauntMock.getUserModel(userName, userLogin, userPassword, token);
    }

}
