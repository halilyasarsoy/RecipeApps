package com.halil.recipeapps.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.halil.recipeapps.ui.viewmodel.LoginViewModel

@Composable
fun LogoutListener(viewModel: LoginViewModel, navController: NavController) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }  // Home'dan itibaren tüm geçmişi temizle
            }
        }
    }
}
