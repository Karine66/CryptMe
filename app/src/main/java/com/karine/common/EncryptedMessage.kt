package com.karine.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EncryptedMessage (

        val cipherText: ByteArray,
        val initializationVector: ByteArray,
        val savedAt: Long = System.currentTimeMillis()
    ) : Parcelable
