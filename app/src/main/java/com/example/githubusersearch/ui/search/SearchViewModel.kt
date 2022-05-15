package com.example.githubusersearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusersearch.data.Status
import com.example.githubusersearch.data.database.model.User
import com.example.githubusersearch.data.repository.SearchRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    private val _repos = MutableLiveData<Status<List<User>>>()
    val repos: LiveData<Status<List<User>>> get() = _repos

    fun getRepos(searchQuery: String) {
        viewModelScope.launch {
            repository.getRepos(searchQuery).collect {
                _repos.value = it
            }
        }
    }

}