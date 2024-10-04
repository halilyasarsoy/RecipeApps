package com.halil.recipeapps.ui.view.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.halil.recipeapps.R
import com.halil.recipeapps.ui.theme.white
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LoginScreen(viewModel: LoginViewModel, onNavigateToRegister: () -> Unit,onNavigateToHome: () -> Unit) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.recipe_background_img),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(white.copy(alpha = 0.8f), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Login", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Email") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Password") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (email.value.contains("@") && password.value.length >= 6) {
                            viewModel.login(email.value, password.value)
                        } else {
                            Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Login")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onNavigateToRegister) {
                    Text("Go to Register")
                }
            }
        }
    }
    when (loginState) {
        is Resource.Loading -> {
            // progress
        }
        is Resource.Success -> {
            Toast.makeText(context, stringResource(R.string.login_success), Toast.LENGTH_SHORT).show()
            onNavigateToHome()
        }
        is Resource.Error -> {
            Toast.makeText(context, (loginState as Resource.Error).message ?: "Registration failed", Toast.LENGTH_SHORT).show()
        }
        null -> {}
    }
}

