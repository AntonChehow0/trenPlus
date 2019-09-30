package com.sportTrains.dataBase;

import com.sportTrains.model.ModelForGetStatus;
import com.sportTrains.model.TrainModel;

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
        ModelForGetStatus modelForGetStatus = new ModelForGetStatus();
        modelForGetStatus.setToken("token1");

        List<String> list = new ArrayList();
        list.add("10.10.10");
        list.add("11.10.10");
        list.add("12.10.10");

        for (TrainModel model : testDriver.getAllDataFromDb()) {
            System.out.println(model.time);
            System.out.println(model.name);
            System.out.println(model.token);
            System.out.println(model.date);
        }

    }


    /**
     * Подключиться к БД
     */
    private void connect() {
        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATA_BASE_NAME);
            System.out.println("База подключена");
            statment = connection.createStatement();
            statment.execute("CREATE TABLE IF NOT EXISTS " +
                    DATA_BASE_TABLE_NAME +
                    " ( \"name\"\tTEXT,\t\"type\"\tTEXT,\t\"allTime\"\tTEXT,\t\"token\"\tTEXT,\t\"dateTren\"\tTEXT);");
        } catch (Exception e) {
            System.out.println("Попытка подключени завершена с ошибкой");
            e.printStackTrace();
        }
    }

    /**
     * Отключится от БД
     */
    private void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statment != null) {
                statment.close();
            }
            if (resultSet != null) {
                resultSet.close();
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
        connect();
        try {
            String sql = "DELETE FROM " + DATA_BASE_TABLE_NAME;
            statment.execute(sql);
            System.out.println("Данные удалены");
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
    }


    /**
     * Сохраняет пользовательскую тренировку
     * Сохранение происходит по токену и по токену будет происходить восстановление
     * @param modelForSave
     */
    public void saveTrain(TrainModel modelForSave) {
        connect();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public List<TrainModel> getAllDataFromDb() {
        connect();
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
            disconnect();
            return modelList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return new ArrayList();
    }

    /**
     * Удаляет одну конкретную тренировку
     * @param model тренировка на удаление
     */
    public void deleteTrain(TrainModel model) {
        connect();
        try {
            String sql = String.format("DELETE FROM " +
                    DATA_BASE_TABLE_NAME +
                    " WHERE token = \"%s\" AND name = \"%s\"AND allTime = \"%s\";\n)", model.token, model.name, model.time);
            statment.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
    }

    /**
     * Получает из БД выборку по токену пользователя и массиву из дат
     * @param model
     * @return
     */
    public Integer getTime(ModelForGetStatus model) {
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

            connect();
            String sql = String.format("SELECT sum (allTime) as sum FROM " +
                    DATA_BASE_TABLE_NAME +
                    " WHERE (token = '%s' AND %s) GROUP BY token", model.getToken(), date);
            System.out.println(sql);
            resultSet = statment.executeQuery(sql);
            if (resultSet.next()) {
                int result = resultSet.getInt("sum");
                disconnect();
                System.out.println("Статистика ---> " + result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return 0;
    }

    public List<TrainModel> getAllTrainsForToken(String token) {
        connect();

        try {
            String sql = String.format("SELECT * FROM %s WHERE (token = '%s')", DATA_BASE_TABLE_NAME, token);
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

                modelList.add(model);
            }
            disconnect();

            return modelList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

}
