package com.example.iron.weektwonewyorktimes.Presenters

import com.example.iron.weektwonewyorktimes.Models.Doc
import com.example.iron.weektwonewyorktimes.Models.GetdataAPI
import com.example.iron.weektwonewyorktimes.Models.IModelsGetDataAPI
import com.example.iron.weektwonewyorktimes.Views.IView

class FiterPresenter : IModelsGetDataAPI {


    private var iView: IView? = null
    private var getdataAPI: GetdataAPI?=GetdataAPI(this)


    constructor(iView: IView){
        this.iView = iView
    }

    fun getFilter(listValueFilter: ArrayList<String>){
        getdataAPI?.getDataAPIbyFilter(listValueFilter)

    }

    override fun getSuccesLoadMore(doclist: List<Doc>?) {
    }
    override fun getSucces(doclist: List<Doc>?) {
        iView?.getDataSuccess(doclist)
    }

    override fun getFailed(message: String) {
        iView?.getDataFailed(message)
    }
}