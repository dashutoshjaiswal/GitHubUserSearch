package com.example.githubusersearch.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusersearch.data.repository.DetailsRepository

/**
 * This class is needed for injecting repository into the ViewModel by DI
 * */
@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(
    private val repository: DetailsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository) as T
    }
}