package com.example.moviedb.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val language: String = "",
    val country: String = "",
    val awards: String = "",
    val poster: String = "",
    val metascore: String = "",
    val imdbRating: String = "",
    val imdbVotes: String = "",
    val imdbID: String = "",
    val type: String = "",
    val response: String = ""
)

data class MovieApiResponse(
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val Ratings: List<Rating>?,
    val Metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val imdbID: String,
    val Type: String,
    val Response: String
)

data class Rating(
    val Source: String,
    val Value: String
)

data class SearchResult(
    val Search: List<SearchItem>?,
    val totalResults: String?,
    val Response: String
)

data class SearchItem(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
)

fun MovieApiResponse.toMovie(): Movie {
    return Movie(
        title = Title,
        year = Year,
        rated = Rated,
        released = Released,
        runtime = Runtime,
        genre = Genre,
        director = Director,
        writer = Writer,
        actors = Actors,
        plot = Plot,
        language = Language,
        country = Country,
        awards = Awards,
        poster = Poster,
        metascore = Metascore,
        imdbRating = imdbRating,
        imdbVotes = imdbVotes,
        imdbID = imdbID,
        type = Type,
        response = Response
    )
}