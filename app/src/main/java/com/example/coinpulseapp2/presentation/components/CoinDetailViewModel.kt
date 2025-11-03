package com.example.coinpulseapp2.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinpulseapp2.data.remote.repository.CoinRepository
import com.example.coinpulseapp2.domain.model.CoinDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CoinDetailViewModel(
    private val repository: CoinRepository
) : ViewModel() {

    private val _coinDetail = MutableStateFlow<CoinDetail?>(null)
    val coinDetail: StateFlow<CoinDetail?> = _coinDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getCoinDetail(coinId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getCoinDetail(coinId)
                _coinDetail.value = result
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}