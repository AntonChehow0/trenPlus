package com.sportTrains.dataBase;

import com.sportTrains.model.ModelForGetStatus;
import com.sportTrains.model.TrainModel;
import org.apache.juli.logging.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Драйвер для БД в которой сохраняются пользовательские тренировки
 */
public class BDDriver {

    /**
     * Имя БД
     */
    private static final String DATA_BASE_NAME = "trainDB.sql";
    /**
     * Имя таблицы для БД
     */
    private static final String DATA_BASE_TABLE_NAME = "userTrain";


    /**
     * Подключение к БД
     */
    private static Connection connection;
    /**
     * Выполнение запросов к БД
     */
    private static Statement statment;
    /**
     * Множество данных из БД
     */
    private static ResultSet resultSet;

    /**
     * Метод для тестирование контроллера БД
     */
    public static void main(String arg[]) {
        BDDriver testDriver = new BDDriver();
        testDriver.connect();

        ModelForGetStatus modelForGetStatus = new ModelForGetStatus();
        modelForGetStatus.setToken("token1");

//        List<String> list = new ArrayList();
//        list.add("10.10.10");
//        list.add("11.10.10");
//        list.add("12.10.10");
//
//
//        for (TrainModel model : testDriver.getAllDataFromDb()) {
//            System.out.println(model.time);
//            System.out.println(model.name);
//            System.out.println(model.token);
//            System.out.println(model.date);
//        }

        TrainModel model = new TrainModel();
        model.setDate("afgsd");
        model.setName("asdf");
        model.setTime(5645);
        model.setToken("ksadnkhsafd");
        model.setType("andskjabnds");

//        testDriver.saveTrain(model);
        List<TrainModel> models = testDriver.getAllTrainsForToken("cwlzk3l2p67aeq5mhbmo1n8t1l6o3tgyzry6p0qmwr9eszns0y8kupy0gah47lbsfgewvzbcccvjaceh05zl8c6esro53w70cabv");
        for (TrainModel emodel : models) {
            emodel.print();
        }

        testDriver.disconnect();

    }


    /**
     * Подключиться к БД
     */
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATA_BASE_NAME);
            System.out.println("База подключена");
            statment = connection.createStatement();
