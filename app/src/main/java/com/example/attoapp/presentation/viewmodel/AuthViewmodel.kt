package com.example.attoapp.presentation.viewmodel

import android.util.Log
import android.view.View
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.attoapp.data.ApiService
import com.example.attoapp.data.Brands
import com.example.attoapp.data.RetrofitInstance
import com.example.attoapp.data.User
import com.example.attoapp.data.UserLogin
import com.example.attoapp.data.UserPreferences
import com.example.attoapp.data.UserResponse
import com.example.attoapp.domain.GetUseCase
import com.example.attoapp.domain.mergeFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewmodel @Inject constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
    private val getUseCase: GetUseCase,
) : ViewModel() {

    private val _currentName = MutableStateFlow<String>("")
    val currentName: StateFlow<String> get() = _currentName
    private val _currentSurname = MutableStateFlow<String>("")
    val currentSurname: StateFlow<String> get() = _currentSurname
    private val _currentLogin = MutableStateFlow<String>("")
    val currentLogin: StateFlow<String> get() = _currentLogin
    private val _currentPassword = MutableStateFlow<String>("")
    val currentPassword: StateFlow<String> get() = _currentPassword
    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> = _userId
    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo: StateFlow<User?> = _userInfo


    fun updateName(name: String) {
        _currentName.value = name
    }

    fun updateSurname(surname: String) {
        _currentSurname.value = surname
    }

    fun updateLogin(login: String) {
        _currentLogin.value = login
    }

    fun updatePassword(password: String) {
        _currentPassword.value = password
    }

    fun checkNumber(
        number : String,
        ifFree : () -> Unit,
        ifNotFree : () -> Unit
    ) {

        viewModelScope.launch {
            try {
                val response = apiService.checkPhone(number)
                if(response.isSuccessful) {
                    val result = response.body()

                    if (result?.isFree == false){
                        ifFree()

                    } else {
                        ifNotFree()
                    }
                }
            } catch (e: Exception) {

            }
        }
    }
    fun checkLogin(
        login : String,
        ifFree : () -> Unit,
        ifNotFree : () -> Unit
    ) {

        viewModelScope.launch {
            try {
                val response = apiService.checkUsername(login)
                if(response.isSuccessful) {
                    val result = response.body()

                    if (result?.isFree == false){
                        ifFree()
                    } else {

                        ifNotFree()
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    fun createUser(
        user: User,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.createUser(user)

                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.success == false) {
                        onSuccess()
                    } else {

                    }
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }

    fun loginingUser(
        username: String,
        password: String,
        onSuccess : () -> Unit
    ) {
        viewModelScope.launch {
            val response = loginUser(username, password)
            response?.let {
                if (it.success) {
                    userPreferences.saveUserId(userId = response.user_id!!)
                    onSuccess()
                } else {

                }
            } ?: run {


            }
        }
    }
    fun logout() {
        viewModelScope.launch {
            userPreferences.clearUserId()
            _userId.value = null
        }
    }
    suspend fun loginUser(username: String, password: String): UserResponse? {
        val user = UserLogin(username, password)

        return try {
            val response = RetrofitInstance.apiService.login(user)

            if (response.isSuccessful) {

                response.body()?.let {
                    it.copy(success = true)
                }
            } else {

                response.errorBody()?.string()?.let {

                    UserResponse(success = false, message = "Ошибка: $it")
                }
            }
        } catch (e: Exception) {

            UserResponse(success = false, message = "Ошибка сети: ${e.localizedMessage}")
        }
    }


}