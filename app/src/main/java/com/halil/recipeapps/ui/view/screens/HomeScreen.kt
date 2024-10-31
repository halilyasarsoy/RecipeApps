package com.halil.recipeapps.ui.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.halil.recipeapps.R
import com.halil.recipeapps.data.model.Recipe
import com.halil.recipeapps.mock.MockUserViewModel
import com.halil.recipeapps.ui.component.RecipeList
import com.halil.recipeapps.ui.viewmodel.UserViewModel
import com.halil.recipeapps.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
) {
    val recipeState by userViewModel.recipes.collectAsState()
    val userName = "Jenny"

    LaunchedEffect(Unit) {
        userViewModel.fetchRecipes()
    }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFA726))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    if (drawerState.isOpen) drawerState.close() else drawerState.open()
                                }
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.avatar),
                                    contentDescription = "User Avatar",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .border(1.dp, Color.White, CircleShape)
                                )
                            }

                            Text(
                                text = "Hello, $userName!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Find amazing delicious recipes!",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            placeholder = { Text("Search any recipe") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search icon"
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.White,
                            )
                        )
                    }
                }

                when (recipeState) {
                    is Resource.Loading -> {
                        CircularProgressIndicator()
                    }
                    is Resource.Success -> {
                        val recipes = (recipeState as Resource.Success<List<Recipe>>).data
                        if (!recipes.isNullOrEmpty()) {
                            RecipeList(
                                recipes = recipes,
                                userViewModel = userViewModel,
                                onClick = { recipe ->
                                    navController.navigate("recipeDetail/${recipe.id}")
                                }
                            )
                        } else {
                            Text(text = "No recipes found.")
                        }
                    }
                    is Resource.Error -> {
                        Text(
                            text = "Error: ${(recipeState as Resource.Error).message}",
                            color = Color.Red
                        )
                    }
                    else -> {}
                }
            }
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    val mockUserViewModel = MockUserViewModel()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    HomeScreen(
        navController = navController,
        userViewModel = mockUserViewModel,
        drawerState = drawerState,
        coroutineScope = coroutineScope
    )
}
