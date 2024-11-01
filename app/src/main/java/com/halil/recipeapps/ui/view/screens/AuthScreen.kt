package com.halil.recipeapps.ui.view.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.halil.recipeapps.ui.viewmodel.LoginViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(
    viewModel: LoginViewModel,
    onNavigateToHome: () -> Unit,
    initialSelectedTab: Int = 0
) {
    val selectedTab = remember { mutableStateOf(initialSelectedTab) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            TabItem(
                title = "Log in",
                isSelected = selectedTab.value == 0,
                onClick = { selectedTab.value = 0 }
            )
            Spacer(modifier = Modifier.width(16.dp))
            TabItem(
                title = "Sign up",
                isSelected = selectedTab.value == 1,
                onClick = { selectedTab.value = 1 }
            )
        }

        Divider(
            color = Color.Gray.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(
            targetState = selectedTab.value,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInHorizontally(initialOffsetX = { it }) + fadeIn()).togetherWith(
                        slideOutHorizontally(
                            targetOffsetX = { -it }) + fadeOut()
                    )
                } else {
                    (slideInHorizontally(initialOffsetX = { -it }) + fadeIn()).togetherWith(
                        slideOutHorizontally(
                            targetOffsetX = { it }) + fadeOut()
                    )
                }
            }, label = ""
        ) { currentTab ->
            if (currentTab == 0) {
                LoginScreen(
                    viewModel = viewModel,
                    onNavigateToHome = onNavigateToHome
                )
            } else {
                RegisterScreen(
                    viewModel = viewModel,
                    onNavigateToLogin = { selectedTab.value = 0 }
                )
            }
        }
    }
}
