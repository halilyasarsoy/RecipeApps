package com.halil.recipeapps.ui.component

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.halil.recipeapps.AuthActivity
import com.halil.recipeapps.data.model.User
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.ui.viewmodel.UserViewModel
import com.halil.recipeapps.util.Resource

@Composable
fun NavigationDrawerContent(viewModel: LoginViewModel, userViewModel: UserViewModel, navController: NavHostController) {
    val userData by userViewModel.userData.collectAsState()
    val showLogoutDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showLogoutDialog.value) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog.value = false },
            title = { Text("Logout") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.logout()
                    showLogoutDialog.value = false
                    // Doğrudan AuthActivity'ye yönlendir
                    context.startActivity(Intent(context, AuthActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                }) {
                    Text("Logout")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Geri tuşunu devre dışı bırak
    BackHandler(enabled = true) {}

    // Kullanıcı bilgilerini göster
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            when (userData) {
                is Resource.Loading -> CircularProgressIndicator()
                is Resource.Success -> {
                    (userData as Resource.Success<User>).data?.let { user ->
                        Text("Name: ${user.name}", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Surname: ${user.surname}", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Age: ${user.age}", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Email: ${user.email}", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                is Resource.Error -> Text("Error: ${(userData as Resource.Error<User>).message}", color = Color.Red)
                null -> Text("No user data available")
            }
            Divider(color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showLogoutDialog.value = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray, contentColor = Color.White)
            ) {
                Text("Logout")
            }
        }
    }
}