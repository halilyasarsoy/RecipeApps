package com.halil.recipeapps.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.halil.recipeapps.data.model.User
import com.halil.recipeapps.data.repository.AuthRepository
import com.halil.recipeapps.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<FirebaseAuth>?>(null)
    val loginState: StateFlow<Resource<FirebaseAuth>?> = _loginState

    private val _registerState = MutableStateFlow<Resource<FirebaseAuth>?>(null)
    val registerState: StateFlow<Resource<FirebaseAuth>?> = _registerState

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading()
            val result = repository.login(email, password)
            _loginState.value = result
            if (result is Resource.Success) {
                _isLoggedIn.value = true
            }
        }
    }

    fun register(name: String, surname: String, age: Int, email: String, password: String) {
        val user = User(name, surname, age, email) // Assuming User class exists with these parameters
        viewModelScope.launch {
            _registerState.value = Resource.Loading()
            val result = repository.register(email, password, user)
            _registerState.value = result
            if (result is Resource.Success) {
                _isLoggedIn.value = true
            }
        }
    }

    private val _logoutStatus = MutableStateFlow(false)
    val logoutStatus: StateFlow<Boolean> = _logoutStatus

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _logoutStatus.value = true  // Çıkış yapıldığını bildir
        }
    }
}