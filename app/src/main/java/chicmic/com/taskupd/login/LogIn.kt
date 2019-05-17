package chicmic.com.taskupd.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import chicmic.com.taskupd.R
import chicmic.com.taskupd.callapi.APIClient
import chicmic.com.taskupd.callapi.APIInterface
import chicmic.com.taskupd.dashboard.DashBoard


import chicmic.com.taskupd.datamodel.User
import chicmic.com.taskupd.utils.Const
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_log_in.view.*
import retrofit2.Call
import retrofit2.Response
import java.net.InetAddress
import java.net.UnknownHostException
import javax.security.auth.callback.Callback

class LogIn : AppCompatActivity() {


    lateinit var apiInterface: APIInterface
    lateinit var mProg:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
       // progress()
        apiInterface = APIClient.getClient().create(APIInterface::class.java)
//Toast.makeText(this@LogIn,isInternetAvailable().toString(),Toast.LENGTH_LONG).show()
    }

fun addLogin(custId:String)
{
    var sharedPreferences=this.getSharedPreferences(Const.LOGIN_DATA,0)
    var editor=sharedPreferences.edit()
    editor.putBoolean(Const.LOGIN_FLAG,true)
    editor.putString(Const.LOGIN_EMAIL,email.text.toString())
    editor.putString(Const.CUST_ID,custId)
    editor.apply()
}
    fun logIn(view: View)
    {
        var user=email.text.toString()
        var password=password.text.toString()
        if(validate()) {
        //   mProg.show()
            if(Const.checkInternet(this)) {
                progbarStart()
                call(user, password)

            }
        }
    }

    fun progbarStart()
    {
       bar.visibility=View.VISIBLE
        layout.visibility=View.GONE
    }
    fun progbarStop()
    {
       bar.visibility=View.GONE
        layout.visibility=View.VISIBLE
    }
    fun progress()
    {
         mProg=ProgressDialog(this)
        mProg.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProg.setCancelable(false)
        mProg.setMessage(getString(R.string.loading))

    }
    fun call(pUser:String,pPassword:String)
    {
        var call=apiInterface.logIn(pUser,pPassword)
        call.enqueue(object: retrofit2.Callback<User> {
            override  fun onResponse(call: Call<User>?, response: Response<User>?) {
                var user=response!!.body()
                if(user.message==Const.LOGIN_SUCCESS&&user.status==Const.LOGIN_STATUS)
                {
                  //  Toast.makeText(this@LogIn,user.customerId.toString(),Toast.LENGTH_LONG).show()
                    Log.d("login_success",user.customerId.toString())
                    addLogin(user.customerId.toString())
                    jump()
                 //   mProg.cancel()
                }
                else
                {
                    Toast.makeText(this@LogIn,user.message.toString(),Toast.LENGTH_LONG).show()
                    Log.d("login",user.message)
                //    mProg.cancel()
                    progbarStop()
                }

            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Log.d("login_fail",t.toString())
              //  mProg.cancel()
                progbarStop()
            }
        })
    }
    fun jump()
    {
        var intent=Intent(this, DashBoard::class.java)
        startActivity(intent)
        finish()
    }
    var mEmailPattern= Const.EMAILPATTERN.toRegex()

    fun validate():Boolean {
       var check=password.text.toString()
        check.replace(" ","")
        if (!mEmailPattern.matches(email.text.toString())) {
            email.setError(getString(R.string.enter_valid_email))
            email.requestFocus()
            return false
        } else if (check.isEmpty()) {
            password.setError(getString(R.string.enter_valid_password))
            password.requestFocus()
            return false
        }
        return true
    }

}
