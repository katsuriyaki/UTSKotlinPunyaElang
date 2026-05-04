package com.example.kotlinpraktikum

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegistrationViewModel : ViewModel() {

    private val _nim = MutableStateFlow("")
    val nim: StateFlow<String> = _nim.asStateFlow()

    private val _nimError = MutableStateFlow(false)
    val nimError: StateFlow<Boolean> = _nimError.asStateFlow()

    private val _namaLengkap = MutableStateFlow("")
    val namaLengkap: StateFlow<String> = _namaLengkap.asStateFlow()

    private val _namaLengkapError = MutableStateFlow(false)
    val namaLengkapError: StateFlow<Boolean> = _namaLengkapError.asStateFlow()

    private val _jenisKelamin = MutableStateFlow("Laki-Laki")
    val jenisKelamin: StateFlow<String> = _jenisKelamin.asStateFlow()

    private val _kelas = MutableStateFlow("RKS 2A")
    val kelas: StateFlow<String> = _kelas.asStateFlow()

    val kelasList = listOf("RKS 2A", "RKS 2B", "RKS 2C")

    private val _nomorTelepon = MutableStateFlow("")
    val nomorTelepon: StateFlow<String> = _nomorTelepon.asStateFlow()

    private val _nomorTeleponError = MutableStateFlow(false)
    val nomorTeleponError: StateFlow<Boolean> = _nomorTeleponError.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _emailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean> = _emailError.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _passwordError = MutableStateFlow(false)
    val passwordError: StateFlow<Boolean> = _passwordError.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _confirmPasswordError = MutableStateFlow(false)
    val confirmPasswordError: StateFlow<Boolean> = _confirmPasswordError.asStateFlow()


    fun updateNim(newNim: String) {
        // Only allow digits
        val digits = newNim.filter { it.isDigit() }
        _nim.value = digits
        _nimError.value = digits.isNotEmpty() && digits.length < 8
    }

    fun updateNamaLengkap(newNama: String) {
        _namaLengkap.value = newNama
        _namaLengkapError.value = newNama.isNotEmpty() && newNama.length < 8
    }

    fun updateJenisKelamin(newJenisKelamin: String) {
        _jenisKelamin.value = newJenisKelamin
    }

    fun updateKelas(newKelas: String) {
        _kelas.value = newKelas
    }

    fun updateNomorTelepon(newNomor: String) {
        val digits = newNomor.filter { it.isDigit() }
        _nomorTelepon.value = digits
        _nomorTeleponError.value = digits.isNotEmpty() && (digits.length < 10 || digits.length > 13)
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
        _emailError.value = newEmail.isNotEmpty() && !isValidEmail(newEmail)
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = newPassword.isNotEmpty() && !isValidPassword(newPassword)
        if (_confirmPassword.value.isNotEmpty()) {
            _confirmPasswordError.value = _confirmPassword.value != _password.value
        }
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        _confirmPasswordError.value =
            newConfirmPassword.isNotEmpty() && newConfirmPassword != _password.value
    }


    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    fun passwordStrength(password: String): String {
        val score = listOf(
            password.length >= 8,
            password.any { it.isDigit() },
            password.any { it.isUpperCase() },
            password.any { it.isLowerCase() },
            password.any { !it.isLetterOrDigit() }
        ).count { it }

        return when (score) {
            0, 1 -> "Very Weak"
            2 -> "Weak"
            3 -> "Medium"
            4 -> "Strong"
            else -> "Very Strong"
        }
    }

    fun canSubmit(): Boolean {
        return _nim.value.length >= 8 &&
                !_nimError.value &&
                _namaLengkap.value.length >= 8 &&
                !_namaLengkapError.value &&
                _nomorTelepon.value.length in 10..13 &&
                !_nomorTeleponError.value &&
                _email.value.isNotEmpty() &&
                !_emailError.value &&
                _password.value.isNotEmpty() &&
                !_passwordError.value &&
                _confirmPassword.value.isNotEmpty() &&
                !_confirmPasswordError.value
    }

    fun submitRegistration() {
        if (canSubmit()) {
            println("Registration successful for: ${_email.value}")
            android.util.Log.d("Registration", "NIM: ${_nim.value}, Nama: ${_namaLengkap.value}")
            clearAllData()
        }
    }

    fun cancelRegistration() {
        clearAllData()
    }

    private fun clearAllData() {
        _nim.value = ""
        _nimError.value = false
        _namaLengkap.value = ""
        _namaLengkapError.value = false
        _jenisKelamin.value = "Laki-Laki"
        _kelas.value = "RKS 2A"
        _nomorTelepon.value = ""
        _nomorTeleponError.value = false
        _email.value = ""
        _emailError.value = false
        _password.value = ""
        _passwordError.value = false
        _confirmPassword.value = ""
        _confirmPasswordError.value = false
    }
}