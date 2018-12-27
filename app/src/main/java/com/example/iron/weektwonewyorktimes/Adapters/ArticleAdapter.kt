package com.example.iron.weektwonewyorktimes.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.iron.weektwonewyorktimes.Models.Doc
import com.example.iron.weektwonewyorktimes.Models.WEBSIDE
import com.example.iron.weektwonewyorktimes.R

class ArticleAdapter (private val context: Context, private val arrayList: List<Doc>?):
    RecyclerView.Adapter<ArticleAdapter.ArticleHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ArticleHolder {
        return ArticleHolder(LayoutInflater.from(context).inflate(R.layout.item_artical, p0, false))
    }

    override fun getItemCount(): Int {
        return arrayList?.size ?: 20

    }

    override fun onBindViewHolder(p0: ArticleHolder, p1: Int) {
        p0.title?.text = arrayList?.get(p1)?.snippet ?: "new york time"
        if (arrayList?.get(p1)?.multimedia?.isEmpty()!!) {
            p0.imageHinh?.let {
                Glide.with(context).load("https://duythanhcse.files.wordpress.com/2018/05/irontrade.png").into(it)
            }
        } else {
            p0.imageHinh?.let {
                Glide.with(context).load(WEBSIDE + arrayList?.get(p1)?.multimedia?.get(0)?.url).into(it)
            }
        }


    }

    inner class ArticleHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val imageHinh = itemView?.findViewById<ImageView>(R.id.imageview)
        val title = itemView?.findViewById<TextView>(R.id.txt_title)
    }
}