//            statment.execute("CREATE TABLE IF NOT EXISTS " +
//                    DATA_BASE_TABLE_NAME +
//                    " ( \"name\"\tTEXT,\t\"type\"\tTEXT,\t\"allTime\"\tTEXT,\t\"token\"\tTEXT,\t\"dateTren\"\tTEXT);");
        } catch (Exception e) {
            System.out.println("Попытка подключени завершена с ошибкой");
            e.printStackTrace();
        }
    }

    /**
     * Отключится от БД
     */
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("connection.close");
            }
            if (statment != null) {
                statment.close();
                System.out.println("connection.close");
            }
            if (resultSet != null) {
                resultSet.close();
                System.out.println("resultSet.close");
            }
            System.out.println("Соединение с БД закрыто");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляет всех пользователей из базы данный
     */
    public void removeAllDataOfTrain() {
        System.out.println("Удаление пользователей в процессе");
        try {
            String sql = "DELETE FROM " + DATA_BASE_TABLE_NAME;
            statment.execute(sql);
            System.out.println("Данные удалены");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Сохраняет пользовательскую тренировку
     * Сохранение происходит по токену и по токену будет происходить восстановление
     * @param modelForSave
     */
    public void saveTrain(TrainModel modelForSave) {
        System.out.println("Сохранение тренировки в процессе...");
        try {
            String sql = String.format("INSERT INTO " +
                            DATA_BASE_TABLE_NAME +
                            " ('name', 'type', 'allTime', 'token', dateTren) VALUES ('%s', '%s', '%s', '%s', '%s');", modelForSave.name,
                    modelForSave.type,
                    ""+modelForSave.time,
                    modelForSave.token,
                    modelForSave.date);
            System.out.println(sql);
            statment.execute(sql);
            System.out.println("Сохраниене тренировки завершено");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Сохраниене тренировки завершено неудачей");
            System.out.println("Повторная попытка");
            connect();
            saveTrain(modelForSave);
            disconnect();
        }
    }

    /**
     * Все данные из БД (метод для теста)
     */
    public List<TrainModel> getAllDataFromDb() {
        try {
            List<TrainModel> modelList = new ArrayList();
            resultSet = statment.executeQuery("SELECT * FROM " + DATA_BASE_TABLE_NAME);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String token = resultSet.getString("token");
                long time = resultSet.getLong("allTime");
                String date = resultSet.getString("dateTren");

                TrainModel model = new TrainModel();
                model.name = name;
                model.type = type;
                model.token = token;
                model.time = time;
                model.date = date;

                model.print();

                modelList.add(model);
            }
            return modelList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    /**
     * Удаляет одну конкретную тренировку
     * @param model тренировка на удаление
     */
    public void deleteTrain(TrainModel model) {
        System.out.println("Удаление тренировки в процессе...");
        try {
            String sql = String.format("DELETE FROM " +
                    DATA_BASE_TABLE_NAME +
                    " WHERE token = \"%s\" AND name = \"%s\"AND allTime = \"%s\";\n)", model.token, model.name, model.time);
            statment.execute(sql);
            System.out.println("Удаление завершено");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Удаление завершено неудачей");
            System.out.println("Повторная попытка");
            connect();
            deleteTrain(model);
            disconnect();
        }
    }

    /**
     * Получает из БД выборку по токену пользователя и массиву из дат
     * @param model
     * @return
     */
    public Integer getTime(ModelForGetStatus model) {
        System.out.println("Получение статистики в процессе...");
        try {

            String date = "(dateTren = ";
            for (int i = 0; i < model.getDateList().length; i++) {
                date += "'"+model.getDateList()[i]+"'";
                if (i != model.getDateList().length - 1) {
                    date += " OR dateTren = ";
                } else {
                    date += ")";
                }
            }

            String sql = String.format("SELECT sum (allTime) as sum FROM " +
                    DATA_BASE_TABLE_NAME +
                    " WHERE (token = '%s' AND %s) GROUP BY token", model.getToken(), date);
            System.out.println(sql);
            resultSet = statment.executeQuery(sql);
            String log = String.format("Количество данных в ответе на запрос ----> %s", resultSet.getFetchSize());
            System.out.println(log);
            if (resultSet.next()) {
                int result = resultSet.getInt("sum");
                System.out.println("Статистика ---> " + result);
                return result;
            } else {
                System.out.println("Получние статистики завершено\nСтатистика отсутствует");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Получние статистики завершено неудачно");
            System.out.println("Повторная попытка");
            connect();
            Integer time = getTime(model);
            disconnect();
            return time;
        }
    }

    /**
     * Получить все тренировки пользователя
     * @param token идентификатор пользователя
     * @return тренировки пользователя
     */
    public List<TrainModel> getAllTrainsForToken(String token) {
        System.out.println("Получение пользователем всех тренировок по своему токену");
        try {
            String sql = String.format("SELECT * FROM %s WHERE (token = \"%s\")", DATA_BASE_TABLE_NAME, token);
            System.out.println(sql);


            resultSet = statment.executeQuery(sql);

            List<TrainModel> modelList = new ArrayList();

            while (resultSet.next()) {
                TrainModel model = new TrainModel();

                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String date = resultSet.getString("dateTren");

                long time = resultSet.getLong("allTime");

                model.setName(name);
                model.setDate(date);
                model.setTime(time);
                model.setToken(token);
                model.setType(type);

                model.print();

                modelList.add(model);
            }
            System.out.println("Получние тренировок завершено");
            return modelList;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Получние тренировок завершено неудачно");
            System.out.println("Повторная попытка");
            connect();
            List<TrainModel> models = getAllTrainsForToken(token);
            disconnect();
            return models;
        }
    }

}
