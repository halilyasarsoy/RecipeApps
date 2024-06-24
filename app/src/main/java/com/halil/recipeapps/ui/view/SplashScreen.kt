package com.halil.recipeapps.ui.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.halil.recipeapps.AuthActivity
import com.halil.recipeapps.MainActivity
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(context: Context, viewModel: LoginViewModel) {
    val user by viewModel.user.collectAsState()

    LaunchedEffect(Unit) {
        delay(4000)  // 4 saniye bekleyin
        if (user != null) {
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as Activity).finish()
        } else {
            // AuthActivity'e yönlendir
            context.startActivity(Intent(context, AuthActivity::class.java))
            (context as Activity).finish()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Splash Screen", style = MaterialTheme.typography.bodyLarge)
    }
}