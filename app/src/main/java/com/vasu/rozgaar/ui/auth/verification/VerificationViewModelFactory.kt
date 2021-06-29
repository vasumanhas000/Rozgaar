package com.vasu.rozgaar.ui.auth.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vasu.rozgaar.data.repository.AuthRepository

class VerificationViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VerificationViewModel(authRepository) as T
    }
}