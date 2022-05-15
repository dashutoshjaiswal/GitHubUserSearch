package com.example.githubusersearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusersearch.data.repository.SearchRepository

/**
 * This class is needed for injecting repository into the ViewModel by DI
 * */
@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    private val repository: SearchRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}