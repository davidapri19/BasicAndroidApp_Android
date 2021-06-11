package com.bismillah.subfundamental2

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.bismillah.subfundamental2.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.bismillah.subfundamental2.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var avatar: String
    private lateinit var usernameku: String
    private lateinit var perusahaan: String
    private lateinit var lokasi: String
    var statusFavorite = false
    //untuk database
    private lateinit var userHelper: UserHelper
    private lateinit var favoriteList: ArrayList<User>
    //private val userItems = User()


    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        //untuk result
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()

        val user = intent.getStringExtra(EXTRA_USER)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = user
        binding.viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        setStatusfavorite(statusFavorite)
        getDetailUser(user)
    }


    private fun getDetailUser(username: String?) {

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/${username}"
            client.addHeader("Authorization","token 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")
            client.addHeader("User-Agent","request")
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val item = JSONObject(result)

                    avatar = item.getString("avatar_url")
                    usernameku = item.getString("login")
                    perusahaan = item.getString("company")
                    lokasi = item.getString("location")
                    Glide.with(applicationContext)
                        .load(avatar)
                        .apply(RequestOptions().override(55, 55))
                        .into(binding.imgDetail)
                    binding.usernameDetail.text = usernameku
                    binding. descriptionDetail.text = StringBuilder(perusahaan).append(" | ${lokasi}")
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
        binding.btnFavorite.setOnClickListener{
            //penggunaan helper
            statusFavorite =! statusFavorite
            //val values = ContentValues()
            //values.put(DatabaseContract.UserColumns._ID, perusahaan)
            //values.put(DatabaseContract.UserColumns.PHOTO, avatar)
            //values.put(DatabaseContract.UserColumns.USERNAME, usernameku)
            //userHelper.insert(values)
            setStatusfavorite(statusFavorite)
        }
    }
    private fun setStatusfavorite(statusFavorite: Boolean){
        if(statusFavorite){
            binding.btnFavorite.setBackgroundResource(R.drawable.ic_favorite_red)
           // val values = ContentValues()
          //  values.put(DatabaseContract.UserColumns.USERNAME, userItems.username)
          //  values.put(DatabaseContract.UserColumns.PHOTO, userItems.avatar)

            //contentResolver.insert(CONTENT_URI, values)

        }
        else{
            binding.btnFavorite.setBackgroundResource(R.drawable.ic_favorite_grey)

        }
    }

}



