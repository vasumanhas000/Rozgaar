package com.vasu.rozgaar.ui.auth.profilesetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vasu.rozgaar.data.repository.AuthRepository
import com.vasu.rozgaar.ui.auth.verification.VerificationViewModel

class ProfileSetupViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileSetupViewModel(authRepository) as T
    }
}