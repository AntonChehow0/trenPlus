//
//  ApiController.swift
//  spotTren
//
//  Created by В М on 24/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import Foundation



/// Получает с сервера данные о статистики тренировок
class StatisticApiController {
    
    /// Метод получающий статистику с сервера
    /// - Parameter dateList: список с датами на которые нужно получить статистику
    /// - Parameter statisticHandler: замыкание в котором будет возвращаться ответ от сервера
    /// - Parameter statistic: Поле со статистикой
    func getStatistic(dateList: [String], statisticHandler: @escaping (_ statistic: StatisticModel) -> ()) {
        let requestModel = GetStatisticRequestModel(dateList: dateList, token: TokenSaver().get())
        guard let url = URL(string: "http://localhost:8080/train/getStatistic") else { return }
        var request = URLRequest(url: url)
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpMethod = "POST"
        do {
            let jsonData = try JSONEncoder().encode(requestModel)
            request.httpBody = jsonData
            URLSession.shared.dataTask(with: request) { (data, _, _) in
                do {
                    let statistic = try JSONDecoder().decode(StatisticModel.self, from: data ?? Data())
                    statisticHandler(statistic)
                } catch {
                    print(error)
                }
            }.resume()
        } catch {
            print(error)
        }
    }
    
}
