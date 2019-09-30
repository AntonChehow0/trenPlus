//
//  TrenDeleter.swift
//  spotTren
//
//  Created by В М on 30/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import Foundation

/// Объкт реализующий удаление тренировки с сервера
class TrenDeleter {
    
    /// Удаляет тренировку
    /// - Parameter tren: Тренировка, которую необходимо удалить
    /// - Parameter complitionHandler: после удаления вызывается замыкаение
    func geleteTren(tren: TrenModel, complitionHandler: @escaping () -> ()) {
        guard let url = URL(string: "http://localhost:8080/train/delete") else { return }
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        do {
            let jsonToServer = try JSONEncoder().encode(tren)
            request.httpBody = jsonToServer
            URLSession.shared.dataTask(with: request).resume()
            complitionHandler()
        } catch {
            print(error)
        }
    }
    
}
