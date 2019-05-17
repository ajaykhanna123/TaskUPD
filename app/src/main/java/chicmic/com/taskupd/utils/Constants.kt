package chicmic.com.taskupd.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo

import android.widget.Toast
import chicmic.com.taskupd.R


object Const{

    const val EMAILPATTERN="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
 //   const val PASSWORDPATTERN="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\\\S+\$).{6,}\$"

    const val BASE_URL="https://updurns.com/api/"
    const val SPLASH_TIME=2000
    const val LOGIN_SUCCESS="Login successfully."
    const val LOGIN_STATUS=1

    const val LIST_STATUS=200
    const val LIST_MESSAGE="Data Found."
const val LOGIN_DATA="Logindata"
    const val LOGIN_FLAG="loginflag"
    const val LOGIN_EMAIL="loginemail"
    const val CUST_ID="id"
    @SuppressLint("MissingPermission")
    fun checkInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED) {
            return true


        } else {

            Toast.makeText(context,"Network Not Available",Toast.LENGTH_SHORT).show()
            return false

        }
    }
}