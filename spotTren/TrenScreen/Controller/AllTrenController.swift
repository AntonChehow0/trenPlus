//
//  AllTrenController.swift
//  spotTren
//
//  Created by ARH on 21/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import Foundation

/// Производит загрузку из сети всех тренировок текущего пользователя
class AllTrenController {
    
    private struct JsonForLoadData: Codable {
        let token: String
    }
    
    func load(complition: @escaping (_ models: [TrenModel]) -> ()) {
        let url = URL(string: "http://localhost:8080/train/getTrains")!
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        do {
            let jsonData = try JSONEncoder().encode(JsonForLoadData(token: TokenSaver().get()))
            request.httpBody = jsonData
        } catch {
            print(error)
        }
        
        URLSession.shared.dataTask(with: request) { (data, _, _) in
            do {
                let models = try JSONDecoder().decode([TrenModel].self, from: data ?? Data())
                complition(models)
            } catch {
                print(error)
            }
        }.resume()
        
    }
    
}
