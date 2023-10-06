//
//  ContentViewModel.swift
//  iosApp
//
//  Created by Vaios Tsitsonis on 2/10/23.
//  Copyright © 2023 orgName. All rights reserved.
//
//
import SwiftUI
import Combine
import Foundation
import Mosaic

class ContentViewModel: ObservableObject {
    
    private let now = Date()
    
    private let mosaicDataProvider = MosaicDataProvider()
    
    private var from: Mosaic.Kotlinx_datetimeLocalDate {
        return LocalDateMapperKt.toLocalDate(now)
    }
    
    private var to: Mosaic.Kotlinx_datetimeLocalDate {
        return from.plusYears(years: 3)
    }
    
    @Published var state: ContentState = ContentState(
        isInRangeMode: false,
        daysOfWeek: [],
        months: [],
        selectedDates: Set(),
        selectedDays: Set()
    )
    
    func processIntent(intent: ContentIntent) {
        switch intent {
        case .toggleMode:
            handleToggleMode()
        case .loadData:
            handleLoadData()
        case .selectDate(let date):
            handleSelectDate(date: date)
        }
    }
    
    private func handleToggleMode() {
        var currentState = state
        let willBeInRangeMode = !currentState.isInRangeMode
        
        let minSelectedDate = LocalDateMapperKt.minimum(currentState.selectedDates)
        var selectedDates = Set<Mosaic.Kotlinx_datetimeLocalDate>()
        var selectedDays = Set<Mosaic.Kotlinx_datetimeDayOfWeek>()
        
        if let minDate = minSelectedDate {
            selectedDates.insert(minDate)
            selectedDays.insert(minDate.toDayOfWeek())
        }
                
        let mapper = MosaicCalDayItemKt.mapperAdapter(
            isInRangeMode: willBeInRangeMode,
            selectedDates: selectedDates,
            selectedDays: selectedDays
        )
                
        state = ContentState(
            isInRangeMode: willBeInRangeMode,
            daysOfWeek: currentState.daysOfWeek,
            months: mosaicDataProvider.provideMonths(from: from, to: to, mapper: mapper).map { $0 as! MonthGrid<MosaicCalDayItem> },
            selectedDates: selectedDates,
            selectedDays: selectedDays
        )

    }
    
    private func handleLoadData() {
        let currentState = state
        
        let mapper = MosaicCalDayItemKt.mapperAdapter(
            isInRangeMode: currentState.isInRangeMode,
            selectedDates: currentState.selectedDates,
            selectedDays: currentState.selectedDays
        )
        
        state = ContentState(
            isInRangeMode: currentState.isInRangeMode,
            daysOfWeek: mosaicDataProvider.provideDaysOfWeek(),
            months: mosaicDataProvider.provideMonths(from: from, to: to, mapper: mapper).map { $0 as! MonthGrid<MosaicCalDayItem> },
            selectedDates: currentState.selectedDates,
            selectedDays: currentState.selectedDays
        )
    }
    
    private func handleSelectDate(date: Mosaic.Kotlinx_datetimeLocalDate) {
        var currentState = state
        
        let hasOnlyOneDateSelected = currentState.selectedDates.count == 1
        let isInRangeMode = currentState.isInRangeMode
        
        var selectedDates = Set<Mosaic.Kotlinx_datetimeLocalDate>()
        var selectedDays = Set<Mosaic.Kotlinx_datetimeDayOfWeek>()
        
        if hasOnlyOneDateSelected && isInRangeMode {
            selectedDates = currentState.selectedDates.union([date])
        } else {
            selectedDates.insert(date)
        }
        
        for selectedDate in selectedDates {
            selectedDays.insert(selectedDate.toDayOfWeek())
        }
        
        let mapper = MosaicCalDayItemKt.mapperAdapter(
            isInRangeMode: isInRangeMode,
            selectedDates: selectedDates,
            selectedDays: selectedDays
        )
   
        Calendar.current.firstWeekday

        
        state = ContentState(
            isInRangeMode: currentState.isInRangeMode,
            daysOfWeek: currentState.daysOfWeek,
            months: mosaicDataProvider.provideMonths(from: from, to: to, mapper: mapper).map { $0 as! MonthGrid<MosaicCalDayItem> },
            selectedDates: selectedDates,
            selectedDays: selectedDays)
        
    }
}
