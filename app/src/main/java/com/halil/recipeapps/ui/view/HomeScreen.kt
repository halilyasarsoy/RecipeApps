package com.halil.recipeapps.ui.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.halil.recipeapps.data.model.User
import com.halil.recipeapps.ui.viewmodel.UserViewModel
import com.halil.recipeapps.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, userViewModel: UserViewModel) {
    val user by userViewModel.userData.collectAsState()
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (user) {
                    is Resource.Loading -> {
                        CircularProgressIndicator()
                    }
                    is Resource.Success -> {
                        val userData = (user as Resource.Success<User>).data
                        userData?.let {
                            Text(
                                text = "Welcome, ${it.email}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    }
                    is Resource.Error -> {
                        Text(
                            text = "Error: ${(user as Resource.Error).message}",
                            color = Color.Red
                        )
                    }
                    else -> {
                        Text(text = "Welcome to Home Screen", style = MaterialTheme.typography.headlineMedium)
                    }
                }
            }
        }
    )
}