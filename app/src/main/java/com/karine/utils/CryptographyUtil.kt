package com.karine.utils

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import com.karine.common.EncryptedMessage
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object CryptographyUtil {

    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val YOUR_SECRET_KEY_NAME = "Y0UR$3CR3TK3YN@M3"
    private const val KEY_SIZE = 128
    private const val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
    private const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
    private const val ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES

    @RequiresApi(Build.VERSION_CODES.M)
    fun getOrCreateSecretKey(keyName: String): SecretKey {
        // 1
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null) // Keystore must be loaded before it can be accessed
        keyStore.getKey(keyName, null)?.let { return it as SecretKey }

        // 2
        val paramsBuilder = KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
        paramsBuilder.apply {
            setBlockModes(ENCRYPTION_BLOCK_MODE)
            setEncryptionPaddings(ENCRYPTION_PADDING)
            setKeySize(KEY_SIZE)
            setUserAuthenticationRequired(true)
        }

        // 3
        val keyGenParams = paramsBuilder.build()
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )
        keyGenerator.init(keyGenParams)

        return keyGenerator.generateKey()
    }
    //for encryption and descryption
    fun getCipher(): Cipher {
        val transformation = "$ENCRYPTION_ALGORITHM/$ENCRYPTION_BLOCK_MODE/$ENCRYPTION_PADDING"

        return Cipher.getInstance(transformation)
    }
    //prepare the cipher instance
    @RequiresApi(Build.VERSION_CODES.M)
    fun getInitializedCipherForEncryption(bytes: ByteArray): Cipher {
        val cipher = getCipher()
        val secretKey = getOrCreateSecretKey(YOUR_SECRET_KEY_NAME)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        return cipher
    }
    //for encrypt plaintext
    fun encryptData(plaintext: String, cipher: Cipher): EncryptedMessage {
        val ciphertext = cipher
            .doFinal(plaintext.toByteArray(Charset.forName("UTF-8")))
        return EncryptedMessage(ciphertext, cipher.iv)
    }
    //for decrypting message
    @RequiresApi(Build.VERSION_CODES.M)
    fun getInitializedCipherForDecryption(
        initializationVector: ByteArray? = null
    ): Cipher {
        val cipher = getCipher()
        val secretKey = getOrCreateSecretKey(YOUR_SECRET_KEY_NAME)
        cipher.init(
            Cipher.DECRYPT_MODE,
            secretKey,
            GCMParameterSpec(KEY_SIZE, initializationVector)
        )
        return cipher
    }
    fun decryptData(ciphertext: ByteArray, cipher: Cipher): String {
        val plaintext = cipher.doFinal(ciphertext)
        return String(plaintext, Charset.forName("UTF-8"))
    }


}