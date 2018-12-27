package com.example.iron.weektwonewyorktimes.API

import com.example.iron.weektwonewyorktimes.Models.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GetArticle {



    @GET("v2/articlesearch.json?begin_date=20160112&sort=oldest&fq=news_desk:(\"Education\"%20\"Health\")&api-key=227c750bb7714fc39ef1559ef1bd8329&page=")
    abstract fun getArticle(@Query("pa") page : Int): Call<Article>

    @GET("v2/articlesearch.json")
    abstract fun getArticletotal(@QueryMap filter: HashMap<String, String?>): Call<Article>

    @GET("v2/articlesearch.json")
    abstract fun getArticletotal1(@Query("sort" ) sort : String, @Query("begin_date" ) begin : String, @Query("fq" ) fq : String, @Query("api-key") api : String): Call<Article>


}