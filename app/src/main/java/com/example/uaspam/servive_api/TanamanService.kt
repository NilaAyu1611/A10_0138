package com.example.uaspam.servive_api

import com.example.uaspam.model.Tanaman
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TanamanService {

    // Mendapatkan daftar Tanaman
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacatanaman.php")         // Endpoint untuk mendapatkan semua Tanaman
    suspend fun getTanaman(): List<Tanaman>

    @GET("baca1tanaman.php/{id_tanaman}")           // Endpoint untuk mendapatkan Tanaman berdasarkan ID
    suspend fun getTanamanById(@Query("id_tanaman")id_tanaman:String):Tanaman

    @POST("inserttanaman.php")
    suspend fun insertTanaman(@Body tanaman: Tanaman)

    @PUT("edittanaman.php/{id_tanaman}")
    suspend fun updateTanaman(@Query("id_tanaman")id_tanaman: String, @Body tanaman: Tanaman)

    @DELETE("deletetanaman.php/{id_tanaman}")
    suspend fun deleteTanaman(@Query("id_tanaman")id_tanaman: String): Response<Void>


}