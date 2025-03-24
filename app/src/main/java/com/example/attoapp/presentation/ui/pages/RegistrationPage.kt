package com.example.attoapp.presentation.ui.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.attoapp.data.RetrofitInstance
import com.example.attoapp.data.User
import com.example.attoapp.presentation.navigation.Routes
import com.example.attoapp.presentation.viewmodel.AuthViewmodel
import com.example.attoapp.ui.theme.AccentColor
import com.example.attoapp.ui.theme.OsnTextColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Response
import kotlin.math.log
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.platform.LocalContext
import com.example.attoapp.data.UserPreferences
import com.example.attoapp.presentation.viewmodel.DataViewModel

@Composable
fun RegistrationPage1(
    navController: NavController,
    authViewModel: AuthViewmodel,
    onLoginExist: () -> Unit
) {

    val userName by authViewModel.currentName.collectAsState()
    val userSurname by authViewModel.currentSurname.collectAsState()
    val login by authViewModel.currentLogin.collectAsState()
    val password by authViewModel.currentPassword.collectAsState()
    var isLoading by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }  // Добавлено для Snackbar
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 20.dp ,end = 20.dp ,top = 50.dp)
            .padding(WindowInsets.systemBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Atto",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 48.sp,
            color = AccentColor,
        )

        Text(
            text = "Регистрация",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = OsnTextColor,
            modifier = Modifier.padding(top = 40.dp)
        )
        TextFieldBox(top = 36, placeholder = "Имя", value = userName, onValueChange = { authViewModel.updateName(it) }, password = false)
        TextFieldBox(top = 8, placeholder = "Фамилия", value = userSurname, onValueChange = { authViewModel.updateSurname(it) }, password = false)
        TextFieldBox(top = 8, placeholder = "Логин", value = login, onValueChange = { authViewModel.updateLogin(it) }, password = false)
        TextFieldBox(top = 8, placeholder = "Пароль", value = password, onValueChange = { authViewModel.updatePassword(it) }, password = true)

        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AccentColor)
                .clickable {
                    authViewModel.checkLogin(
                        login,
                        ifFree = {
                            authViewModel.updateName(userName)
                            authViewModel.updateSurname(userSurname)
                            authViewModel.updateLogin(login)
                            authViewModel.updatePassword(password)
                            navController.navigate(Routes.VERIFICATION)
                        },
                        ifNotFree = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Этот логин уже занят.")
                            }
                            Log.d("Ошибка","Ошибка")
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Создать аккаунт",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun RegistrationPage2(
    authViewModel : AuthViewmodel,
    navController: NavController
) {
    val name = authViewModel.currentName.collectAsState()
    val surname = authViewModel.currentSurname.collectAsState()
    val login = authViewModel.currentLogin.collectAsState()
    val password = authViewModel.currentPassword.collectAsState()

    val number = remember { mutableStateOf("+7") }

    val snackbarHostState = remember { SnackbarHostState() }  // Добавлено для Snackbar
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 20.dp ,end = 20.dp ,top = 50.dp)
            .padding(WindowInsets.systemBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Atto",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 48.sp,
            color = AccentColor,
        )

        Text(
            text = "Введите номер телефона",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = OsnTextColor,
            modifier = Modifier.padding(top = 40.dp)
        )
        Text(
            text = "Мы отправим СМС с кодом подтверждения",
            style = MaterialTheme.typography.labelMedium,
            color = OsnTextColor,
        )
        TextFieldBox(top = 8, placeholder = "Номер телефона", value = number.value, onValueChange = { newValue -> number.value = newValue }, password = false)

        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AccentColor)
                .clickable {
                    authViewModel.checkNumber(
                        number.value,
                        ifFree = {
                            val user = User(
                                name = name.value,
                                surname = surname.value,
                                username = login.value,
                                number = number.value,
                                password = password.value,
                            )
                            authViewModel.createUser(
                                user,
                                onSuccess = {
                                    navController.navigate(Routes.CODE)
                                },
                                onError = {},
                            )
                            Log.d("Data: ", "${user.name}, ${user.surname}, ${user.number}, ${user.password}, ${user.username}")
                        },
                        ifNotFree = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Этот логин уже занят.")
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Получить код",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun RegistrationPage3(navController: NavController) {
    val code = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 20.dp ,end = 20.dp ,top = 50.dp)
            .padding(WindowInsets.systemBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Atto",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 48.sp,
            color = AccentColor,
        )

        Text(
            text = "Введите код",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = OsnTextColor,
            modifier = Modifier.padding(top = 40.dp)
        )
        Text(
            text = "Полученный по СМС",
            style = MaterialTheme.typography.labelMedium,
            color = OsnTextColor,
        )

        TextFieldBox(top = 8, placeholder = "", value = code.value, onValueChange = { newValue -> code.value = newValue }, password = true)

        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AccentColor)
                .clickable {

                    navController.navigate(Routes.LOGIN)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Подтвердить",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}
@Composable
fun AuthorizationPage(
    navController: NavController,
    authViewModel : AuthViewmodel,
    dataViewModel : DataViewModel,
    userPreferences : UserPreferences) {

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 20.dp ,end = 20.dp ,top = 50.dp)
            .padding(WindowInsets.systemBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Atto",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 48.sp,
            color = AccentColor,
        )

        Text(
            text = "Авторизация",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = OsnTextColor,
            modifier = Modifier.padding(top = 40.dp)
        )

        TextFieldBox(top = 36, placeholder = "Логин", value = login, onValueChange = { newValue -> login = newValue }, password = false)
        TextFieldBox(top = 8, placeholder = "Пароль", value = password, onValueChange = { newValue -> password = newValue }, password = true)

        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AccentColor)
                .clickable {
                    authViewModel.loginingUser(
                        login,
                        password,
                        onSuccess = {
                            dataViewModel.fetchUserInfo()
                            navController.navigate(Routes.MAIN)
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Войти в аккаунт",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White
            )
        }


        Box(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(2.dp ,color = AccentColor ,RoundedCornerShape(8.dp))
                .clickable { navController.navigate(Routes.REGISTER) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Регистрация",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 18.sp,
                color = AccentColor
            )
        }
    }
}

@Composable
fun TextFieldBox(
    top: Int,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    password: Boolean
) {
    Box(
        modifier = Modifier
            .padding(top = top.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xffF4F3FA)) // Фон бокса
            .padding(horizontal = 16.dp) // Отступы внутри бокса
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp), // Чтобы текст был по центру
            textStyle = TextStyle(color = OsnTextColor, fontSize = 16.sp),
            visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        )
    }
}
