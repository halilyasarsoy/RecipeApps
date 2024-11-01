package com.halil.recipeapps.ui.view.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halil.recipeapps.R
import com.halil.recipeapps.mock.MockLoginViewModel
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.util.Resource

@Composable
fun RegisterScreen(viewModel: LoginViewModel, onNavigateToLogin: () -> Unit) {
    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current
    val registerState by viewModel.registerState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel._registerState.value = null
    }

    LaunchedEffect(registerState) {
        when (registerState) {
            is Resource.Success -> {
                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                onNavigateToLogin()
            }

            is Resource.Error -> {
                Toast.makeText(
                    context,
                    (registerState as Resource.Error<*>).message ?: "Registration failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        CustomTextField(value = name.value, onValueChange = { name.value = it }, label = "Name")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = surname.value,
            onValueChange = { surname.value = it },
            label = "Surname"
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = age.value,
            onValueChange = { age.value = it },
            label = "Age",
            isPasswordField = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(value = email.value, onValueChange = { email.value = it }, label = "Email")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = "Password",
            isPasswordField = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.value.contains("@") && password.value.length >= 6) {
                    viewModel.register(
                        name.value,
                        surname.value,
                        age.value.toIntOrNull() ?: 0,
                        email.value,
                        password.value
                    )
                } else {
                    Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.button_orange),
                contentColor = Color.Black
            )
        ) {
            Text(
                "Register",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onNavigateToLogin,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorResource(id = R.color.button_orange)
            )
        ) {
            Text("Go to Login", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
        }
    }

    if (registerState is Resource.Loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.button_orange),
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    val mockLoginViewModel = MockLoginViewModel()

    RegisterScreen(
        viewModel = mockLoginViewModel,
        onNavigateToLogin = { /* Mock action for preview */ }
    )
}
