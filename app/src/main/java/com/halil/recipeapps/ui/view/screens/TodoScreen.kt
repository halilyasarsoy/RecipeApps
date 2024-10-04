package com.halil.recipeapps.ui.view.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.halil.recipeapps.ui.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(navController: NavHostController, userViewModel: UserViewModel) {
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding)
            ) {
                Text(text = "Todo List Screen", style = MaterialTheme.typography.headlineMedium)
            }
        }
    )
}