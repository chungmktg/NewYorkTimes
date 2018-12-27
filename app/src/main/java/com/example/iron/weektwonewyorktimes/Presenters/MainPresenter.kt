package com.example.iron.weektwonewyorktimes.Presenters

import android.util.Log
import com.example.iron.weektwonewyorktimes.Models.Doc
import com.example.iron.weektwonewyorktimes.Models.GetdataAPI
import com.example.iron.weektwonewyorktimes.Models.IModelsGetDataAPI
import com.example.iron.weektwonewyorktimes.Views.IView

class MainPresenter: IModelsGetDataAPI {
    private var iView: IView?=null
    private var getdataAPI: GetdataAPI = GetdataAPI(this)

    constructor(iView: IView){
        this.iView=iView
    }

    fun loadData(){
        getdataAPI.getdataAPI()
    }

    fun getFilterSeach(string: String?){
        getdataAPI.getRetrofitFromSeach(string)
    }
    override fun getSucces(doclist: List<Doc>?) {
        Log.d("mainpresentergetsuccess",doclist.toString())
        iView?.getDataSuccess(doclist)
    }

    override fun getFailed(message: String) {
        iView?.getDataFailed(message)
    }

}