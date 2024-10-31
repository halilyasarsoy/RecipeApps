package com.halil.recipeapps.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.halil.recipeapps.ui.component.BottomNavigationBar
import com.halil.recipeapps.ui.component.NavigationDrawerContent
import com.halil.recipeapps.ui.view.screens.HomeScreen
import com.halil.recipeapps.ui.view.screens.RecipeDetailScreen
import com.halil.recipeapps.ui.view.screens.RecipesScreen
import com.halil.recipeapps.ui.view.screens.TodoListScreen
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewModel: UserViewModel by viewModels()

        userViewModel.fetchUserData(FirebaseAuth.getInstance().currentUser?.uid ?: "")

        setContent {

            MainNavigation()

        }
    }

    @SuppressLint("UseOfNonLambdaOffsetOverload")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        val userViewModel: UserViewModel = viewModel()
        val loginViewModel: LoginViewModel = viewModel()

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()

        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val drawerWidth = screenWidth * 0.8f

        val screenOffset by animateDpAsState(
            targetValue = if (drawerState.targetValue == DrawerValue.Open) drawerWidth else 0.dp,
            animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing), label = ""
        )

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(drawerWidth)
                        .background(Color(0xFF121212))
                ) {
                    NavigationDrawerContent(
                        viewModel = loginViewModel,
                        userViewModel = userViewModel,
                        navController = navController,
                        drawerState = drawerState,
                        onCloseDrawer = { coroutineScope.launch { drawerState.close() } }
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = screenOffset)
            ) {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = "home",
                        Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(
                                navController = navController,
                                userViewModel = userViewModel,
                                drawerState = drawerState,
                                coroutineScope = coroutineScope
                            )
                        }
                        composable("recipes") {
                            RecipesScreen(userViewModel = userViewModel)
                        }
                        composable("todolist") {
                            TodoListScreen(
                                navController = navController,
                                userViewModel = userViewModel
                            )
                        }
                        composable("recipeDetail/{recipeId}") { backStackEntry ->
                            val recipeId =
                                backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
                            val recipe =
                                userViewModel.recipes.value?.data?.find { it.id == recipeId }
                            RecipeDetailScreen(recipe = recipe)
                        }
                    }
                }
            }
        }
    }


}