package com.bismillah.subfundamental2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel:ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()

    fun getListUser(usernameku: String){
        var users = arrayListOf<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=${usernameku}"
        client.addHeader("Authorization","token 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")
        client.addHeader("User-Agent","request")
        client.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val result = String(responseBody)
                try{
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")
                    for(i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val id_user = item.getInt("id")
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = User(photo = avatar,username =  username, id = id_user)
                        users.add(user)
                    }
                    listUsers.postValue(users)
                }
                catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getFollowers(names: String?){
        var users = arrayListOf<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/${names}/followers"
        client.addHeader("Authorization","token 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")
        client.addHeader("User-Agent","request")
        client.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val result = String(responseBody)
                try{
                    val items = JSONArray(result)
                    for(i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val id_user = item.getInt("id")
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = User(photo = avatar, username = username, id = id_user)
                        users.add(user)
                    }
                    listUsers.postValue(users)
                }
                catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getFollowing(names: String?){
        var users = arrayListOf<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/${names}/following"
        client.addHeader("Authorization","token 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")
        client.addHeader("User-Agent","request")
        client.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val result = String(responseBody)
                try{
                    val items = JSONArray(result)
                    for(i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val id_user = item.getInt("id")
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = User(photo = avatar,username =  username, id = id_user)
                        users.add(user)
                    }
                    listUsers.postValue(users)
                }
                catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    fun getUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }
}
