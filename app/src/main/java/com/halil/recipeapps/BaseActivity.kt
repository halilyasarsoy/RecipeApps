package com.halil.recipeapps

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

abstract class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen { isLoggedIn ->
                if (isLoggedIn) {
                    navigateToMain()
                } else {
                    navigateToAuth()
                }
            }
        }
    }

    @Composable
    fun SplashScreen(onNavigate: (Boolean) -> Unit) {
        val isLoggedIn = remember { mutableStateOf<Boolean?>(null) }

        LaunchedEffect(true) {
            delay(3000) // 3 saniyelik splash süresi
            isLoggedIn.value = FirebaseAuth.getInstance().currentUser != null
        }

        if (isLoggedIn.value != null) {
            onNavigate(isLoggedIn.value == true)
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Splash Screen", style = MaterialTheme.typography.bodySmall)
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToAuth() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}