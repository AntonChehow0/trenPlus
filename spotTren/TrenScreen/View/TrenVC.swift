//
//  TrenVC.swift
//  spotTren
//
//  Created by ARH on 12/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import UIKit

class TrenVC: UIViewController {

    @IBOutlet weak var tableView: UITableView?
    
    private var models = [TrenModel]()
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        let item = UIBarButtonItem(barButtonSystemItem: .add, target: self, action: #selector(addTren))
        navigationItem.rightBarButtonItem = item
        
    }
    
    let refrashControll = UIRefreshControl()

    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.updateData()
        
        self.refrashControll.attributedTitle = NSAttributedString(string: "Идёт обновление...")
        self.refrashControll.addTarget(self, action: #selector(refrashData), for: UIControl.Event.valueChanged)
        self.tableView?.addSubview(self.refrashControll)
        
        tableView?.delegate = self
        tableView?.dataSource = self
    }

    @objc func refrashData() {
        updateData()
        self.refrashControll.endRefreshing()
    }
    
    
    /// Обновляет данные на View
    private func updateData() {
        let loader = AllTrenController()
        loader.load { (models) in
            self.models = models
            DispatchQueue.main.async {
                self.tableView?.reloadData()
            }
        }
    }
    
    @IBAction func logout(_ sender: Any) {
        TokenSaver().save(token: "")
        self.navigationController?.popViewController(animated: true)
    }
    
    /// Получить статистику
    @IBAction func getStatus(_ sender: Any) {
        guard let statusScreen = UIStoryboard(name: "StatusVC", bundle: nil).instantiateInitialViewController() as? StatusVC else { return }
        self.navigationController?.pushViewController(statusScreen, animated: true)
    }
    
    @objc func addTren() {
        guard let create = UIStoryboard(name: "CreateTrenVC", bundle: nil).instantiateInitialViewController() as? CreateTrenVC else { return }
        create.complitionHandler = {
            self.updateData()
        }
        self.navigationController?.pushViewController(create, animated: true)
    }
    
}

extension TrenVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.models.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        let currentModel = self.models[indexPath.row]
        cell.textLabel?.text = currentModel.name
        cell.textLabel?.textAlignment = .center
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        guard let detailInformation = UIStoryboard(name: "TrenWatchVC", bundle: nil).instantiateInitialViewController() as? TrenWatchVC else { return }
        let currentModel = self.models[indexPath.row]
        detailInformation.currentModel = currentModel
        
        detailInformation.updateHandler = { [weak self] in
            self?.updateData()
        }
        
        self.navigationController?.pushViewController(detailInformation, animated: true)
    }
}
