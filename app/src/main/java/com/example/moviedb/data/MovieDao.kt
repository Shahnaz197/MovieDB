package com.example.moviedb.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
//interface for data access objects
@Dao
interface MovieDao {
    //insert a single movie to the database and it already exist with same primary key it will replaced
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie): Long
 //insert list of movies to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)
//Searches for movies whose title contain the given text
    @Query("SELECT * FROM movies WHERE title LIKE '%' || :title || '%'")
    suspend fun searchMoviesByTitle(title: String): List<Movie>
//search movies with actors name
    @Query("SELECT * FROM movies WHERE LOWER(actors) LIKE '%' || LOWER(:actorName) || '%'")
    suspend fun searchMoviesByActor(actorName: String): List<Movie>
//retrieve all movies from database
    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<Movie>
//return total number of movies in database
    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int
}