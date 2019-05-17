package chicmic.com.taskupd.callapi

import chicmic.com.taskupd.datamodel.LoadData
import chicmic.com.taskupd.datamodel.Search
import chicmic.com.taskupd.datamodel.User
import chicmic.com.taskupd.responsemodel.ListData

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @FormUrlEncoded
    @POST("login_api.php")
    fun logIn(@Field("email") email:String,@Field("password") password:String): Call<User>

    @POST("customer-api.php")
    fun listLoad(@Body obj:LoadData): Call<ListData>

    @POST("customer-api.php")
    fun search(@Body obj:Search): Call<ListData>
}
