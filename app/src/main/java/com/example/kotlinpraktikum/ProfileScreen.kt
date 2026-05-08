package com.example.kotlinpraktikum

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

data class ProfileData(
    val nim: String,
    val namaLengkap: String,
    val jenisKelamin: String,
    val kelas: String,
    val nomorTelepon: String,
    val email: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileData: ProfileData,
    onBerandaClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    // =========================
    // STATE FOTO
    // =========================
    var imageBitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    // =========================
    // CAMERA LAUNCHER
    // =========================
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->

        if (bitmap != null) {
            imageBitmap = bitmap
        }
    }

    // =========================
    // PERMISSION LAUNCHER
    // =========================
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->

        if (granted) {

            cameraLauncher.launch(null)

        } else {

            Toast.makeText(
                context,
                "Izin kamera ditolak",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profil Mahasiswa",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // =========================
            // FOTO PROFIL
            // =========================
            Box(
                contentAlignment = Alignment.Center,

                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer
                    )
            ) {

                if (imageBitmap != null) {

                    Image(
                        bitmap = imageBitmap!!.asImageBitmap(),
                        contentDescription = "Foto Profil",
                        contentScale = ContentScale.Crop,

                        modifier = Modifier.fillMaxSize()
                    )

                } else {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Foto Profil",

                        modifier = Modifier.size(72.dp),

                        tint = MaterialTheme.colorScheme
                            .onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // =========================
            // BUTTON AMBIL FOTO
            // =========================
            Button(
                onClick = {

                    when (
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        )
                    ) {

                        PackageManager.PERMISSION_GRANTED -> {

                            cameraLauncher.launch(null)
                        }

                        else -> {

                            permissionLauncher.launch(
                                Manifest.permission.CAMERA
                            )
                        }
                    }
                }
            ) {

                Text("Ambil Foto")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = profileData.namaLengkap,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = profileData.nim,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // =========================
            // CARD DATA
            // =========================
            Card(
                modifier = Modifier.fillMaxWidth(),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        "Informasi Mahasiswa",

                        style = MaterialTheme.typography
                            .titleMedium,

                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.padding(
                            bottom = 12.dp
                        )
                    )

                    ProfileInfoRow(
                        icon = Icons.Default.School,
                        label = "NIM",
                        value = profileData.nim
                    )

                    Divider(
                        modifier = Modifier.padding(
                            vertical = 8.dp
                        )
                    )

                    ProfileInfoRow(
                        icon = Icons.Default.Person,
                        label = "Nama Lengkap",
                        value = profileData.namaLengkap
                    )

                    Divider(
                        modifier = Modifier.padding(
                            vertical = 8.dp
                        )
                    )

                    ProfileInfoRow(
                        icon = Icons.Default.Person,
                        label = "Jenis Kelamin",
                        value = profileData.jenisKelamin
                    )

                    Divider(
                        modifier = Modifier.padding(
                            vertical = 8.dp
                        )
                    )

                    ProfileInfoRow(
                        icon = Icons.Default.School,
                        label = "Kelas",
                        value = profileData.kelas
                    )

                    Divider(
                        modifier = Modifier.padding(
                            vertical = 8.dp
                        )
                    )

                    ProfileInfoRow(
                        icon = Icons.Default.Phone,
                        label = "No. Telepon",
                        value = profileData.nomorTelepon
                    )

                    Divider(
                        modifier = Modifier.padding(
                            vertical = 8.dp
                        )
                    )

                    ProfileInfoRow(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = profileData.email
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =========================
            // BUTTON BERANDA
            // =========================
            Button(
                onClick = {

                    Toast.makeText(
                        context,
                        "Menampilkan Halaman Beranda",
                        Toast.LENGTH_SHORT
                    ).show()

                    onBerandaClick()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        MaterialTheme.colorScheme.secondary
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,

                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    "Beranda",

                    style = MaterialTheme.typography
                        .titleMedium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ProfileInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,

            tint = MaterialTheme.colorScheme.primary,

            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {

            Text(
                text = label,

                style = MaterialTheme.typography.labelSmall,

                color = MaterialTheme.colorScheme
                    .onSurfaceVariant
            )

            Text(
                text = value,

                style = MaterialTheme.typography.bodyMedium,

                fontWeight = FontWeight.Medium
            )
        }
    }
}