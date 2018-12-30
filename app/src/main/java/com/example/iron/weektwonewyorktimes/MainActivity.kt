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
import android.net.ConnectivityManager
import java.io.IOException
import android.widget.AbsListView


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), IView {


    lateinit var mainPresenter: MainPresenter
    private lateinit var adapters: ArticleAdapter
    lateinit var filterPresenter: FiterPresenter
    var pagecurrent : Int = 0
    var isScrolling : Boolean = false
    var pastVisibleItems = 0
    var currentItems: Int = 0
    var totalItems:Int = 0
    var arrayArticalLoadMore : ArrayList<Doc> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkInternet()
        filterPresenter =  FiterPresenter(this)
        mainPresenter= MainPresenter(this)
        mainPresenter.loadData()
    }

    private fun checkInternet() {
        if (!isOnline()) {
            Toast.makeText(
                applicationContext, "Your phone is not online, " +
                    "check wifi and click refresh in menu !",
                Toast.LENGTH_LONG).show()
        }
    }
    override fun getDataSuccess(listDemo: List<Doc>?) {
        arrayArticalLoadMore = ArrayList(listDemo)
        adapters = ArticleAdapter(this@MainActivity, listDemo)
        val manager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recycleview_main.layoutManager = manager
        recycleview_main.setHasFixedSize(true)
        recycleview_main.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = manager.childCount
                totalItems = manager.itemCount
                var firstVisibleItems: IntArray? = null
                firstVisibleItems = manager.findFirstVisibleItemPositions(firstVisibleItems)
                if(firstVisibleItems != null && firstVisibleItems.isNotEmpty()) {
                    pastVisibleItems = firstVisibleItems[0];
                }
                if (isScrolling){
                    if ((currentItems + pastVisibleItems) >= totalItems){
                        isScrolling = false
                        Toast.makeText(this@MainActivity, "Loading more...", Toast.LENGTH_SHORT).show()
                        loadNextDataFromApi(pagecurrent)
                    }
                }

            }
        })
        recycleview_main.adapter = adapters
        adapters.notifyDataSetChanged()
    }
    override fun getDataSuccessLoadMore(listDemo: List<Doc>?) {
        if (listDemo != null) {
            for(i in listDemo)
                arrayArticalLoadMore.add(i)
        }
        adapters = ArticleAdapter(this@MainActivity, arrayArticalLoadMore)
        val manager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recycleview_main.layoutManager = manager
        recycleview_main.setHasFixedSize(true)
        recycleview_main.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = manager.childCount
                totalItems = manager.itemCount
                var firstVisibleItems: IntArray? = null
                firstVisibleItems = manager.findFirstVisibleItemPositions(firstVisibleItems)
                if(firstVisibleItems != null && firstVisibleItems.isNotEmpty()) {
                    pastVisibleItems = firstVisibleItems[0];
                }
                if (isScrolling){
                    if ((currentItems + pastVisibleItems) >= totalItems){
                        isScrolling = false
                        Toast.makeText(this@MainActivity, "Loading more...", Toast.LENGTH_SHORT).show()
                        loadNextDataFromApi(pagecurrent)
                    }
                }

            }
        })
        recycleview_main.adapter = adapters
        adapters.notifyDataSetChanged()
    }
    private fun loadNextDataFromApi(page:Int) {
        pagecurrent = page + 1
        mainPresenter.getDataLoadMorePage(pagecurrent)

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