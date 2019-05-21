package chicmic.com.taskupd.dashboard

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import chicmic.com.taskupd.R
import chicmic.com.taskupd.callapi.APIClient
import chicmic.com.taskupd.callapi.APIInterface
import chicmic.com.taskupd.callapi.UserFetchListener
import chicmic.com.taskupd.database.UserDatabase
import chicmic.com.taskupd.datamodel.LoadData
import chicmic.com.taskupd.datamodel.Search
import chicmic.com.taskupd.datamodel.User
import chicmic.com.taskupd.helper.Constants
import chicmic.com.taskupd.helper.Utils
import chicmic.com.taskupd.login.LogIn
import chicmic.com.taskupd.responsemodel.CustomerData
import chicmic.com.taskupd.responsemodel.Data
import chicmic.com.taskupd.responsemodel.ListData
import chicmic.com.taskupd.utils.Const
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.app_bar_dash_board.*
import retrofit2.Call
import retrofit2.Response
import java.net.URL
import chicmic.com.taskupd.utils.*
class DashBoard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,UserFetchListener {

    lateinit var mApiInterface: APIInterface
    lateinit var mAdapter:AdapterRecycleView
    lateinit var mAdapterSearch:AdapterRecycleView
    var mCustomerId:String?=null
    var mEmail:String?=null
    var mPageIndex:Int=0
    var mPageSize:Int=10
    var mRowCount:Int?=null
    var mSearch=false
    var mSearchList=mutableListOf<CustomerData>()
    var mSearchIndex=0
    var mSearchRowCount:Int?=null
    var mSearchName:String=""
    private var mDatabase: UserDatabase? = null
    var mManager:APIClient?=null

    var isLoading=false
    var responseflag=false


    public var mList= mutableListOf<CustomerData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        setSupportActionBar(toolbar)

        mDatabase = UserDatabase(this)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        nav_view.setNavigationItemSelectedListener(this)
        mApiInterface = APIClient.getClient().create(APIInterface::class.java)
        getLogin()
        nav_view.getHeaderView(0)
        val header = nav_view.getHeaderView(0)
        val headerEmail = header.findViewById<TextView>(R.id.header_email)
        headerEmail!!.text = mEmail


        loadData()
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                as RecyclerView.LayoutManager?
        recyclerview.setHasFixedSize(true)
        searchAdapterInit()
        listAdapterInit()

        initScrollListener()


