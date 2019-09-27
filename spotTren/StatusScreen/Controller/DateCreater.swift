//
//  DateCreater.swift
//  spotTren
//
//  Created by В М on 24/09/2019.
//  Copyright © 2019 ARH. All rights reserved.
//

import Foundation


/// Объект работающий с датами
class DateCreator {
    
    /// Метод возвращающий даты текущей недели
    func getDatesForCurrentWeak() -> [String] {
        var calendar = Calendar.current
        calendar.locale = Locale(identifier: "ru_RU")
        let today = calendar.startOfDay(for: Date())
        let dayOfWeek = calendar.component(.weekday, from: today)
        let weekdays = calendar.range(of: .weekday, in: .weekOfYear, for: today)!
        var days = (weekdays.lowerBound ... weekdays.upperBound)
            .compactMap { calendar.date(byAdding: .day, value: $0 - dayOfWeek, to: today) }
            //.filter { !calendar.isDateInWeekend($0) }
        days.remove(at: 0)
        let formatter = DateFormatter()
        formatter.dateFormat = "dd.MM.yy"

        let strings = days.map { formatter.string(from: $0) }
        
        return strings
        
    }
    
    /// Возвращает даты за текущий месяц
    func getDatesForCurrentMounce() -> [String] {
        var calendar = Calendar.current
        calendar.locale = Locale(identifier: "ru_RU")
        let currentDate = Date()
        var components = calendar.dateComponents(in: TimeZone.current, from: currentDate)
        components.day = 1
        let startMonthDate = components.date
        let today = calendar.startOfDay(for:  startMonthDate ?? Date())
        let dayOfWeek = calendar.component(.weekday, from: today)
        let weekdays = calendar.range(of: .day, in: .month, for: today)!
        var days = (weekdays.lowerBound ... weekdays.upperBound)
            .compactMap { calendar.date(byAdding: .day, value: $0 - dayOfWeek, to: today) }
        let formatter = DateFormatter()
        formatter.dateFormat = "dd.MM.yy"
        _ = days.popLast()
        let strings = days.map { formatter.string(from: $0) }
        
        return strings
    }
    
    
    /// Возвращает даты за периуд времени
    func getDatesForPeriude(startDate: String, endDate: String) -> [String] {
        var calendar = Calendar.current
        calendar.locale = Locale(identifier: "ru_RU")
        let currentDate = Date()
        var components = calendar.dateComponents(in: TimeZone.current, from: currentDate)
        components.day = 1
        let startMonthDate = components.date
        let today = calendar.startOfDay(for:  startMonthDate ?? Date())
        let dayOfWeek = calendar.component(.weekday, from: today)
        let weekdays = calendar.range(of: .day, in: .year, for: today)!
        var days = (weekdays.lowerBound ... weekdays.upperBound)
            .compactMap { calendar.date(byAdding: .day, value: $0 - dayOfWeek, to: today) }
        let formatter = DateFormatter()
        formatter.dateFormat = "dd.MM.yy"
        _ = days.popLast()
        var strings = days.map { formatter.string(from: $0) }
        
        strings.append(startDate)
        var i = 0
        while strings[i] != startDate {
            i += 1
        }
        strings.popLast()
        for k in 0..<i {
            strings.remove(at: 0)
        }
        strings.append(endDate)
        i = 0
        while strings[i] != endDate {
            i += 1
        }
        strings.popLast()
        for _ in i..<strings.count-1 {
            strings.popLast()
        }
        
        return strings    }
    
    
}
