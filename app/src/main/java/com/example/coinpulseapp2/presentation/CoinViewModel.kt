package com.example.coinpulseapp2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinpulseapp2.data.remote.repository.CoinRepository
import com.example.coinpulseapp2.domain.model.Coin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
class CoinViewModel(
    private val repository: CoinRepository
) : ViewModel() {

    private val _coins = MutableStateFlow<List<Coin>>(emptyList())
    val coins: StateFlow<List<Coin>> = _coins

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchCoins()
    }

    private fun fetchCoins() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val coinList = repository.getCoins()
                Log.d("CoinViewModel", "Fetched coins: ${coinList.size}")
                _coins.value = coinList
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "An unexpected error occurred"
                Log.e("CoinViewModel", "Error fetching coins", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}

