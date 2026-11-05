package com.example.dogedex.ui.permission

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RequestPermissions(
    permissions: List<String>,
    onAllPermissionsGranted: () -> Unit = {},
    onPermissionsResult: (Map<String, Boolean>) -> Unit = {}
) {
    val context = LocalContext.current

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        onPermissionsResult(result)
        if (result.values.all { it }) {
            onAllPermissionsGranted()
        }
    }

    LaunchedEffect(permissions) {
        val permissionsToRequest = permissions.filter { permission ->
            ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            permissionsLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            onAllPermissionsGranted()
        }
    }
}
