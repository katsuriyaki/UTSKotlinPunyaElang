package com.example.kotlinpraktikum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = viewModel()
) {
    val nim by viewModel.nim.collectAsState()
    val namaLengkap by viewModel.namaLengkap.collectAsState()
    val jenisKelamin by viewModel.jenisKelamin.collectAsState()
    val kelas by viewModel.kelas.collectAsState()
    val nomorTelepon by viewModel.nomorTelepon.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()

    val nimError by viewModel.nimError.collectAsState()
    val namaLengkapError by viewModel.namaLengkapError.collectAsState()
    val nomorTeleponError by viewModel.nomorTeleponError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()

    // Re-evaluate canSubmit whenever any relevant state changes
    val canSubmit by remember {
        derivedStateOf {
            nim.length >= 8 && !nimError &&
                    namaLengkap.length >= 8 && !namaLengkapError &&
                    nomorTelepon.length in 10..13 && !nomorTeleponError &&
                    email.isNotEmpty() && !emailError &&
                    password.isNotEmpty() && !passwordError &&
                    confirmPassword.isNotEmpty() && !confirmPasswordError
        }
    }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var kelasExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registration", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Create Account",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // ── NIM ──────────────────────────────────────────────────────────
            item {
                OutlinedTextField(
                    value = nim,
                    onValueChange = { viewModel.updateNim(it) },
                    label = { Text("NIM") },
                    placeholder = { Text("Contoh: 12345678") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = nimError,
                    supportingText = {
                        if (nimError) Text(
                            "NIM minimal 8 digit angka",
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }

            // ── Nama Lengkap ─────────────────────────────────────────────────
            item {
                OutlinedTextField(
                    value = namaLengkap,
                    onValueChange = { viewModel.updateNamaLengkap(it) },
                    label = { Text("Nama Lengkap") },
                    placeholder = { Text("Contoh: Budi Santoso") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = namaLengkapError,
                    supportingText = {
                        if (namaLengkapError) Text(
                            "Nama minimal 8 karakter",
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    singleLine = true
                )
            }

            // ── Jenis Kelamin ────────────────────────────────────────────────
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Jenis Kelamin",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        listOf("Laki-Laki", "Perempuan").forEach { gender ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = jenisKelamin == gender,
                                    onClick = { viewModel.updateJenisKelamin(gender) }
                                )
                                Text(
                                    text = gender,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // ── Kelas ────────────────────────────────────────────────────────
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Kelas",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = kelasExpanded,
                        onExpandedChange = { kelasExpanded = !kelasExpanded }
                    ) {
                        OutlinedTextField(
                            value = kelas,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = kelasExpanded)
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = kelasExpanded,
                            onDismissRequest = { kelasExpanded = false }
                        ) {
                            viewModel.kelasList.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        viewModel.updateKelas(option)
                                        kelasExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // ── Nomor Telepon ────────────────────────────────────────────────
            item {
                OutlinedTextField(
                    value = nomorTelepon,
                    onValueChange = { viewModel.updateNomorTelepon(it) },
                    label = { Text("Nomor Telepon") },
                    placeholder = { Text("Contoh: 08123456789") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = nomorTeleponError,
                    supportingText = {
                        if (nomorTeleponError) Text(
                            "Nomor telepon harus 10–13 digit angka",
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true
                )
            }

            // ── Email ────────────────────────────────────────────────────────
            item {
                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = { Text("Email") },
                    placeholder = { Text("Contoh: nama@email.com") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = emailError,
                    supportingText = {
                        if (emailError) Text(
                            "Format email tidak valid",
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
            }

            // ── Password ─────────────────────────────────────────────────────
            item {
                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.updatePassword(it) },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = passwordError,
                    supportingText = {
                        if (passwordError) {
                            Text("Minimal 8 karakter", color = MaterialTheme.colorScheme.error)
                        } else if (password.isNotEmpty()) {
                            val strength = viewModel.passwordStrength(password)
                            Text("Strength: $strength", fontWeight = FontWeight.Bold)
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Sembunyikan password" else "Tampilkan password"
                            )
                        }
                    },
                    singleLine = true
                )
            }

            // ── Konfirmasi Password ──────────────────────────────────────────
            item {
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { viewModel.updateConfirmPassword(it) },
                    label = { Text("Konfirmasi Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = confirmPasswordError,
                    supportingText = {
                        if (confirmPasswordError) Text(
                            "Password tidak cocok!",
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (confirmPasswordVisible) "Sembunyikan password" else "Tampilkan password"
                            )
                        }
                    },
                    singleLine = true
                )
            }

            // ── Buttons ──────────────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.cancelRegistration() },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    ) {
                        Text("Cancel", style = MaterialTheme.typography.titleMedium)
                    }

                    Button(
                        onClick = { viewModel.submitRegistration() },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        enabled = canSubmit
                    ) {
                        Text("Register", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            // ── Security Info Card ───────────────────────────────────────────
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Security Features:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Password tidak terlihat", style = MaterialTheme.typography.bodySmall)
                        Text("Validasi real-time", style = MaterialTheme.typography.bodySmall)
                        Text(
                            "Indikator kekuatan password",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            "Data sensitif tidak di-log",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen()
}