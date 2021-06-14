package com.karine.common

import android.hardware.biometrics.BiometricPrompt

interface BiometricAuthListener {

    fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult)
    fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String)
}