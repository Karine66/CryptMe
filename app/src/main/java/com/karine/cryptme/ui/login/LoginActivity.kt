package com.karine.cryptme.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import com.karine.common.BiometricAuthListener
import com.karine.cryptme.databinding.ActivityLoginBinding
import com.karine.cryptme.ui.listMessage.ListMessageActivity
import com.karine.utils.BiometricUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), BiometricAuthListener {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)
    }
    fun onClickLogin(view: View) {
        navigateToListActivity()
    }

    fun onClickBiometrics(view: View) {
        BiometricUtil.showBiometricPrompt(
            activity = this,
            listener = this,
            cryptoObject = null,
            allowDeviceCredential = true
        )
    }

    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
       navigateToListActivity()
    }

    override fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String) {
        Toast.makeText(this, "Biometric login failed. Error: $errorMessage", Toast.LENGTH_SHORT)
            .show()
    }

    fun navigateToListActivity() {
        startActivity(Intent(this, ListMessageActivity::class.java))
        finish()
    }
    // for button biometric visibility with compatible phone
    fun showBiometricLoginOption() {
        buttonBiometricsLogin.visibility =
            if (BiometricUtil.isBiometricReady(this)) View.VISIBLE
            else View.GONE
    }


}