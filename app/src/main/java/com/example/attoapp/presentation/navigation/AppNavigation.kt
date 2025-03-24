package com.example.attoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.attoapp.data.ApiService
import com.example.attoapp.data.DataRepository
import com.example.attoapp.data.DataRepositoryImpl
import com.example.attoapp.data.RetrofitInstance
import com.example.attoapp.data.UserPreferences
import com.example.attoapp.presentation.ui.pages.AuthorizationPage
import com.example.attoapp.presentation.ui.pages.CartPage
import com.example.attoapp.presentation.ui.pages.CatalogPage
import com.example.attoapp.presentation.ui.pages.FavoritePage
import com.example.attoapp.presentation.ui.pages.ItemPage
import com.example.attoapp.presentation.ui.pages.MainPage
import com.example.attoapp.presentation.ui.pages.MoreInfoShopPage
import com.example.attoapp.presentation.ui.pages.ProfilePage
import com.example.attoapp.presentation.ui.pages.RegistrationPage1
import com.example.attoapp.presentation.ui.pages.RegistrationPage2
import com.example.attoapp.presentation.ui.pages.RegistrationPage3
import com.example.attoapp.presentation.ui.pages.ShopFeedbacks
import com.example.attoapp.presentation.ui.pages.sections.favoriteP.AllShops
import com.example.attoapp.presentation.ui.pages.sections.itemP.AllFeedbacksItem
import com.example.attoapp.presentation.viewmodel.AuthViewmodel
import com.example.attoapp.presentation.viewmodel.DataViewModel


@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController() ,
    viewModel: DataViewModel = hiltViewModel() ,
    authViewmodel: AuthViewmodel = hiltViewModel()
) {

    var userId = viewModel.userId.collectAsState()
    NavHost(navController = navController, startDestination = if(userId != null) Routes.LOGIN else Routes.LOGIN) {
        composable(Routes.MAIN) { MainPage(navController, viewModel) }
        composable(Routes.CATALOG) { CatalogPage(navController, viewModel) }
        composable(Routes.FAVORITE) { FavoritePage(navController, authViewmodel, viewModel) }
        composable(Routes.ITEM) { ItemPage(navController, viewModel) }
        composable(Routes.INFO) { MoreInfoShopPage(navController, viewModel) }
        composable(Routes.FEEDBACKS_SHOP) { ShopFeedbacks(navController, viewModel) }
        composable(Routes.FEEDBACKS_ITEM) { AllFeedbacksItem(navController, viewModel) }
        composable(Routes.ALLFAVORITESHOPS) { AllShops(navController, viewModel) }
        composable(Routes.SHOPALLINFO) { MoreInfoShopPage(navController, viewModel) }
        composable(Routes.PROFILE) { ProfilePage(navController, viewModel) }
        composable(Routes.CART) { CartPage(navController, viewModel) }
        composable(Routes.LOGIN) {
            AuthorizationPage(navController, authViewmodel, userPreferences = UserPreferences(LocalContext.current), dataViewModel = viewModel)
        }
        composable(Routes.CODE) { RegistrationPage3(navController) }
        composable(Routes.VERIFICATION) { RegistrationPage2(authViewmodel, navController) }
        composable(Routes.REGISTER) {
            RegistrationPage1(navController, authViewmodel, onLoginExist = { })
        }
    }
}
