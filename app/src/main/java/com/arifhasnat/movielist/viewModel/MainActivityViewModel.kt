package com.arifhasnat.movielist.viewModel

import android.content.Context
import android.provider.Settings
import androidx.lifecycle.ViewModel
import com.arifhasnat.movielist.R
import com.arifhasnat.movielist.adapters.MoviesAdapter
import com.arifhasnat.movielist.global.Global
import com.arifhasnat.movielist.models.movieList.MovieList
import com.arifhasnat.movielist.models.movieList.Results
import com.arifhasnat.movielist.repositories.api.ApiCall
import com.arifhasnat.movielist.repositories.api.RetrofitClient
import com.arifhasnat.movielist.repositories.database.MovieEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel(){


    init {
    }

    fun getMovieList() {
        val apiReader: ApiCall? = RetrofitClient.getApiService()
        val call = apiReader?.movieList(Global.API_KEY, "1")

        call?.enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.code() == 200) {
                    //  Log.d("movies", response.body()!!.results.toString())

                    var results: ArrayList<Results> = ArrayList()
                    results = response.body()?.results!!
                    //Log.d("results", results.toString())
                    if (results.size > 0) {
                        for (result in results) {
                            var movieEntity = result.id?.let {
                                result.title?.let { it1 ->
                                    result.overview?.let { it2 ->
                                        result.poster_path?.let { it3 ->
                                            MovieEntity(0,
                                                it, it1, it2, it3
                                            )
                                        }
                                    }
                                }
                            }

//                            /** for initial showing of movies*/
//                            if (movieEntity != null) {
//                                movieList.add(movieEntity)
//                            }
//                            mAdapter = MoviesAdapter(movieList)
//                            recyclerView?.adapter = mAdapter
//                            /** for inserting data for next time use*/
//                            if (movieEntity != null) {
//                                db?.movieDao()?.insertAll(movieEntity)
//                            }

                        }


                    }


                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
            }

        })

    }
}

