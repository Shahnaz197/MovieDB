package com.example.moviedb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDatabase = MovieDatabase.getDatabase(application)
    private val movieDao = movieDatabase.movieDao()
    private val repository = MovieRepository(movieDao)

    // Search for movie
    private val _searchMovieResult = MutableStateFlow<MovieApiResponse?>(null)
    val searchMovieResult: StateFlow<MovieApiResponse?> = _searchMovieResult

    // Search by actor name
    private val _actorSearchResults = MutableStateFlow<List<Movie>>(emptyList())
    val actorSearchResults: StateFlow<List<Movie>> = _actorSearchResults

    //search through api
    private val _apiSearchResults = MutableStateFlow<List<SearchItem>>(emptyList())
    val apiSearchResults: StateFlow<List<SearchItem>> = _apiSearchResults

    // state of database
    private val _operationStatus = MutableStateFlow<String>("")
    val operationStatus: StateFlow<String> = _operationStatus

    // predefined movie to the dataase
    fun addPredefinedMovies() {
        viewModelScope.launch {
            try {
                repository.addPredefinedMovies()
                _operationStatus.value = "Movies added successfully"
            } catch (e: Exception) {
                _operationStatus.value = "Error adding movies: ${e.message}"
            }
        }
    }

    // API search for movie by its title
    fun searchMovieFromApi(title: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchMovieFromApi(title)
                _searchMovieResult.value = result
            } catch (e: Exception) {
                _operationStatus.value = "Error searching movie: ${e.message}"
            }
        }
    }

    // Save movie to the database
    fun saveMovieToDatabase() {
        viewModelScope.launch {
            try {
                val movie = _searchMovieResult.value
                if (movie != null) {
                    repository.insertMovie(movie.toMovie())
                    _operationStatus.value = "Movie saved successfully"
                } else {
                    _operationStatus.value = "No movie to save"
                }
            } catch (e: Exception) {
                _operationStatus.value = "Error saving movie: ${e.message}"
            }
        }
    }

    // Search movies by actor
    fun searchMoviesByActor(actorName: String) {
        viewModelScope.launch {
            try {
                val results = repository.searchMoviesByActor(actorName)
                _actorSearchResults.value = results
            } catch (e: Exception) {
                _operationStatus.value = "Error searching by actor: ${e.message}"
            }
        }
    }

    // Search movies from API by title
    fun searchMoviesFromApi(searchQuery: String) {
        viewModelScope.launch {
            try {
                val results = repository.searchMoviesFromApi(searchQuery)
                _apiSearchResults.value = results
            } catch (e: Exception) {
                _operationStatus.value = "Error searching movies from API: ${e.message}"
            }
        }
    }

    // Reset operation status
    fun resetOperationStatus() {
        _operationStatus.value = ""
    }

    // Reset search result
    fun resetSearchResult() {
        _searchMovieResult.value = null
    }
}