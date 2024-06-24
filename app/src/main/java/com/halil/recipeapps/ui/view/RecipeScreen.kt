package com.halil.recipeapps.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.halil.recipeapps.ui.component.BottomNavigationBar
import com.halil.recipeapps.ui.component.NavigationDrawerContent
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.ui.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(navController: NavHostController, userViewModel: UserViewModel) {
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding)
            ) {
                Text(text = "Recipes Screen", style = MaterialTheme.typography.headlineMedium)
                // Yemek tarifleri ile ilgili içerik buraya eklenecek
            }
        }
    )
}