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
open class LoginViewModel @Inject constructor(
    open val repository: AuthRepository
) : ViewModel() {

    val _loginState = MutableStateFlow<Resource<FirebaseAuth?>>(Resource.Success(null))
    open val loginState: StateFlow<Resource<FirebaseAuth?>> = _loginState

    val _registerState = MutableStateFlow<Resource<FirebaseAuth?>?>(null)
    val registerState: StateFlow<Resource<FirebaseAuth?>?> = _registerState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {

        _isLoggedIn.value = isUserLoggedIn()
    }
    open fun isUserLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
    open fun login(email: String, password: String) {
        if (_loginState.value !is Resource.Loading) {
            viewModelScope.launch {
                _loginState.value = Resource.Loading()
                val result = repository.login(email, password)
                _loginState.value = result
                if (result is Resource.Success) {
                    _isLoggedIn.value = true
                }
            }
        }
    }


    fun register(name: String, surname: String, age: Int, email: String, password: String) {
        val user = User(name, surname, age, email)
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
            _logoutStatus.value = true
        }
    }

    private val _resetPasswordState = MutableStateFlow<Resource<Unit>?>(null)
    val resetPasswordState: StateFlow<Resource<Unit>?> = _resetPasswordState
    fun resetPassword(email: String) {
        viewModelScope.launch {
            _resetPasswordState.value = Resource.Loading()
            val result = repository.resetPassword(email)
            _resetPasswordState.value = result
        }
    }
}
