package com.com.sport.dataBase.userDb;


import com.com.sport.spnig.server.trainAPI.models.UserModel;

import java.sql.*;
import java.util.Random;

/**
 * Добавляет и возвращает пользователя из базу данных
 */
public class UserManagerDb {

    private static Connection connection;
    private static Statement statment;
    private static ResultSet resultSet;


    public static void main(String[] argv) {
        UserManagerDb managerDb = new UserManagerDb();
//        managerDb.createNewUser(new UserModel());
        managerDb.readBd();
        managerDb.closeConnection();
    }


    /**
     * Создает и добавляет нового пользователя в базу данных
     * @param model модель пользователя, которая будет добавленна в базу данных
     */
    public UserModel createNewUser(UserModel model) {
        connect();
        UserModel userModel = writeUserToBd(model);
        closeConnection();
        return userModel;
    }


    /**
     * Возвращает информацию о пользователе по токену этого пользователя
     * @param model из модели пользовательских данных берется токен
     * @return заполненная модель с пользовательскими данными
     */
    public UserModel getUser(UserModel model) {
        connect();
        UserModel model1 = getUserByToken(model.getToken());
        closeConnection();
        return model1;
    }

    /**
     * Производит авторизацию пользователя по логину и паролю
     * @param model берёт из пользовательских данных логин и пароль пользователя
     * @return модель пользовательских данных из Базы Данных
     */
    public UserModel autoryse(UserModel model) {
        connect();
        UserModel model1 = getWithLoginAndPass(model);
        closeConnection();
        return  model1;
    }


    /**
     * Извлекает данные из БД по логину и паролю
     * @param model модель с логином и паролем
     * @return данные найденные по логину и паролю
     */
    private UserModel getWithLoginAndPass(UserModel model) {
        connect();
        try {
            resultSet = statment.executeQuery("SELECT * FROM userDataBase");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String passwd = resultSet.getString("password");
                String tokenFromBd = resultSet.getString("token");

                if (model.getUserLogin().equals(login) && model.getUserPassword().equals(passwd)) {
                    UserModel modelFromBd = new UserModel();
                    modelFromBd.setUserName(name);
                    modelFromBd.setUserLogin(login);
                    modelFromBd.setUserPassword(passwd);
                    modelFromBd.setToken(tokenFromBd);

                    modelFromBd.setError("");

                    modelFromBd.getLog();

                    return modelFromBd;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            UserModel errorModel = new UserModel();
            errorModel.setUserName("");
            errorModel.setUserLogin("");
            errorModel.setUserPassword("");
            errorModel.setToken("");
            errorModel.setError("User not found");

            errorModel.getLog();

            return errorModel;
        }
        UserModel nullModel = new UserModel();

        nullModel.setError("");
        nullModel.setToken("");
        nullModel.setUserLogin("");
        nullModel.setUserPassword("");
        nullModel.setUserName("");

        nullModel.getLog();

        return nullModel;
    }


    private void connect() {
        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:userDataBase.db");
            System.out.println("База подключена");
            createNewTable();
        } catch (Exception e) {
            System.out.println("Попытка подключени завершена с ошибкой");
            e.printStackTrace();
        }
    }

    private void createNewTable() {
        try {
            statment = connection.createStatement();
            statment.execute("CREATE TABLE if not exists 'userDataBase' (\n" +
                    "\t\"name\"\tTEXT,\n" +
                    "\t\"login\"\tTEXT,\n" +
                    "\t\"password\"\tTEXT,\n" +
                    "\t\"token\"\tTEXT\n" +
                    ");");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы для БД");
            e.printStackTrace();
        }
    }

    /**
     * Возвращает данные из БД по токену пользователя
     * @param token токен конкретного пользователя
     */
    private UserModel getUserByToken(String token) {
        try {
            connect();
            resultSet = statment.executeQuery("SELECT * FROM userDataBase");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String passwd = resultSet.getString("password");
                String tokenFromBd = resultSet.getString("token");

                System.out.println("Имя - " + name);
                System.out.println("Логин - " + login);
                System.out.println("Пароль - " + passwd);
                System.out.println("Токен - " + token);

                if (tokenFromBd.equals(token)) {
                    UserModel model = new UserModel();
                    model.setUserName(name);
                    model.setUserLogin(login);
                    model.setUserPassword(passwd);
                    model.setToken(tokenFromBd);
                    return model;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Производит чтение всей базы данных
     */
    private void readBd() {
        try {
            resultSet = statment.executeQuery("SELECT * FROM userDataBase");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String passwd = resultSet.getString("password");
                String token = resultSet.getString("token");

                System.out.println("Имя - " + name);
                System.out.println("Логин - " + login);
                System.out.println("Пароль - " + passwd);
                System.out.println("Токен - " + token);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Закрывает соединение и зачищает остаточные данные
     */
    private void closeConnection() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Записывает тестовый объект в базу данных
     */
    private void writeMockToBD() {
        String name = "Test";
        String login = "TestL";
        String password = "JHVHV";
        String token = "ajsdfgajdskgndfsjolk;jbhsadgiyafdhbjhkjhvkfsadhuvjk";

        try {
            String sql = String.format("INSERT INTO 'userDataBase' ('name', 'login', 'password', 'token') VALUES ('%s', '%s', '%s', '%s'); ", name, login, password, token);
            statment.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Записывает данные пользователя в базу данных
     * @param model данные о пользователе
     */
    private UserModel writeUserToBd(UserModel model) {
        UserModel newModel = model;
        newModel.setToken(this.createToken());
        String name = newModel.getUserName();
        String login = newModel.getUserLogin();
        String password = newModel.getUserPassword();
        String token = newModel.getToken();
        try {
            String sql = String.format("INSERT INTO 'userDataBase' ('name', 'login', 'password', 'token') VALUES ('%s', '%s', '%s', '%s'); ", name, login, password, token);
            statment.execute(sql);
            return getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Удаляет всех пользователей из базы данный
     */
    public void removeAllUser() {
        try {
            connect();
            String sql = "DELETE FROM 'userDataBase'";
            statment.execute(sql);
            System.out.println("Данные удалены");
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Генерирует токен для пользователя
     * @return сгенерированный токен
     */
    private String createToken() {
        String characters = "qwertyuiopasdfghjklzxcvbnm1234567890";
        Random rng = new Random();
        int length = 100;
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

}
