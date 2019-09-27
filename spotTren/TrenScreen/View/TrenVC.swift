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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView?.delegate = self
        tableView?.dataSource = self
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        let loader = AllTrenController()
        loader.load { [weak self] (models) in
            DispatchQueue.main.async {
                self?.models = models
                self?.tableView?.reloadData()
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
            let loader = AllTrenController()
            loader.load { (model) in
                self?.models = model
                DispatchQueue.main.async {
                    self?.tableView?.reloadData()
                }
            }
        }
        
        self.navigationController?.pushViewController(detailInformation, animated: true)
    }
}
