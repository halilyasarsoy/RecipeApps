package com.halil.recipeapps.mock

import androidx.lifecycle.viewModelScope
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.util.Resource
import kotlinx.coroutines.launch

class MockLoginViewModel : LoginViewModel(MockAuthRepository()) {

    override fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = if (email == "test@example.com" && password == "password") {
                Resource.Success(null) // Mock başarılı giriş
            } else {
                Resource.Error("Invalid credentials")
            }
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return false
    }
}