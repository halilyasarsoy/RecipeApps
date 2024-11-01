package com.halil.recipeapps.ui.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halil.recipeapps.R
import com.halil.recipeapps.mock.MockLoginViewModel
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.util.Resource

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToHome: () -> Unit
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()
    val hasNavigated = remember { mutableStateOf(false) }

    LaunchedEffect(loginState) {
        if (loginState is Resource.Success && loginState.data != null && !hasNavigated.value) {
            hasNavigated.value = true
            onNavigateToHome()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CustomTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = "Email"
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = "Password",
            isPasswordField = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        ForgotPasswordRow(viewModel = viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.value.contains("@") && password.value.length >= 6) {
                    viewModel.login(email.value, password.value)
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
                text = "Log in",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun TabItem(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) colorResource(id = R.color.black) else colorResource(id = R.color.button_orange)
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (isSelected) {
            Box(
                modifier = Modifier
                    .height(3.dp)
                    .width(40.dp)
                    .background(
                        color = if (isSelected) colorResource(id = R.color.black) else colorResource(
                            id = R.color.button_orange
                        )
                    )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordRow(viewModel: LoginViewModel) {
    val context = LocalContext.current
    val resetPasswordState by viewModel.resetPasswordState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val emailInput =
        remember { mutableStateOf("") }

    LaunchedEffect(resetPasswordState) {
        when (resetPasswordState) {
            is Resource.Success -> {
                Toast.makeText(context, "Reset email sent successfully", Toast.LENGTH_SHORT).show()
                showDialog.value = false
            }

            is Resource.Error -> {
                Toast.makeText(
                    context,
                    (resetPasswordState as Resource.Error).message ?: "Error sending reset email",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp)
            .clickable {
                showDialog.value = true
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Forgot your password?",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Forgot password arrow",
            tint = Color.Gray
        )
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Reset Password") },
            text = {
                Column {
                    Text("Please enter your email to reset password:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = emailInput.value,
                        onValueChange = { emailInput.value = it },
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetPassword(emailInput.value)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.button_orange),
                        contentColor = Color.Black
                    )
                ) {
                    Text("Send", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog.value = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = colorResource(id = R.color.button_orange)
                    )
                ) {
                    Text("Cancel", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPasswordField: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPasswordField && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPasswordField) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color(0xFFFFA500).copy(alpha = 0.1f),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = colorResource(id = R.color.button_orange_dark).copy(alpha = 0.8f),
            unfocusedLabelColor = colorResource(id = R.color.button_orange_dark).copy(alpha = 0.8f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    )
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val mockLoginViewModel = MockLoginViewModel()

    AuthScreen(
        viewModel = mockLoginViewModel,
        onNavigateToHome = { /* Mock action */ }
    )
}