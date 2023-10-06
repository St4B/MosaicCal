//
//  ContentState.swift
//  iosApp
//
//  Created by Vaios Tsitsonis on 2/10/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Mosaic

struct ContentState {
    let isInRangeMode: Bool
    let daysOfWeek: [Mosaic.Kotlinx_datetimeDayOfWeek]
    let months: [MonthGrid<MosaicCalDayItem>]
    let selectedDates: Set<Mosaic.Kotlinx_datetimeLocalDate>
    let selectedDays: Set<Mosaic.Kotlinx_datetimeDayOfWeek>
}
