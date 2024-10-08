package com.halil.recipeapps.ui.view.screens

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.halil.recipeapps.R
import com.halil.recipeapps.ui.view.AuthActivity
import com.halil.recipeapps.ui.view.MainActivity
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.ui.viewmodel.UserViewModel
import com.halil.recipeapps.util.Resource
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(activity: Activity, viewModel: LoginViewModel, userViewModel: UserViewModel) {
    LaunchedEffect(true) {
        userViewModel.fetchRecipes()

        userViewModel.recipes.collect { state ->
            when (state) {
                is Resource.Success -> {
                    Log.d("SplashScreen", "Recipes successfully fetched and saved.")
                    userViewModel.getRecipesFromDb()

                    // user check login
                    if (viewModel.isUserLoggedIn()) {
                        activity.startActivity(Intent(activity, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        })
                    } else {
                        activity.startActivity(Intent(activity, AuthActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        })
                    }
                    activity.finish()
                    return@collect
                }
                is Resource.Error -> {
                    Log.e("SplashScreen", "Error fetching recipes: ${state.message}")
                }
                is Resource.Loading -> {
                }
                else -> {}
            }
        }
    }
    SplashScreenAnimation()
}

@Composable
fun SplashScreenAnimation() {
    val visibleState = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visibleState.value,
            enter = scaleIn(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
            exit = scaleOut(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_recipe),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        visibleState.value = false
    }
}

