//
//  StatusVC.swift
//  spotTren
//
//  Created by В М on 24/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import UIKit

class StatusVC: UIViewController {
 
    private let api = StatisticApiController()
    private let dateCreator = DateCreator()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "Получение статистики за периуд времени"
    }
 
    @IBAction func getStatusForWeak(_ sender: Any) {
        let currendWeekDates = dateCreator.getDatesForCurrentWeak()
        api.getStatistic(dateList: currendWeekDates) { (statisticModel) in
            print("за неделю ---> \(statisticModel)")
        }
    }
    
    @IBAction func getStatusForMonth(_ sender: Any) {
        let currentMonthDates = dateCreator.getDatesForCurrentMounce()
        api.getStatistic(dateList: currentMonthDates) { (statisticModel) in
            print("за месяц ---> \(statisticModel)")
        }
    }
    
    @IBAction func getStatusForTime(_ sender: Any) {
        
    }
}
