//
//  TrenWatchVC.swift
//  spotTren
//
//  Created by ARH on 21/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import UIKit

class TrenWatchVC: UIViewController {
    @IBOutlet weak var nameTren: UILabel?
    @IBOutlet weak var trenType: UILabel?
    @IBOutlet weak var trenDate: UILabel?
    @IBOutlet weak var trenTime: UILabel?
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        title = "Просмотр тренировки"
    }
    
    /// Вызывается, чтобы обновить таблицу  с данными о тренировках
    var updateHandler: (() -> ())?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guard let model = self.currentModel else {
            self.navigationController?.popViewController(animated: true)
            return
        }
        
        self.setData(tren: model)
    }
    
    var currentModel: TrenModel?
    
    private func setData(tren: TrenModel) {
        self.nameTren?.text = tren.name
        self.trenType?.text = tren.type
        self.trenDate?.text = tren.date
        self.trenTime?.text = String(tren.time)
    }
    
    @IBAction func chenge(_ sender: Any) {
        guard let changeTrenVC = UIStoryboard(name: "CreateTrenVC", bundle: nil).instantiateInitialViewController() as? CreateTrenVC else { return }
        changeTrenVC.isCreate = false
        changeTrenVC.currentModel = self.currentModel
        changeTrenVC.complitionHandler = { [weak self] in
            self?.navigationController?.popViewController(animated: true)
        }
        self.navigationController?.pushViewController(changeTrenVC, animated: true)
    }
}
