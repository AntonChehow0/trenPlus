package com.sportTrains.web;


import com.sportTrains.dataBase.BDDriver;
import com.sportTrains.model.ModelForClient;
import com.sportTrains.model.ModelForGetStatus;
import com.sportTrains.model.RemoveModel;
import com.sportTrains.model.TrainModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * WEB контроллер для добавления и удаления тренировок
 * Чтобы изменить тренировку нужно удалить старую и добавить как новую
 * Чтобы удалить тренировку нужно вызвать метод /delete и передать json тренировки для удаления
 * Чтобы добавить новую тренировку нужно вызвать метод /set и передать json с созданой тренировкой
 * Чтобы удалить все тренировки нужно вызвать метод /rm и передать json с паролем
 * Чтобы получить статистику о количестве часов потраченных на тренировки за переуд времни нужно вызвать метод /get. Нужно отправить json c массивом дат на которые нужно получить сумму времени
 * и токен пользователя
 */
@RestController
@RequestMapping(value = "/train", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
public class TrainController {

    /**
     * База данных
     */
    private BDDriver trainBd = new BDDriver();


    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello, train server";
    }


    /**
     * Запрос на удаление всех данных из БД
     * @param removeModel  JSON с паролем на удаление
     */
    @GetMapping(value = "/rm")
    public void removeAll(@RequestBody RemoveModel removeModel) {
        trainBd.connect();
        if (removeModel.password.equals("1234567890")) {
            trainBd.removeAllDataOfTrain();
        } else {
            System.out.println("Введен неверный пароль");
        }
        trainBd.disconnect();
    }

    /**
     * Получает от пользователя новую тренировку
     * @param payload модель с тренировкой
     */
    @GetMapping(value = "/set")
    public void setTrain(@RequestBody TrainModel payload) {
        System.out.println("Метод /set вызван");
        trainBd.connect();
        trainBd.saveTrain(payload);
        payload.print();
        trainBd.disconnect();
    }


    /**
     * Метод возвращает статистику о тренировках проведенных за промежуток времени
     */
    @GetMapping(value = "/getStatistic")
    public ModelForClient getStatus(@RequestBody ModelForGetStatus model) {
        trainBd.connect();
        model.print();
        ModelForClient client = new ModelForClient(trainBd.getTime(model));
        trainBd.disconnect();
        return client;
    }

    /**
     * Извлекает из БД все записи
     */
    @GetMapping(value = "/all")
    public List<TrainModel> getAll() {
        trainBd.connect();
        List<TrainModel> data = trainBd.getAllDataFromDb();
        trainBd.disconnect();
        return data;
    }


    /**
     * Удаляет тренировку
     * @param model тренировка для удаления
     * @return все тренировки
     */
    @GetMapping(value = "/delete")
    public List<TrainModel> delete(@RequestBody TrainModel model) {
        trainBd.connect();
        trainBd.deleteTrain(model);
        List<TrainModel> data = trainBd.getAllDataFromDb();
        trainBd.disconnect();
        return data;
    }

    @GetMapping(value = "/getTrains")
    public List<TrainModel> getForToken(@RequestBody Map<String, String> userToken) {
        trainBd.connect();
        System.out.println(userToken.get("token"));
        List<TrainModel> data = trainBd.getAllTrainsForToken(userToken.get("token"));
        trainBd.disconnect();
        return data;
    }

}
