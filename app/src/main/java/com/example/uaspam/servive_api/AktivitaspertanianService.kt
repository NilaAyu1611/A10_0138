package com.example.uaspam.servive_api

import com.example.uaspam.model.AktivitasPertanian
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AktivitaspertanianService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacaaktivitaspertanian.php")
    suspend fun getAktivitasPertanian(): List<AktivitasPertanian>

    @GET("baca1aktivitaspertanian.php/{id_aktivitas}")
    suspend fun getAktivitasPertanianById(@Query("id_aktivitas")id_aktivitas:String): AktivitasPertanian

    @POST("insertaktivitaspertanian.php")
    suspend fun insertAktivitasPertanian(@Body aktivitasPertanian: AktivitasPertanian)

    @PUT("editaktivitaspertanian.php/{id_aktivitas}")
    suspend fun updateAktivitasPertanian(@Query("id_aktivitas")id_aktivitas: String, @Body aktivitasPertanian: AktivitasPertanian)

    @DELETE("deleteaktivitaspertanian.php/{id_aktivitas}")
    suspend fun deleteAktivitasPertanian(@Query("id_aktivitas")id_aktivitas: String): Response<Void>
}