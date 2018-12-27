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

    val filter = HashMap<String, String?>()

    fun getDataAPIbyFilter(value: ArrayList<String>) {
        val beginDay = value[0]
        val sortby = value.filter { it.equals(NEWEST) || it.equals(OLDEST) }
        val checkbox = value.filter { it == CHECKBOX_ART || it == CHECKBOX_FASHION || it == CHECKBOX_SPORT }
        var checbockStyle = ""
        for (i in 0 until checkbox.size) {
            checbockStyle = checbockStyle.plus(checkbox[i] + ",")
        }

        Log.d("checkk",checbockStyle)
        filter.clear()
        filter["begin_date"] = beginDay
        filter["sort"] = sortby[0]
        filter["fq"] = checbockStyle
        filter["api-key"] = API_KEY
        getRetrofit(filter)
    }

    fun getdataAPI() {
        filter.clear()
        filter["sort"] = SORT
        filter["begin_date"] = BEGIN_DAY
        filter["fq"] = FQ
        filter["api-key"] = API_KEY
        getRetrofit(filter)
    }

    fun getRetrofitFromSeach(inputText : String?){
        filter.clear()
        filter["sort"] = SORT
        filter["begin_date"] = BEGIN_DAY
        filter["fq"] = inputText
        filter["api-key"] = API_KEY
        Log.d("getRetrofitFromSeach1",filter.toString())
        getRetrofit(filter)
    }

    private fun getRetrofit(filter: HashMap<String, String?>) {

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
                        Log.d("callfaile","callsuccess")
                        iModelsGetDataAPI.getSucces(response.body()?.response?.docs)
                    }

                }
                override fun onFailure(call: Call<Article>, t: Throwable) {
                    iModelsGetDataAPI.getFailed("load failed")
                }
            })
    }


}