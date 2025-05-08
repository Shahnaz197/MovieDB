package com.example.moviedb

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moviedb.data.MovieApiResponse
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchMoviesScreen(
    movieViewModel: MovieViewModel,
    onNavigateBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var movieResult by remember { mutableStateOf<MovieApiResponse?>(null) }

    LaunchedEffect(Unit) {
        movieViewModel.searchMovieResult.collectLatest { result ->
            movieResult = result
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        movieViewModel.operationStatus.collectLatest { status ->
            if (status.isNotEmpty()) {
                message = status
                isLoading = false
                movieViewModel.resetOperationStatus()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5F))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Search for Movies",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Movie Title") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    cursorColor = Color.White,
                    focusedContainerColor = Transparent,
                    unfocusedContainerColor = Transparent,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        if (searchQuery.isNotEmpty()) {
                            isLoading = true
                            message = ""
                            movieViewModel.searchMovieFromApi(searchQuery)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = searchQuery.isNotEmpty() && !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE50914)
                    )
                ) {
                    Text("Retrieve Movie", color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        isLoading = true
                        message = ""
                        movieViewModel.saveMovieToDatabase()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = movieResult != null && !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE50914)
                    )
                ) {
                    Text("Save Movie to Database", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            }

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = Color.Green, // You can change to Color.Red for error messages
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (movieResult != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Title: ${movieResult!!.Title}", color = Color.White)
                        Text("Year: ${movieResult!!.Year}", color = Color.White)
                        Text("Rated: ${movieResult!!.Rated}", color = Color.White)
                        Text("Released: ${movieResult!!.Released}", color = Color.White)
                        Text("Runtime: ${movieResult!!.Runtime}", color = Color.White)
                        Text("Genre: ${movieResult!!.Genre}", color = Color.White)
                        Text("Director: ${movieResult!!.Director}", color = Color.White)
                        Text("Writer: ${movieResult!!.Writer}", color = Color.White)
                        Text("Actors: ${movieResult!!.Actors}", color = Color.White)
                        Text("Plot: ${movieResult!!.Plot}", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    movieViewModel.resetSearchResult()
                    onNavigateBack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE50914)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back", color = Color.White)
            }
        }
    }
}
