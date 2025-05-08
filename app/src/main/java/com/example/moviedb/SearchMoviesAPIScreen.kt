package com.example.moviedb

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moviedb.data.SearchItem
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SearchMoviesAPIScreen(
    movieViewModel: MovieViewModel,
    onNavigateBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<SearchItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        movieViewModel.apiSearchResults.collectLatest { results ->
            searchResults = results
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
        // Background image
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Overlay to make the background darker
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5F))
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Search Movies from API",
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
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (searchQuery.isNotEmpty()) {
                        isLoading = true
                        message = ""
                        movieViewModel.searchMoviesFromApi(searchQuery)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = searchQuery.isNotEmpty() && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE50914)
                )
            ) {
                Text("Search", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            }

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (searchResults.isNotEmpty()) {
                Text(
                    text = "Found ${searchResults.size} movie(s)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(searchResults) { movie ->
                        SearchResultItem(movie = movie)
                    }
                }
            } else if (!isLoading && searchQuery.isNotEmpty() && searchResults.isEmpty()) {
                Text(
                    text = "No movies found with title containing: $searchQuery",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE50914)
                )
            ) {
                Text("Back", color = Color.White)
            }
        }
    }
}

@Composable
fun SearchResultItem(movie: SearchItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = movie.Title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Year: ${movie.Year}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Type: ${movie.Type}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "IMDb ID: ${movie.imdbID}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}