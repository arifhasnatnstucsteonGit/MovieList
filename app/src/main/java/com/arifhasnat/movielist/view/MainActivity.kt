package com.arifhasnat.movielist.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.arifhasnat.movielist.R
import com.arifhasnat.movielist.adapters.MoviesAdapter
import com.arifhasnat.movielist.models.movieList.MovieList
import com.arifhasnat.movielist.models.movieList.Results
import com.arifhasnat.movielist.repositories.api.ApiCall
import com.arifhasnat.movielist.repositories.api.RetrofitClient
import com.arifhasnat.movielist.repositories.database.AppDatabase
import com.arifhasnat.movielist.repositories.database.MovieEntity
import com.arifhasnat.movielist.viewModel.MainActivityViewModel
import retrofit2.Call
import retrofit2.Response


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    var mainActivityViewModel: MainActivityViewModel? = null
    private var recyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    var db: AppDatabase? = null

    //var movieList: List<MovieEntity> = ArrayList()
    var movieList = mutableListOf<MovieEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Movies"
        mainActivityViewModel = ViewModelProviders.of(this).get(
            MainActivityViewModel::class.java
        )
        uiBinds()

        movieList.toMutableList().clear()
        movieList = db?.movieDao()!!.getAll().toMutableList()
        if (movieList.size > 0) {
            getAllData()
        } else {
            getMovieList()
        }
    }

    fun uiBinds() {
        recyclerView = findViewById<View>(R.id.movie_recycler) as RecyclerView
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)

        db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "myDatabase"
        ).allowMainThreadQueries().build()

    }

    private fun getAllData() {

        // userList.clear()

        Log.d("movielistdata", movieList.size.toString())
        if (movieList.size > 0) {
            mAdapter = MoviesAdapter(movieList)
            recyclerView?.adapter = mAdapter
        } else {
            Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show()
        }
    }

    fun getMovieList() {
        val apiReader: ApiCall? = RetrofitClient.getApiService()
        val call = apiReader?.movieList(getString(R.string.api_key), "1")

        call?.enqueue(object : retrofit2.Callback<MovieList> {
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

                            /** for initial showing of movies*/
                            if (movieEntity != null) {
                                movieList.add(movieEntity)
                            }
                            mAdapter = MoviesAdapter(movieList)
                            recyclerView?.adapter = mAdapter
                            /** for inserting data for next time use*/
                            if (movieEntity != null) {
                                db?.movieDao()?.insertAll(movieEntity)
                            }

                        }


                    }


                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
            }

        })

    }

}