package com.example.uaspam.repository

import com.example.uaspam.model.Tanaman
import com.example.uaspam.servive_api.TanamanService
import java.io.IOException

// interface yang mendifinisikan operasi yang dilakukan pada data tanaman
interface TanamanRepository {
    // Mengambil daftar Tanaman
    suspend fun getTanaman(): List<Tanaman>

    // Menambah Tanaman baru
    suspend fun insertTanaman(tanaman: Tanaman)

    // Memperbarui data Tanaman yang sudah ada berdasarkan ID
    suspend fun updateTanaman(id_tanaman: String, tanaman: Tanaman)

    // Menghapus Tanaman berdasarkan ID
    suspend fun deleteTanaman(id_tanaman: String)

    // Mengambil Tanaman berdasarkan ID
    suspend fun getTanamanById(id_tanaman: String): Tanaman
}

// Implementasi dari TanamanRepository yang berinteraksi dengan API untuk melakukan operasi Tanaman
class NetworktanamanRepository(
    private val tanamanApiService: TanamanService
) : TanamanRepository {
    override suspend fun insertTanaman(tanaman: Tanaman) {
        tanamanApiService.insertTanaman(tanaman)
    }

    override suspend fun updateTanaman(id_tanaman: String, tanaman: Tanaman) {
        tanamanApiService.updateTanaman(id_tanaman, tanaman)
    }

    override suspend fun deleteTanaman(id_tanaman: String) {
        try {
            val response = tanamanApiService.deleteTanaman(id_tanaman)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Tanaman. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }



    override suspend fun getTanaman(): List<Tanaman> = tanamanApiService.getTanaman()

    // Mengambil Tanaman berdasarkan ID dari API
    override suspend fun getTanamanById(id_tanaman: String): Tanaman {
        return tanamanApiService.getTanamanById(id_tanaman)
    }
}