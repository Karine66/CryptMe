package com.karine.cryptme.ui.Encrypt

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import com.karine.common.BiometricAuthListener
import com.karine.common.CommonUtils
import com.karine.cryptme.databinding.ActivityEncryptBinding
import com.karine.utils.BiometricUtil
import com.karine.utils.CryptographyUtil
import com.karine.utils.PreferenceUtil
import javax.crypto.Cipher

class EncryptActivity : AppCompatActivity(), BiometricAuthListener {

    private lateinit var activityEncryptBinding: ActivityEncryptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       activityEncryptBinding = ActivityEncryptBinding.inflate(layoutInflater)
        val view = activityEncryptBinding.root
        setContentView(view)
    }

    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        result.cryptoObject?.cipher?.let {
            val message = activityEncryptBinding.textInputMessage.editText?.text.toString().trim()
            if (!TextUtils.isEmpty(message)) {
                encryptAndSave(message, it)
                confirmInput()
            }
        }
    }
            override fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String) {
                CommonUtils.displayMessage(window.decorView, "Biometric error: $errorMessage")
            }

       //for display the biometric prompt upon tapping the button
       @RequiresApi(Build.VERSION_CODES.M)
       fun onClickEncryptMessage(view: View) {
            val message = activityEncryptBinding.textInputMessage.editText?.text.toString().trim()
            if (!TextUtils.isEmpty(message)) {
                showBiometricPromptToEncrypt()
            }
        }
            private fun confirmInput() {
                activityEncryptBinding.textInputMessage.editText?.text = null
                CommonUtils.displayMessage(window.decorView.rootView, "Message Saved")
            }

            //create cryptoObject and display de biometric prompt

            @RequiresApi(Build.VERSION_CODES.M)
            private fun showBiometricPromptToEncrypt() {
                // 1
                val cryptoObject = BiometricPrompt.CryptoObject(
                    CryptographyUtil.getInitializedCipherForEncryption()
                )
                // 2
                BiometricUtil.showBiometricPrompt(
                    activity = this,
                    listener = this,
                    cryptoObject = cryptoObject
                )
            }

            //convert to plainTextMessage to Encrypted message
            private fun encryptAndSave(plainTextMessage: String, cipher: Cipher) {
                val encryptedMessage = CryptographyUtil.encryptData(plainTextMessage, cipher)

                PreferenceUtil.storeEncryptedMessage(
                    applicationContext,
                    prefKey = encryptedMessage.savedAt.toString(),
                    encryptedMessage = encryptedMessage
                )
            }
        }




