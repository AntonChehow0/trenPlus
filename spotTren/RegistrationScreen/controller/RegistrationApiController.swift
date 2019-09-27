//
//  ApiController.swift
//  spotTren
//
//  Created by ARH on 10/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import Foundation

class RegistrationApiController {
    
    private let serverAddress = "http://localhost:8080/profile"
    
    func reguster(userModel: UserModel, complitionHandler: @escaping (_ model: UserModel) -> ()) {
        guard let url = URL(string: serverAddress) else { return }
        var request = URLRequest(url: url)
        request.addValue(userModel.userLogin, forHTTPHeaderField: "login")
        request.addValue(userModel.userPassword, forHTTPHeaderField: "passwd")
        request.addValue(userModel.userName, forHTTPHeaderField: "name")
        request.addValue(userModel.token, forHTTPHeaderField: "")
        
        URLSession.shared.dataTask(with: request) { (data, _, error) in
            do {
                let model = try JSONDecoder().decode(UserModel.self, from: data ?? Data())
                complitionHandler(model)
            } catch {
                print(error)
            }
        }.resume()
    }
    
}
