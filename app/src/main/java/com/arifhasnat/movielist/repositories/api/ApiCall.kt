package com.arifhasnat.movielist.repositories.api

import com.arifhasnat.movielist.models.movieDetails.MovieDetails
import com.arifhasnat.movielist.models.movieList.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiCall {

    companion object{

    }

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("3/movie/popular")
    fun movieList(
            @Query("api_key") query: String?,
            @Query("page") pageNo: String?
    ): Call<MovieList>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("/3/movie/{id}")
    fun movieDetails(
            @Query("api_key") query: String?,
            @Path("id") id: String?
    ): Call<MovieDetails>

}