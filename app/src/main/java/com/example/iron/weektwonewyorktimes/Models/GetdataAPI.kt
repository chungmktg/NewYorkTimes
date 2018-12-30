package com.example.iron.weektwonewyorktimes.Models

import android.util.Log
import com.example.iron.weektwonewyorktimes.API.GetArticle
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GetdataAPI(var iModelsGetDataAPI: IModelsGetDataAPI) {

    private val filter = HashMap<String, String?>()
    lateinit var currentFilter : HashMap<String, String?>

    fun getDataAPIbyFilter(value: ArrayList<String>) {
        val beginDay = value[0]
        val sortby = value.filter { it.equals(NEWEST) || it.equals(OLDEST) }
        val checkbox = value.filter { it == CHECKBOX_ART || it == CHECKBOX_FASHION || it == CHECKBOX_SPORT }
        var checbockStyle = ""
        for (i in 0 until checkbox.size) {
            checbockStyle = checbockStyle.plus(checkbox[i] + ",")
        }
        getFilter(sortby[0], beginDay, checbockStyle, PAGE)
        getRetrofit(filter, false)
    }
    fun getdataAPI() {
        getFilter(SORT, BEGIN_DAY, FQ, PAGE)
        getRetrofit(filter, false)
    }
    fun getRetrofitFromSeach(inputText : String?){
        getFilter(SORT, BEGIN_DAY,inputText, PAGE)
        getRetrofit(filter, false)
    }
    fun getDataLoadMorePage(page:Int){
        currentFilter = HashMap(filter)
        currentFilter["page"] = page.toString()
        Log.d("current",currentFilter.toString())
        Log.d("current",filter.toString())
        getRetrofit(currentFilter, true)
    }
    fun getFilter(sort: String? = SORT, begin_date : String? = BEGIN_DAY, fq : String ?= FQ, page : String? = PAGE){
        filter["sort"] = sort
        filter["begin_date"] = begin_date
        filter["fq"] = fq
        filter["api-key"] = API_KEY
        filter["page"] = page
    }

    private fun getRetrofit(filter: HashMap<String, String?>, isLoadMore : Boolean) {

        val retrofit = Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        ).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(
            BASE_URL
        ).build()
        val postApi = retrofit.create(GetArticle::class.java)
        postApi.getArticletotal(filter)
            .enqueue(object : Callback<Article> {
                override fun onResponse(call: Call<Article>, response: Response<Article>) {
                    if(response.body() == null){
                        Log.d("callfaile","callfelt")
                    }else{
                        Log.d("callsuccess","callsuccess")
                        if(!isLoadMore){
                            iModelsGetDataAPI.getSucces(response.body()?.response?.docs)
                        }else{
                            iModelsGetDataAPI.getSuccesLoadMore(response.body()?.response?.docs)
                        }

                    }

                }
                override fun onFailure(call: Call<Article>, t: Throwable) {
                    iModelsGetDataAPI.getFailed("load failed")
                }
            })
    }


}