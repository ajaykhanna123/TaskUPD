package chicmic.com.taskupd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import chicmic.com.taskupd.dashboard.DashBoard

import chicmic.com.taskupd.login.LogIn

import chicmic.com.taskupd.utils.Const
import kotlinx.android.synthetic.main.activity_log_in.*


class Splash : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(

        {

                var sharedPreferences=this.getSharedPreferences(Const.LOGIN_DATA,0)

                if (sharedPreferences.getBoolean(Const.LOGIN_FLAG,false)) {
                    val intent = Intent(this, DashBoard::class.java)
                    startActivity(intent)
                }else
                {
                    val intent = Intent(this, LogIn::class.java)
                    startActivity(intent)
                }




            // close this activity
            finish()
        }, Const.SPLASH_TIME.toLong())
    }
}