        swipeRefreshLayout.setOnRefreshListener {
            if(!Const.checkInternet(this))
            {
                swipeRefreshLayout.isRefreshing=false
                isLoading=true
                responseflag=true

            }
            else
            {
               swipeRefreshLayout.isRefreshing=true
                refresh()
                isLoading=true
                responseflag=true
            }

    }




    }
    override fun onDeliverAllUsers(list: List<CustomerData>) {

    }

    override fun onDeliverUser(customerData: CustomerData) {
        mAdapter.addUser(customerData)
    }

    override fun onHideDialog() {

    }
    fun searchAdapterInit()
    {
        mAdapterSearch=AdapterRecycleView(mSearchList)
        recyclerview.adapter=mAdapterSearch
    }
    fun listAdapterInit()
    {
        mAdapter=AdapterRecycleView(mList)
        recyclerview.adapter=mAdapter
    }
    fun refresh()
    {
        if(Const.checkInternet(this)) {
           mPageIndex=0
           mList.clear()
           response()
           mAdapter.notifyDataSetChanged()


         }

    }
    fun search(view: View)
    {
        searchbar.visibility=View.VISIBLE
        //searchbutton.visibility=View.GONE
        swipeRefreshLayout.isEnabled=false

    }
    fun back(view: View)
    {
        searchbar.visibility=View.GONE
        //searchbutton.visibility=View.VISIBLE
        onBackPressed()
    }
    fun close(view: View)
    {
        close.visibility=View.GONE
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else if(searchbar.visibility==View.VISIBLE)
        {
            searchbar.visibility==View.GONE
        }
        else {
            super.onBackPressed()
        }
    }
    fun onInput()
    {
        mSearch=true
        recyclerview.adapter=mAdapterSearch
        mSearchIndex=0
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_send -> {
                removeLogin()
                gotoLogIn()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    fun removeLogin()
    {

        var sharedPreferences=this.getSharedPreferences(Const.LOGIN_DATA,0)
        var editor=sharedPreferences.edit()
        editor.putBoolean(Const.LOGIN_FLAG,false)
        editor.apply()
    }
    fun gotoLogIn()
    {
        startActivity(Intent(this, LogIn::class.java))
        finish()
    }
    fun getLogin()
    {
        val sharedPreferences=this.getSharedPreferences(Const.LOGIN_DATA,0)
        mCustomerId=sharedPreferences.getString(Const.CUST_ID,"")
        mEmail=sharedPreferences.getString(Const.LOGIN_EMAIL,"")

    }
    fun loadData()
    {
        // var index=checkIndex(mPageIndex)
        progressBar.visibility= View.VISIBLE
        if(Const.checkInternet(this)) {
            responseflag=true
            isLoading=true
            response()

        }
        else {
            progressBar.visibility = View.GONE
            isLoading = false


            //swipeRefreshLayout.setOnRefreshListener { swipeRefreshLayout.isRefreshing=false }
            //swipeRefreshLayout.visibility=View.GONE


            getFeedFromDatabase()

        }
    }
    fun response()
    {


//        if (!mSearch)
//        {
            val obj = LoadData(mCustomerId!!, mPageIndex.toString(), mPageSize.toString())
            callData( mApiInterface.listLoad(obj))
        //}
//        else{
//            val obj = Search(mCustomerId!!, mPageIndex.toString(), mPageSize.toString(),mSearchName)
//            callData( mApiInterface.search(obj))
//        }

//        swipeRefreshLayout.setOnRefreshListener {
//            if (!Utils.isNetworkAvailable(this)) {
//                swipeRefreshLayout.isRefreshing = false
//            }
//
//            else
//            {
//
//                swipeRefreshLayout.isRefreshing = true
//                refresh()
//            }
//        }

    }
    fun callData(call: Call<ListData>)
    {

        call.enqueue(object : retrofit2.Callback<ListData> {

            override fun onResponse(call: Call<ListData>?, response: Response<ListData>?) {
                val listData = response!!.body()
                if (listData.message == Const.LIST_MESSAGE && listData.status == Const.LIST_STATUS) {

                    Log.d("List_response_success", listData.message)
                    var data: Data = listData.data
//                    if(mSearch)
//                    {
//                        mSearchRowCount= data.rowCount
//                        mSearchList.addAll(data.customerData)
//                        mAdapterSearch.notifyDataSetChanged()
//                        mSearchIndex=mSearchIndex+10
//                    }
                    //else
                   // {
                        mRowCount = data.rowCount
                        mList.addAll(data.customerData)
                        mAdapter.notifyDataSetChanged()
                        mPageIndex = mPageIndex + 10
                   // val task = SaveIntoDatabase()

                    var customerList=data.customerData

                    for (i in customerList.indices) {
                        val customer = customerList.get(i)

                        mDatabase?.addUser(customer)

//                        val task = SaveIntoDatabase()
//                        task.execute(customer)

                       // mAdapter.addUser(customer)
                    }


                   // }
                    progressBar.visibility = View.GONE
                    isLoading = false
                    responseflag = true
                    swipeRefreshLayout.isRefreshing=false
                } else {
                    Log.d("List_response", listData.message)
                }
            }

            override fun onFailure(call: Call<ListData>?, t: Throwable?) {
                Log.d("List_response_fail", t.toString())
            }

        })
    }
fun checkLimit():Boolean
{
    if(mRowCount!! >mPageIndex)
    {
       return true
    }

    return false
}





    private fun initScrollListener() {
        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                loading()
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })


    }
    fun loading()
    {
        val linearLayoutManager = recyclerview.layoutManager as LinearLayoutManager
        if (isLoading && responseflag && checkLimit())
        {
            loadData()

            isLoading =false

        }
        else if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mList.size - 1)
        {
            isLoading = true
            responseflag=true
        }
    }
//    inner class SaveIntoDatabase : AsyncTask<CustomerData, Void, Void>() {
//
//
//        private val TAG = SaveIntoDatabase::class.java.simpleName
//
//        override fun onPreExecute() {
//            super.onPreExecute()
//        }
//
//        override fun doInBackground(vararg params: CustomerData): Void? {
//
//            val user = params[0]
//
//            try {
//
//
//                mDatabase!!.addUser(user)
//
//            } catch (e: Exception) {
//                Log.d(TAG, e.message)
//            }
//
//            return null
//        }
//    }

    private fun getFeedFromDatabase() {

        mList= mDatabase?.users as MutableList<CustomerData>
    }


}
