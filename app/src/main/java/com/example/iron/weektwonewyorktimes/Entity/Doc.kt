package com.example.iron.weektwonewyorktimes.Models

data class Doc(
    val _id: String?,
    val byline: Byline?,
    val document_type: String?,
    val headline: Headline?,
    val keywords: List<Keyword>?,
    val multimedia: List<Multimedia>?,
    val news_desk: String?,
    val print_page: String?,
    val pub_date: String?,
    val score: Double?,
    val section_name: String?,
    val snippet: String?,
    val source: String?,
    val type_of_material: String?,
    val web_url: String?,
    val word_count: Int?
)