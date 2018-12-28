package com.example.iron.weektwonewyorktimes.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.iron.weektwonewyorktimes.R
import kotlinx.android.synthetic.main.activity_content_artical.*

class ContentArticalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_artical)
        val intent = intent.getStringExtra("url")
        webviewArtical.loadUrl(intent)
    }
}
