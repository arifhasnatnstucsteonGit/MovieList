package com.arifhasnat.movielist.repositories.database
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_entity")
    fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movie_entity WHERE title LIKE :movieID")
    fun findByMovieID(movieID: String): MovieEntity

    @Query("SELECT * FROM movie_entity WHERE title LIKE :title")
    fun findByTitle(title: String): MovieEntity

    @Query("SELECT * FROM movie_entity WHERE title LIKE :title")
    fun findByTitles(title: String): LiveData<List<MovieEntity>>

    @Insert
    fun insertAll(vararg todo: MovieEntity)

    @Delete
    fun delete(todo: MovieEntity)

    @Update
    fun updateTodo(vararg todos: MovieEntity)
}