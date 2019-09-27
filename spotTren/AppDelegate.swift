//
//  AppDelegate.swift
//  spotTren
//
//  Created by ARH on 10/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import UIKit
import SideMenu

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        guard let rootController = UIStoryboard(name: "Main", bundle: nil).instantiateInitialViewController() as? ViewController else { return true }
        let nav = UINavigationController(rootViewController: rootController)
        self.window = UIWindow(frame: UIScreen.main.bounds)
        self.window?.rootViewController = nav
        self.window?.makeKeyAndVisible()
        
        return true
    }

}

