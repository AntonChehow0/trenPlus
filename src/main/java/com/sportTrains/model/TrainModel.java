package com.sportTrains.model;

import com.fasterxml.jackson.annotation.*;

/**
 * Модель JSON которая приходит с клиента и сохраняется в БД
 */
public class TrainModel {

    /**
     * Пользовательское название тренировки
     */
    public String name;
    /**
     * Тип тренировки
     */
    public String type;
    /**
     * Токен пользователя
     */
    public String token;
    /**
     * Время ушедшее на тренировку
     */
    public long time;
    /**
     * Дата тренировки
     */
    public String date;

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String name) { this.name = name; }

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("token")
    public String getToken() { return token; }
    @JsonProperty("token")
    public void setToken(String value) { this.token = value; }

    @JsonProperty("time")
    public long getTime() { return time; }
    @JsonProperty("time")
    public void setTime(long value) { this.time = value; }

    @JsonProperty("date")
    public String getDate() { return date; }
    @JsonProperty("date")
    public void setDate(String value) { this.date = value; }


    public void print() {
        String print = String.format("Имя тренировки ---> %s\n" +
                "Тип тренировки ---> %s\n" +
                "Токен пользователя ---> %s\n" +
                "Время ушедшее на тренировку ---> %d\n" +
                "Дата тренировки ---> %s", name, type, token, time, date);
        System.out.println(print);
    }

}