package com.example.iron.weektwonewyorktimes.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.iron.weektwonewyorktimes.Models.Doc
import com.example.iron.weektwonewyorktimes.Models.WEBSIDE
import com.example.iron.weektwonewyorktimes.R
import com.example.iron.weektwonewyorktimes.Views.ContentArticalActivity

class ArticleAdapter (private val context: Context, private val arrayList: List<Doc>?):
    RecyclerView.Adapter<ArticleAdapter.ArticleHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ArticleHolder {
        return ArticleHolder(LayoutInflater.from(context).inflate(R.layout.item_artical, p0, false))
    }

    override fun getItemCount(): Int {
        return arrayList?.size ?: 20

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: ArticleHolder, p1: Int) {
        p0.title?.text = arrayList?.get(p1)?.headline?.main +"\n" + arrayList?.get(p1)?.snippet

        if (arrayList?.get(p1)?.multimedia?.isEmpty()!!) {
            p0.imageHinh?.setImageResource(R.drawable.thenewyorktime)
        } else {
            p0.imageHinh?.let {
                Glide.with(context).load(WEBSIDE + arrayList.get(p1).multimedia?.get(0)?.url).into(it)
            }
        }
        p0.linearLayout?.setOnClickListener {
            val intent = Intent(context,ContentArticalActivity::class.java)
            intent.putExtra("url",arrayList[p1].web_url)
            context.startActivity(intent)
        }


    }

    inner class ArticleHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val imageHinh = itemView?.findViewById<ImageView>(R.id.imageview)
        val title = itemView?.findViewById<TextView>(R.id.txt_title)
        val linearLayout = itemView?.findViewById<LinearLayout>(R.id.linearLayout_item)
    }
}


