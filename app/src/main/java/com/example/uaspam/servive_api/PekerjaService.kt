package com.example.uaspam.servive_api

import com.example.uaspam.model.Pekerja
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PekerjaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacapekerja.php")
    suspend fun getPekerja(): List<Pekerja>

    @GET("baca1pekerja.php/{id_pekerja}")
    suspend fun getPekerjaById(@Query("id_pekerja")id_pekerja:String): Pekerja

    @POST("insertpekerja.php")
    suspend fun insertPekerja(@Body pekerja: Pekerja)

    @PUT("editpekerja.php/{id_pekerja}")
    suspend fun updatePekerja(@Query("id_pekerja")id_pekerja: String, @Body pekerja: Pekerja)

    @DELETE("deletepekerja.php/{id_pekerja}")
    suspend fun deletePekerja(@Query("id_pekerja")id_pekerja: String): Response<Void>
}