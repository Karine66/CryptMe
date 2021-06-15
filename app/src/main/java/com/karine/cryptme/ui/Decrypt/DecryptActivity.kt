package com.karine.cryptme.ui.Decrypt

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import com.karine.common.BiometricAuthListener
import com.karine.common.CommonUtils
import com.karine.common.EncryptedMessage
import com.karine.cryptme.R
import com.karine.cryptme.databinding.ActivityDecryptBinding
import com.karine.utils.BiometricUtil
import com.karine.utils.CryptographyUtil
import javax.crypto.Cipher

class DecryptActivity : AppCompatActivity(), BiometricAuthListener {
    private lateinit var decryptBinding: ActivityDecryptBinding
    var encryptedMessage: EncryptedMessage? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        decryptBinding = ActivityDecryptBinding.inflate(layoutInflater)
        val view = decryptBinding.root
        setContentView(view)
        encryptedMessage = intent.getParcelableExtra(getString(R.string.parcel_message))
    }

    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        result.cryptoObject?.cipher?.let {
            decryptAndDisplay(it)
        }
    }

    override fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String) {
        CommonUtils.displayMessage(window.decorView, "Biometric error: $errorMessage")
    }
    fun onClickDecryptMessage(view: View) {
        showBiometricPromptToDecrypt()
    }
    // for decrypt
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showBiometricPromptToDecrypt() {
        encryptedMessage?.initializationVector?.let { it ->
            val cryptoObject = BiometricPrompt.CryptoObject(
                CryptographyUtil.getInitializedCipherForEncryption(it)
            )

            BiometricUtil.showBiometricPrompt(
                activity = this,
                listener = this,
                cryptoObject = cryptoObject
            )
        }
    }
    //for convert to plaintext
    private fun decryptAndDisplay(cipher: Cipher) {
        encryptedMessage?.cipherText?.let { it ->
            val decryptedMessage = CryptographyUtil.decryptData(it, cipher)
            decryptBinding.textViewMessage.text = decryptedMessage
        }
    }

}