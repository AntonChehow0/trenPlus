//
//  ViewController.swift
//  spotTren
//
//  Created by ARH on 10/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import UIKit
import SideMenu

class ViewController: UIViewController {

    /// Поле для ввода логина
    @IBOutlet weak var loginTextField: UITextField?
    
    /// Поле для ввода пароля
    @IBOutlet weak var passwordTextField: UITextField?
    
    /// Кнопка логина
    @IBOutlet weak var loginButton: UIButton?
    
    /// Кнопка регистрации
    @IBOutlet weak var registryButton: UIButton?
    
    let loginController = LoginApiController()
    
    /// Нажатие для входа в аккаунт
    @IBAction func loginTap(_ sender: UIButton) {
        let usModel = UserModel(userName: "",
                                userLogin: loginTextField?.text ?? "",
                                userPassword: passwordTextField?.text ?? "",
                                token: "",
                                error: nil)
        self.loginController.auth(model: usModel) { (modelFromServer) in
            if (!modelFromServer.token.isEmpty) {
                let qeue = DispatchQueue.global(qos: .utility)
                qeue.async {
                    let tokenStorage = TokenSaver()
                    tokenStorage.save(token: modelFromServer.token)
                }
                DispatchQueue.main.async {
                    guard let trenScreen = UIStoryboard(name: "TrenVC", bundle: nil).instantiateInitialViewController() as? TrenVC else { return }
                    self.navigationController?.pushViewController(trenScreen, animated: true)
                }
            }
        }
    }
    
    /// Нажатие для перехода на экран регистрации
    @IBAction func registerTap(_ sender: UIButton) {
        guard let regVC: RegistrationVC = UIStoryboard(name: "RegistrationVC", bundle: nil).instantiateInitialViewController() as? RegistrationVC else { return }
        self.navigationController?.pushViewController(regVC, animated: true)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.setupButtons(buttons: [loginButton, registryButton])
        if !TokenSaver().get().isEmpty {
            guard let trenScreen = UIStoryboard(name: "TrenVC", bundle: nil).instantiateInitialViewController() as? TrenVC else { return }
            self.navigationController?.pushViewController(trenScreen, animated: true)
        }
        self.title = "Вход в аккаунт"
    }

    

}

extension UIViewController {
    func setupButtons(buttons: [UIButton?]) {
        buttons.forEach { (button) in
            button?.layer.cornerRadius = 10
        }
    }
}

