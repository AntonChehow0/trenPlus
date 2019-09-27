package com.com.sport.spnig.server.trainAPI.models;

/**
 * Модель данных, которая будет отправлена на сторону клиента
 * Содержит иформацию о тренеровке
 */
public class JsonModel {
    /**
     * Название тренировки
     */
    String trainName;
    /**
     * Тип тренировки
     */
    String trainType;
    /**
     * Дата тренировки
     */
    String trainDate;
    /**
     * Время, которое было затрачено на на тренировку
     */
    float timeOfTrain;

    public JsonModel(String trainName, String trainType, String trainDate, float timeOfTrain) {
        this.trainName = trainName;
        this.trainType = trainType;
        this.trainDate = trainDate;
        this.timeOfTrain = timeOfTrain;
    }
}
