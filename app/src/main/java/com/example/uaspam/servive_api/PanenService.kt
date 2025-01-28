package com.example.uaspam.servive_api

import com.example.uaspam.model.Panen
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PanenService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacapanen.php")
    suspend fun getPanen(): List<Panen>

    @GET("baca1panen.php/{id_panen}")
    suspend fun getPanenById(@Query("id_panen")id_panen: String): Panen

    @POST("insertpanen.php")
    suspend fun insertPanen(@Body panen: Panen)

    @PUT("editpanen.php/{id_panen}")
    suspend fun updatePanen(@Query("id_panen")id_panen: String, @Body panen: Panen)

    @DELETE("deletepanen.php/{id_panen}")
    suspend fun deletePanen(@Query("id_panen")id_panen: String): Response<Void>

}