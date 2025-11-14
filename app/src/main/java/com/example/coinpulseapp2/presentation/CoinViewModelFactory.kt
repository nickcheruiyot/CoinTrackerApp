package com.example.coinpulseapp2.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinpulseapp2.data.remote.repository.CoinRepository

class CoinViewModelFactory(
    private val repository: CoinRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoinViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}