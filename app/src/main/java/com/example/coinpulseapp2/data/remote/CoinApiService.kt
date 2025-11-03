package com.example.coinpulseapp2.data.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinApiService {

    @Headers("x-access-token: coinranking1a8ec4dd1759e1299369767005e1868b2f686cb6b19855a8")
    @GET("coins")
    suspend fun getCoins(
        @Query("limit") limit: Int = 50
    ): CoinResponse

    @Headers("x-access-token: coinranking1a8ec4dd1759e1299369767005e1868b2f686cb6b19855a8")
    @GET("coin/{uuid}")
    suspend fun getCoinDetail(
        @Path("uuid") uuid: String
    ): CoinDetailResponse
}
