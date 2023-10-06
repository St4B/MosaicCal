//
//  ContentIntent.swift
//  iosApp
//
//  Created by Vaios Tsitsonis on 2/10/23.
//  Copyright © 2023 orgName. All rights reserved.
//
//
import Foundation
import Mosaic

enum ContentIntent {
    case loadData
    case toggleMode
    case selectDate(date: Mosaic.Kotlinx_datetimeLocalDate)
}
