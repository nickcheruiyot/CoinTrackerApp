package com.example.coinpulseapp2.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinpulseapp2.data.remote.CoinApiService
import com.example.coinpulseapp2.data.remote.repository.CoinRepositoryImpl
import com.example.coinpulseapp2.domain.model.Coin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoinViewModel : ViewModel() {

    // ✅ Build Retrofit + API Service here
    private val apiService: CoinApiService = Retrofit.Builder()
        .baseUrl("https://api.coinranking.com/v2/") // Base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CoinApiService::class.java)

    // ✅ Inject into repository manually
    private val repository = CoinRepositoryImpl(apiService)

    private val _coins = MutableStateFlow<List<Coin>>(emptyList())
    val coins = _coins.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun fetchCoins() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _coins.value = repository.getCoins()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}