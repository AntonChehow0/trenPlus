//
//  LoginApiController.swift
//  spotTren
//
//  Created by ARH on 12/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import Foundation

/// Отвечает за авторизацию пользователя
class LoginApiController {
    
    private let serverAddress = "http://localhost:8080/profile/auth"
    
    func auth(model: UserModel, complition: @escaping (_ modelFromServcer: UserModel) -> ()) {
        guard let url = URL(string: self.serverAddress) else { return }
        var request = URLRequest(url: url)
        request.addValue(model.userLogin, forHTTPHeaderField: "login")
        request.addValue(model.userPassword, forHTTPHeaderField: "password")
        
        URLSession.shared.dataTask(with: request) { (data, _, error) in
            do {
                let m = try JSONDecoder().decode(UserModel.self, from: data ?? Data())
                complition(m)
            } catch {
                print(error)
            }
        }.resume()
        
    }
    
}
