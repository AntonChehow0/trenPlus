//
//  TokenSaver.swift
//  spotTren
//
//  Created by ARH on 12/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import Foundation

/// Сохраняет и возвращает токен пользователя
class TokenSaver {
    
    /// Хранилище данных
    private let storage = UserDefaults.standard
    
    /// Сохраняет токен в хранилище
    ///
    /// - Parameter token: токен
    func save(token: String) {
        self.storage.set(token, forKey: "token")
    }
    
    /// Возвращает токен из хранилища
    ///
    /// - Returns: токен или пустая строка в случае, если токен не был найден
    func get() -> String {
        return storage.string(forKey: "token") ?? String()
    }
}
