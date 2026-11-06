package com.example.dogedex.navigation

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dogedex.auth.presentation.LoginScreen
import com.example.dogedex.auth.presentation.SignUpScreen
import com.example.dogedex.camera.presentation.CameraScreen
import com.example.dogedex.dogdetail.presentation.DogDetailScreen
import com.example.dogedex.doglist.domain.Dog
import com.example.dogedex.doglist.presentation.DogListScreen

sealed class NavDestination(val route: String) {
    object Login : NavDestination("login")
    object SignUp : NavDestination("sign_up")
    object DogList : NavDestination("dog_list")
    object DogDetail : NavDestination("dog_detail")
    object Camera : NavDestination("camera")
}

@Composable
fun AppNavHost(
    startDestination: String,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
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

        composable(NavDestination.DogList.route) { backStackEntry ->
            val photoUri by backStackEntry.savedStateHandle.getStateFlow<Uri?>("captured_photo_uri", null).collectAsState()
            val recognitionId by backStackEntry.savedStateHandle.getStateFlow<String?>("recognition_id", null).collectAsState()

            DogListScreen(
                photoUri = photoUri,
                recognitionId = recognitionId,
                onDogClick = { dog ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("selected_dog", dog)
                    navController.navigate(NavDestination.DogDetail.route)
                },
                onLogout = {
                    navController.navigate(NavDestination.Login.route) {
                        popUpTo(NavDestination.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onCameraClick = {
                    navController.navigate(NavDestination.Camera.route)
                },
                onResetRecognition = {
                    backStackEntry.savedStateHandle["captured_photo_uri"] = null
                    backStackEntry.savedStateHandle["recognition_id"] = null
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

        composable(NavDestination.Camera.route) {
            val context = LocalContext.current
            CameraScreen(
                onBackClick = { navController.popBackStack() },
                onPhotoCaptured = { uri, recognitions ->
                    val topRecognition = recognitions.firstOrNull()
                    val message = if (topRecognition != null) {
                        "Perro reconocido: ID ${topRecognition.id} (${(topRecognition.confidence * 100).toInt()}%)"
                    } else {
                        "No se pudo reconocer el perro"
                    }
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                    navController.previousBackStackEntry?.savedStateHandle?.set("captured_photo_uri", uri)
                    navController.previousBackStackEntry?.savedStateHandle?.set("recognition_id", topRecognition?.id)
                    navController.popBackStack()
                }
            )
        }
    }
}
