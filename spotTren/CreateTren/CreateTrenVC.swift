//
//  CreateTren.swift
//  spotTren
//
//  Created by ARH on 21/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import UIKit

class CreateTrenVC: UIViewController {
    @IBOutlet weak var trainName: UITextField?
    @IBOutlet weak var treinType: UITextField?
    @IBOutlet weak var trenTime: UITextField?
    @IBOutlet weak var trenDate: UITextField?
 
    @IBOutlet weak var button: UIButton?
    
    /// Отвечает за то, создать новую или отредактировать старую
    var isCreate = true
    
    /// Текущая модель для редактирования
    var currentModel: TrenModel?
    
    var complitionHandler: (() -> ())?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guard let model = self.currentModel else { return }
        self.button?.setTitle("Изменить", for: .normal)
        self.showModel(model: model)
        
    }
    
    func createModel() -> TrenModel? {
        guard let name = trainName?.text,
            let type = treinType?.text,
            let time = Int(trenTime?.text ?? ""),
            let date = trenDate?.text else { return nil }
        let model = TrenModel(name: name, type: type, token: TokenSaver().get(), time: time, date: date)
        return model
    }
    
    func createNew() {
        guard let name = trainName?.text,
            let type = treinType?.text,
            let time = Int(trenTime?.text ?? ""),
            let date = trenDate?.text else { return }
        let model = TrenModel(name: name, type: type, token: TokenSaver().get(), time: time, date: date)
        do {
            let jsonData = try JSONEncoder().encode(model)
            let url = URL(string: "http://localhost:8080/train/set")!
            var request = URLRequest(url: url)
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
            request.httpMethod = "POST"
            request.httpBody = jsonData
            URLSession.shared.dataTask(with: request).resume()
        } catch {
            print(error)
        }
    }
    
    func delete() {
        guard let model = self.currentModel else { return }
        let url = URL(string: "http://localhost:8080/train/delete")!
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        do {
            let json = try JSONEncoder().encode(model)
            request.httpBody = json
            URLSession.shared.dataTask(with: request).resume()
        } catch {
            print(error)
        }
    }
    
    private func showModel(model: TrenModel) {
        self.trainName?.text = model.name
        self.treinType?.text = model.type
        self.trenTime?.text = String(model.time)
        self.trenDate?.text = model.date
    }
    
    @IBAction func createTren(_ sender: Any) {
      
        if (isCreate) {
            createNew()
            self.complitionHandler?()
            self.navigationController?.popViewController(animated: true)
        } else {
            self.delete()
            self.createNew()
            self.navigationController?.popViewController(animated: true)
            complitionHandler?()
        }
        
    }
    
    
}
