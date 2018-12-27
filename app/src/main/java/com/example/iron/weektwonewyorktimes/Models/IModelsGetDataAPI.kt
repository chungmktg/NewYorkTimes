package com.example.iron.weektwonewyorktimes.Models


interface IModelsGetDataAPI {
    fun getSucces( doclist : List<Doc>?)
    fun getFailed(message: String)
}