package com.example.kotlinpraktikum

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinpraktikum.ui.theme.KotlinPraktikumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Prevent screenshots / screen recording of sensitive form data
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
fun AppContent(
    viewModel: RegistrationViewModel = viewModel()
) {
    // Observe the submitted profile; non-null → navigate to ProfileScreen
    val submittedProfile by viewModel.submittedProfile.collectAsState()

    if (submittedProfile != null) {
        // ── Profile Screen ───────────────────────────────────────────────────
        ProfileScreen(
            profileData = submittedProfile!!,
            onBerandaClick = {
                // "Beranda" Toast is shown inside ProfileScreen itself.
                // Navigate back to Registration (clear profile state).
                viewModel.onProfileNavigated()
            }
        )
    } else {
        // ── Registration Screen ──────────────────────────────────────────────
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            RegistrationScreen(
                viewModel = viewModel
            )
        }
    }
}