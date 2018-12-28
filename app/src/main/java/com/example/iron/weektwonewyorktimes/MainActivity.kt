package com.example.iron.weektwonewyorktimes

import android.content.Context
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
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.iron.weektwonewyorktimes.Models.EndlessRecyclerViewScrollListener
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.support.v4.content.ContextCompat
import java.io.IOException


class MainActivity : AppCompatActivity(), IView {
    lateinit var mainPresenter: MainPresenter
    private lateinit var adapters: ArticleAdapter
    lateinit var filterPresenter: FiterPresenter

    private var scrollListener: EndlessRecyclerViewScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkInternet()
        filterPresenter =  FiterPresenter(this)
        mainPresenter= MainPresenter(this)
        mainPresenter.loadData()


    }

    private fun checkInternet() {
        if (!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "Opps looks like " +
                    "network connectivity problem. Turn on Internet and click Refresh in menu option",
                Toast.LENGTH_LONG).show();
        }

        if (!isOnline()) {
            Toast.makeText(getApplicationContext(), "Your device is not online, " +
                    "check wifi and try again!",
                Toast.LENGTH_LONG).show();
        }
    }

    override fun getDataSuccess(listDemo: List<Doc>?) {
        adapters = ArticleAdapter(this@MainActivity, listDemo)
        val manager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        recycleview_main.layoutManager = manager
        recycleview_main.setHasFixedSize(true)
        recycleview_main.adapter = adapters
        adapters.notifyDataSetChanged()
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE)
        recycleview_main.addOnScrollListener(
            object : EndlessRecyclerViewScrollListener(manager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    loadNextDataFromApi(page)
                }
            })

    }

    private fun loadNextDataFromApi(page: Int) {
        Toast.makeText(this, "Loading more..."+ page, Toast.LENGTH_SHORT).show()
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
            R.id.refresh -> {
                mainPresenter.loadData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    private fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return false
    }


}