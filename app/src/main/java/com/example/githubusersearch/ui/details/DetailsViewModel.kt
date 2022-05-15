package com.example.githubusersearch.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusersearch.data.Status
import com.example.githubusersearch.data.database.model.UserDetails
import com.example.githubusersearch.data.repository.DetailsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: DetailsRepository
) : ViewModel() {

    private val _repo = MutableLiveData<Status<UserDetails>>()
    val user: LiveData<Status<UserDetails>> get() = _repo

    fun getRepo(name: String) {
        viewModelScope.launch {
            repository.getRepoById(name).collect {
                _repo.value = it
            }
        }
    }

}