//
//  RegistrationModel.swift
//  spotTren
//
//  Created by ARH on 10/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import Foundation

struct UserModel: Codable {
    let userName: String
    let userLogin: String
    let userPassword: String
    let token: String
    
    let error: String?
}
