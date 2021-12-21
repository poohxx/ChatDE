package com.poohxx.chatde

import android.inputmethodservice.AbstractInputMethodService
import android.os.Message

data class User(
    val name: String? = null,
    val message: String? = null
)
