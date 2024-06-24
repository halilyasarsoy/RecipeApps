package com.halil.recipeapps.ui.view
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.halil.recipeapps.R
import com.halil.recipeapps.ui.theme.white
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: LoginViewModel, onNavigateToLogin: () -> Unit) {
    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current
    val registerState by viewModel.registerState.collectAsState()

    LaunchedEffect(registerState) {
        when (registerState) {
            is Resource.Success -> {
                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                onNavigateToLogin()
            }
            is Resource.Error -> {
                Toast.makeText(context, (registerState as Resource.Error<*>).message ?: "Registration failed", Toast.LENGTH_SHORT).show()
            }
            // No action needed for other states
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.recipe_background_img),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.White.copy(alpha = 0.8f), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Register", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = name.value, onValueChange = { name.value = it }, label = { Text("Name") })
                OutlinedTextField(value = surname.value, onValueChange = { surname.value = it }, label = { Text("Surname") })
                OutlinedTextField(value = age.value, onValueChange = { age.value = it }, label = { Text("Age") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                OutlinedTextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") })
                OutlinedTextField(value = password.value, onValueChange = { password.value = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (email.value.contains("@") && password.value.length >= 6) {
                            viewModel.register(name.value, surname.value, age.value.toIntOrNull() ?: 0, email.value, password.value)
                        } else {
                            Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Register")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onNavigateToLogin) {
                    Text("Go to Login")
                }
            }
        }
    }
}