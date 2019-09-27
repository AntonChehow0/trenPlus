//
//  TrenModel.swift
//  spotTren
//
//  Created by ARH on 21/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import Foundation

struct TrenModel: Codable {
    let name, type, token: String
    let time: Int
    let date: String
}
