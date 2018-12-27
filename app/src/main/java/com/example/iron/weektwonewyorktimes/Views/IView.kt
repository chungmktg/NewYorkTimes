package com.example.iron.weektwonewyorktimes.Views

import com.example.iron.weektwonewyorktimes.Models.Doc

interface IView {
    fun getDataSuccess(listDemo: List<Doc>?)
    fun getDataFailed(message: String)
}