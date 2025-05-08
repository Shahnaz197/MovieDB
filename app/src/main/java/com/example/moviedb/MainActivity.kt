//https://youtu.be/vYfhhzozha4 link to demo video

package com.example.moviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieApp()
                }
            }
        }
    }
}

@Composable
fun MovieApp() {
    val navController = rememberNavController()
    val movieViewModel: MovieViewModel = viewModel()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                onAddMoviesToDb = { navController.navigate("add_movies") },
                onSearchMovies = { navController.navigate("search_movies") },
                onSearchActors = { navController.navigate("search_actors") },
                onSearchMoviesFromAPI = { navController.navigate("search_movies_api") }
            )
        }
        composable("add_movies") {
            AddMoviesToDbScreen(
                movieViewModel = movieViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("search_movies") {
            SearchMoviesScreen(
                movieViewModel = movieViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("search_actors") {
            SearchActorsScreen(
                movieViewModel = movieViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("search_movies_api") {
            SearchMoviesAPIScreen(
                movieViewModel = movieViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun MainScreen(
    onAddMoviesToDb: () -> Unit,
    onSearchMovies: () -> Unit,
    onSearchActors: () -> Unit,
    onSearchMoviesFromAPI: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter= painterResource(id = R.drawable.bg),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier =Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5F))
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onAddMoviesToDb,
            colors =ButtonDefaults.buttonColors(
                containerColor =Color(0xFFE50914)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Add Movies to DB",
                color = White
            )
        }

        Button(
            onClick = onSearchMovies,
            colors =ButtonDefaults.buttonColors(
                containerColor =Color(0xFFE50914)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Search for Movies",
                color = White
            )
        }

        Button(
            onClick = onSearchActors,
            colors =ButtonDefaults.buttonColors(
                containerColor =Color(0xFFE50914)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Search for Actors",
                color =White
            )
        }

        Button(
            onClick = onSearchMoviesFromAPI,
            colors =ButtonDefaults.buttonColors(
                containerColor =Color(0xFFE50914)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Search Movies from API",
                color =White
            )
        }
    }
}

@Composable
fun MovieAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        content = content
    )
}