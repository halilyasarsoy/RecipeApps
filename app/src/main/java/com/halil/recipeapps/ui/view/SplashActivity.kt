package com.halil.recipeapps.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.halil.recipeapps.ui.view.screens.SplashScreen
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModel: LoginViewModel by viewModels()
        val userViewModel: UserViewModel by viewModels()


        setContent {
            SplashScreen(this, loginViewModel, userViewModel)
        }
    }
}
