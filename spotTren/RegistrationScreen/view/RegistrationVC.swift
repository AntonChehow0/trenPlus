//
//  RegistrationVC.swift
//  spotTren
//
//  Created by ARH on 10/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import UIKit

/// Экран регистрации
class RegistrationVC: UIViewController {
    
    /// Поле для ввода имени
    @IBOutlet weak var nameTextField: UITextField?
    
    /// Поле для ввода логина
    @IBOutlet weak var loginTextField: UITextField?
    
    /// Поле для ввода пароля
    @IBOutlet weak var passwordTextField: UITextField?
    
    /// Кнопка регистрации
    @IBOutlet weak var registrationButton: UIButton?
    
    private let regApi = RegistrationApiController()
    
    @IBAction func registrationClick(_ sender: UIButton) {
        guard let name = nameTextField?.text,
            let login = loginTextField?.text,
            let passwd = passwordTextField?.text else { return }
        let uModel = UserModel(userName: name, userLogin: login, userPassword: passwd, token: "", error: nil)
        regApi.reguster(userModel: uModel) { (model) in
            print("Модель с сервера ---> \(model)")
            if (!model.token.isEmpty) {
                let tokenStorage = TokenSaver()
                let qeue = DispatchQueue.global(qos: .utility)
                qeue.async {
                    tokenStorage.save(token: model.token)
                }
                DispatchQueue.main.async {
                    self.navigationController?.popViewController(animated: true)
                }
            }
        }
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.setupButtons(buttons: [registrationButton])
    }
    
}

