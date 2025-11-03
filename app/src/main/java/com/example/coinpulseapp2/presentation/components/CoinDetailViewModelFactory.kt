package com.example.coinpulseapp2.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinpulseapp2.data.remote.repository.CoinRepository


class CoinDetailViewModelFactory(
    private val repository: CoinRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoinDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}