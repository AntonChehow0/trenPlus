package com.sportTrains.model;
import com.fasterxml.jackson.annotation.*;

/**
 * Модель для JSON, который отправляет клиент для получения статистики о общем
 * количестве времени затраченного на тренировки за определенный промежуток времени
 */
public class ModelForGetStatus {
    /**
     * Даты по которым нужно делать выборку
     */
    private String[] dateList;
    /**
     * Токен пользователя для доступа к данным
     */
    private String token;

    @JsonProperty("dateList")
    public String[] getDateList() { return dateList; }
    @JsonProperty("dateList")
    public void setDateList(String[] value) { this.dateList = value; }

    @JsonProperty("token")
    public String getToken() { return token; }
    @JsonProperty("token")
    public void setToken(String value) { this.token = value; }

    public void print() {
        String dateString = "";
        for (String s : dateList) {
            dateString += s + ", ";
        }
        String print = String.format("Массив с датами ----> [ %s ]\n" +
                "Токен пользователя ----> %s", dateString, this.token);
        System.out.println(print);
    }

}