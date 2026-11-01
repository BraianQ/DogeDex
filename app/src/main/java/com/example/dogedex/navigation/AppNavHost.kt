package com.example.dogedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dogedex.auth.presentation.LoginScreen
import com.example.dogedex.auth.presentation.SignUpScreen
import com.example.dogedex.dogdetail.presentation.DogDetailScreen
import com.example.dogedex.doglist.domain.Dog
import com.example.dogedex.doglist.presentation.DogListScreen

sealed class NavDestination(val route: String) {
    object Login : NavDestination("login")
    object SignUp : NavDestination("sign_up")
    object DogList : NavDestination("dog_list")
    object DogDetail : NavDestination("dog_detail")
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.Login.route
    ) {
        composable(NavDestination.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(NavDestination.DogList.route) {
                        popUpTo(NavDestination.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(NavDestination.SignUp.route)
                }
            )
        }

        composable(NavDestination.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(NavDestination.DogList.route) {
                        popUpTo(NavDestination.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavDestination.DogList.route) {
            DogListScreen(
                onDogClick = { dog ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("selected_dog", dog)
                    navController.navigate(NavDestination.DogDetail.route)
                }
            )
        }

        composable(NavDestination.DogDetail.route) {
            val dog = navController.previousBackStackEntry?.savedStateHandle?.get<Dog>("selected_dog")
            if (dog != null) {
                DogDetailScreen(
                    dog = dog,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
