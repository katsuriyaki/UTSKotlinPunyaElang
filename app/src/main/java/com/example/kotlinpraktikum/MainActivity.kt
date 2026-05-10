package com.example.kotlinpraktikum

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinpraktikum.ui.theme.KotlinPraktikumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        enableEdgeToEdge()
        setContent {
            KotlinPraktikumTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent(viewModel: RegistrationViewModel = viewModel()) {
    val submittedProfile by viewModel.submittedProfile.collectAsState()

    if (submittedProfile != null) {
        ProfileScreen(
            profileData = submittedProfile!!,
            onBerandaClick = {  }
        )
    } else {
        // RegistrationScreen has its own Scaffold — no wrapper needed
        RegistrationScreen(viewModel = viewModel)
    }
}