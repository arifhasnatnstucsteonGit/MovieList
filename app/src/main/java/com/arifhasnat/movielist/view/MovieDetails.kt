package com.arifhasnat.movielist.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.arifhasnat.movielist.R
import com.arifhasnat.movielist.repositories.api.ApiCall
import com.arifhasnat.movielist.repositories.api.RetrofitClient
import com.arifhasnat.movielist.global.Global
import com.arifhasnat.movielist.models.movieDetails.MovieDetails
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class MovieDetails : AppCompatActivity() {

    private lateinit var title:TextView
    private lateinit var description:TextView
    private lateinit var poster:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        poster = findViewById(R.id.poster)

        title.setText(Global.title)
        description.setText(Global.description)
        Picasso.with(this@MovieDetails)
            .load(Global.POSTER_ROOT+Global.posterPath)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(poster)


        //getMovieDetails()
        /**
         * i am just showing title,description and image in details page. This informations i am getting
         * from previous api call. so i am not calling the details api
         */

    }

    fun getMovieDetails() {
        val apiReader: ApiCall? = RetrofitClient.getApiService()
        val call = apiReader?.movieDetails(getString(R.string.api_key),"")

        call?.enqueue(object : retrofit2.Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                if (response.code() == 200) {


                }
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
            }

        })

    }
}