//
//  GetStatisticRequestModel.swift
//  spotTren
//
//  Created by В М on 25/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import Foundation


/// Модель запроса для получения статистики тренировок
struct GetStatisticRequestModel: Codable {
    
    /// Список дат на которые нужно получить статистику
    let dateList: [String]
    
    /// Токен пользователя
    let token: String
}
