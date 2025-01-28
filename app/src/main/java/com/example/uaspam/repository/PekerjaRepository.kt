package com.example.uaspam.repository

import com.example.uaspam.model.Pekerja
import com.example.uaspam.servive_api.PekerjaService
import java.io.IOException

interface PekerjaRepository {
    suspend fun getPekerja(): List<Pekerja>
    suspend fun insertPekerja(pekerja: Pekerja)
    suspend fun updatePekerja(id_pekerja: String, pekerja: Pekerja)
    suspend fun deletePekerja(id_pekerja: String)
    suspend fun getPekerjaById(id_pekerja: String): Pekerja
}

class NetworkpekerjaRepository(
    private val pekerjaApiService: PekerjaService
) : PekerjaRepository {
    override suspend fun insertPekerja(pekerja: Pekerja) {
        pekerjaApiService.insertPekerja(pekerja)
    }

    override suspend fun updatePekerja(id_pekerja: String, pekerja: Pekerja) {
        pekerjaApiService.updatePekerja(id_pekerja, pekerja)
    }

    override suspend fun deletePekerja(id_pekerja: String) {
        try {
            val response = pekerjaApiService.deletePekerja(id_pekerja)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Pekerja. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPekerja(): List<Pekerja> = pekerjaApiService.getPekerja()
    override suspend fun getPekerjaById(id_pekerja: String):Pekerja {
        return pekerjaApiService.getPekerjaById(id_pekerja)
    }
}