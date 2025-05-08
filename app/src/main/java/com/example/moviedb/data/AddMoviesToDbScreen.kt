package com.example.moviedb

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddMoviesToDbScreen(
    movieViewModel: MovieViewModel,
    onNavigateBack: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        movieViewModel.operationStatus.collectLatest { status ->
            if (status.isNotEmpty()) {
                message = status
                isLoading = false
                movieViewModel.resetOperationStatus()
            }
        }
    }
    //box layout for buttons
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
        Text(
            text = "Add Predefined Movies to Database",
            style = MaterialTheme.typography.headlineSmall,
            color =White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isLoading = true
                message = ""
                movieViewModel.addPredefinedMovies()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = !isLoading,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE50914))
        ) {
            Text("Add Movies",color= White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(
                color = Color(0xFFE50914))
   }

        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = White,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateBack,colors =ButtonDefaults.buttonColors(containerColor=Color(0xFFE50914)
),          modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Back", color = White)
        }
    }
}