package com.example.iron.weektwonewyorktimes.API

import com.example.iron.weektwonewyorktimes.Models.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GetArticle {
    @GET("v2/articlesearch.json")
    fun getArticletotal(@QueryMap filter: HashMap<String, String?>): Call<Article>
}