package com.halil.recipeapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.halil.recipeapps.ui.component.BottomNavigationBar
import com.halil.recipeapps.ui.component.NavigationDrawerContent
import com.halil.recipeapps.ui.view.HomeScreen
import com.halil.recipeapps.ui.view.RecipesScreen
import com.halil.recipeapps.ui.view.SplashScreen
import com.halil.recipeapps.ui.view.TodoListScreen
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewModel: UserViewModel by viewModels()
        val loginViewModel: LoginViewModel by viewModels()

        userViewModel.fetchUserData(FirebaseAuth.getInstance().currentUser?.uid ?: "")

        setContent {

            MainNavigation()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        val userViewModel: UserViewModel = viewModel()
        val loginViewModel: LoginViewModel = viewModel()

        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()  // Coroutine scope oluşturuluyor.

        ModalNavigationDrawer(
            drawerContent = {
                // UserViewModel ve LoginViewModel NavigationDrawerContent'e geçiriliyor.
                NavigationDrawerContent(
                    navController = navController,
                    userViewModel = userViewModel,
                    viewModel = loginViewModel
                )
            },
            drawerState = drawerState,
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("App Title") },
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    drawerState.open()  // Coroutine içinde suspend fonksiyonu çağrılıyor.
                                }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomNavigationBar(navController = navController)
                }
            ) { innerPadding ->
                NavHost(navController, startDestination = "home", Modifier.padding(innerPadding)) {
                    composable("home") {
                        HomeScreen(navController = navController, userViewModel = userViewModel)
                    }
                    composable("recipes") {
                        RecipesScreen()
                    }
                    composable("todolist") {
                        TodoListScreen(navController = navController, userViewModel = userViewModel)
                    }
                }
            }
        }
    }
}
