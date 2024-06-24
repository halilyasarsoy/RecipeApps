package com.halil.recipeapps.ui.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halil.recipeapps.data.model.User
import com.halil.recipeapps.data.repository.UserRepository
import com.halil.recipeapps.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userData = MutableStateFlow<Resource<User>?>(null)
    val userData: StateFlow<Resource<User>?> = _userData

    fun fetchUserData(userId: String) {
        viewModelScope.launch {
            _userData.value = Resource.Loading()
            _userData.value = userRepository.getUserData(userId)
        }
    }

    fun clearUserData() {
        _userData.value = null
    }
}