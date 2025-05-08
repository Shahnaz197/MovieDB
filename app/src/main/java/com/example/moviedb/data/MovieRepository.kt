package com.example.moviedb.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MovieRepository(private val movieDao: MovieDao) {


    private val apiKey = "b685dbc5"

    suspend fun addPredefinedMovies() {
        val predefinedMovies = getPredefinedMovies()
        movieDao.insertMovies(predefinedMovies)
    }

    // Get a list of predefined movies
    private fun getPredefinedMovies(): List<Movie> {
        return listOf(
            Movie(
                title = "The Shawshank Redemption",
                year = "1994",
                rated = "R",
                released = "14 Oct 1994",
                runtime = "142 min",
                genre = "Drama",
                director = "Frank Darabont",
                writer = "Stephen King, Frank Darabont",
                actors = "Tim Robbins, Morgan Freeman, Bob Gunton",
                plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."
            ),
            Movie(
                title = "The Godfather",
                year = "1972",
                rated = "R",
                released = "24 Mar 1972",
                runtime = "175 min",
                genre = "Crime, Drama",
                director = "Francis Ford Coppola",
                writer = "Mario Puzo, Francis Ford Coppola",
                actors = "Marlon Brando, Al Pacino, James Caan",
                plot = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."
            ),
            Movie(
                title = "The Dark Knight",
                year = "2008",
                rated = "PG-13",
                released = "18 Jul 2008",
                runtime = "152 min",
                genre = "Action, Crime, Drama",
                director = "Christopher Nolan",
                writer = "Jonathan Nolan, Christopher Nolan",
                actors = "Christian Bale, Heath Ledger, Aaron Eckhart",
                plot = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice."
            ),
            Movie(
                title = "Pulp Fiction",
                year = "1994",
                rated = "R",
                released = "14 Oct 1994",
                runtime = "154 min",
                genre = "Crime, Drama",
                director = "Quentin Tarantino",
                writer = "Quentin Tarantino, Roger Avary",
                actors = "John Travolta, Uma Thurman, Samuel L. Jackson",
                plot = "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption."
            ),
            Movie(
                title = "Inception",
                year = "2010",
                rated = "PG-13",
                released = "16 Jul 2010",
                runtime = "148 min",
                genre = "Action, Adventure, Sci-Fi",
                director = "Christopher Nolan",
                writer = "Christopher Nolan",
                actors = "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page",
                plot = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O."
            ),
            Movie(
                title = "The Matrix",
                year = "1999",
                rated = "R",
                released = "31 Mar 1999",
                runtime = "136 min",
                genre = "Action, Sci-Fi",
                director = "Lana Wachowski, Lilly Wachowski",
                writer = "Lana Wachowski, Lilly Wachowski",
                actors = "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
                plot = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
            ),
            Movie(
                title = "Forrest Gump",
                year = "1994",
                rated = "PG-13",
                released = "06 Jul 1994",
                runtime = "142 min",
                genre = "Drama, Romance",
                director = "Robert Zemeckis",
                writer = "Winston Groom, Eric Roth",
                actors = "Tom Hanks, Robin Wright, Gary Sinise",
                plot = "The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other historical events unfold through the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart."
            ),
            Movie(
                title = "Fight Club",
                year = "1999",
                rated = "R",
                released = "15 Oct 1999",
                runtime = "139 min",
                genre = "Drama",
                director = "David Fincher",
                writer = "Chuck Palahniuk, Jim Uhls",
                actors = "Brad Pitt, Edward Norton, Meat Loaf",
                plot = "An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more."
            ),
            Movie(
                title = "The Lord of the Rings: The Fellowship of the Ring",
                year = "2001",
                rated = "PG-13",
                released = "19 Dec 2001",
                runtime = "178 min",
                genre = "Action, Adventure, Drama",
                director = "Peter Jackson",
                writer = "J.R.R. Tolkien, Fran Walsh, Philippa Boyens",
                actors = "Elijah Wood, Ian McKellen, Orlando Bloom",
                plot = "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron."
            ),
            Movie(
                title = "Star Wars: Episode V - The Empire Strikes Back",
                year = "1980",
                rated = "PG",
                released = "20 Jun 1980",
                runtime = "124 min",
                genre = "Action, Adventure, Fantasy",
                director = "Irvin Kershner",
                writer = "Leigh Brackett, Lawrence Kasdan, George Lucas",
                actors = "Mark Hamill, Harrison Ford, Carrie Fisher",
                plot = "After the Rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda, while his friends are pursued by Darth Vader and a bounty hunter named Boba Fett."
            )
        )
    }

    // Search movies by title from the database
    suspend fun searchMoviesByTitle(title: String): List<Movie> {
        return movieDao.searchMoviesByTitle(title)
    }

    // Search movies by actor name from the database
    suspend fun searchMoviesByActor(actorName: String): List<Movie> {
        return movieDao.searchMoviesByActor(actorName)
    }

    // Insert a movie into the database
    suspend fun insertMovie(movie: Movie): Long {
        return movieDao.insertMovie(movie)
    }

    // Get all movies from the database
    suspend fun getAllMovies(): List<Movie> {
        return movieDao.getAllMovies()
    }

    // Get movie count from the database
    suspend fun getMovieCount(): Int {
        return movieDao.getMovieCount()
    }

    // Search a movie by title from the OMDb API
    suspend fun searchMovieFromApi(title: String): MovieApiResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val urlString = "https://www.omdbapi.com/?t=$title&apikey=$apiKey"
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()

                    val jsonResponse = response.toString()
                    val jsonObject = JSONObject(jsonResponse)

                    if (jsonObject.optString("Response") == "True") {
                        return@withContext parseMovieResponse(jsonObject)
                    }
                }

                null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    // Search movies by title from the OMDb API
    suspend fun searchMoviesFromApi(searchQuery: String): List<SearchItem> {
        return withContext(Dispatchers.IO) {
            try {
                val urlString = "https://www.omdbapi.com/?s=$searchQuery&apikey=$apiKey"
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()

                    val jsonResponse = response.toString()
                    val jsonObject = JSONObject(jsonResponse)

                    if (jsonObject.optString("Response") == "True") {
                        val searchResults = mutableListOf<SearchItem>()
                        val searchArray = jsonObject.getJSONArray("Search")

                        for (i in 0 until searchArray.length()) {
                            val itemObject = searchArray.getJSONObject(i)
                            val searchItem = SearchItem(
                                Title = itemObject.optString("Title", ""),
                                Year = itemObject.optString("Year", ""),
                                imdbID = itemObject.optString("imdbID", ""),
                                Type = itemObject.optString("Type", ""),
                                Poster = itemObject.optString("Poster", "")
                            )
                            searchResults.add(searchItem)
                        }

                        return@withContext searchResults
                    }
                }

                emptyList<SearchItem>()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList<SearchItem>()
            }
        }
    }

    // Parse JSON response to MovieApiResponse
    private fun parseMovieResponse(jsonObject: JSONObject): MovieApiResponse {
        val ratings = mutableListOf<Rating>()
        val ratingsArray = jsonObject.optJSONArray("Ratings")

        if (ratingsArray != null) {
            for (i in 0 until ratingsArray.length()) {
                val ratingObject = ratingsArray.getJSONObject(i)
                val rating = Rating(
                    Source = ratingObject.optString("Source", ""),
                    Value = ratingObject.optString("Value", "")
                )
                ratings.add(rating)
            }
        }

        return MovieApiResponse(
            Title = jsonObject.optString("Title", ""),
            Year = jsonObject.optString("Year", ""),
            Rated = jsonObject.optString("Rated", ""),
            Released = jsonObject.optString("Released", ""),
            Runtime = jsonObject.optString("Runtime", ""),
            Genre = jsonObject.optString("Genre", ""),
            Director = jsonObject.optString("Director", ""),
            Writer = jsonObject.optString("Writer", ""),
            Actors = jsonObject.optString("Actors", ""),
            Plot = jsonObject.optString("Plot", ""),
            Language = jsonObject.optString("Language", ""),
            Country = jsonObject.optString("Country", ""),
            Awards = jsonObject.optString("Awards", ""),
            Poster = jsonObject.optString("Poster", ""),
            Ratings = ratings,
            Metascore = jsonObject.optString("Metascore", ""),
            imdbRating = jsonObject.optString("imdbRating", ""),
            imdbVotes = jsonObject.optString("imdbVotes", ""),
            imdbID = jsonObject.optString("imdbID", ""),
            Type = jsonObject.optString("Type", ""),
            Response = jsonObject.optString("Response", "")
        )
    }
}