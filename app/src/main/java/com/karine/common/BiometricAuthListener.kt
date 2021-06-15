package com.karine.common

interface BiometricAuthListener {

    fun onBiometricAuthenticationSuccess(result: androidx.biometric.BiometricPrompt.AuthenticationResult)
    fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String)
}