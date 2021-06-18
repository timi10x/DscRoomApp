package com.example.dscroomapp.presentation

import androidx.lifecycle.ViewModel
import com.example.dscroomapp.respository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel(){
    val users = repository.getUser()
}