package com.sportTrains.model;


/**
 * Ответ сервера на запрос о тренировках
 */
public class ModelForClient {

    /**
     * Все время потраченное на тренировки за периуд времени
     */
    public Integer allTime;




    public ModelForClient(Integer allTime) {
        this.allTime = allTime;
    }

}
