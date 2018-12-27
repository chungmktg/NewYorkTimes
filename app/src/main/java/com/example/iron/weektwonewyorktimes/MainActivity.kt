package com.example.iron.weektwonewyorktimes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.iron.weektwonewyorktimes.Adapters.ArticleAdapter
import com.example.iron.weektwonewyorktimes.Models.Doc
import com.example.iron.weektwonewyorktimes.Presenters.FiterPresenter
import com.example.iron.weektwonewyorktimes.Presenters.MainPresenter
import com.example.iron.weektwonewyorktimes.Views.FilterFragment
import com.example.iron.weektwonewyorktimes.Views.IView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IView {
    lateinit var mainPresenter: MainPresenter
    private lateinit var adapters: ArticleAdapter
    lateinit var filterPresenter: FiterPresenter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        filterPresenter =  FiterPresenter(this)
        mainPresenter= MainPresenter(this)
        mainPresenter.loadData()


    }

    override fun getDataSuccess(listDemo: List<Doc>?) {
        adapters = ArticleAdapter(this@MainActivity, listDemo)
        val manager =  StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        recycleview_main.layoutManager = manager
        recycleview_main.setHasFixedSize(true)
        recycleview_main.adapter = adapters
        adapters.notifyDataSetChanged()
    }

    override fun getDataFailed(message: String) {
        Log.d("testfail",message)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(string: String?): Boolean {
                mainPresenter.getFilterSeach(string)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })

        return true
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.filter_menu -> {
                val fm = supportFragmentManager
                val filterFragment = FilterFragment()
                filterFragment.show(fm,"filter")
            }


        }
        return super.onOptionsItemSelected(item)
    }

}