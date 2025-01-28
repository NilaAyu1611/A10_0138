package com.example.uaspam.repository

import com.example.uaspam.model.Panen
import com.example.uaspam.servive_api.PanenService
import java.io.IOException

interface PanenRepository {
    suspend fun getPanen(): List<Panen>
    suspend fun insertPanen(panen: Panen)
    suspend fun updatePanen(id_panen: String, panen: Panen)
    suspend fun deletePanen(id_panen: String)
    suspend fun getPanenById(id_panen: String): Panen
}

class NetworkpanenRepository(
    private val panenApiService: PanenService
) : PanenRepository {
    override suspend fun insertPanen(panen: Panen) {
        panenApiService.insertPanen(panen)
    }

    override suspend fun updatePanen(id_panen: String, panen: Panen) {
        panenApiService.updatePanen(id_panen, panen)
    }

    override suspend fun deletePanen(id_panen: String) {
        try {
            val response = panenApiService.deletePanen(id_panen)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Panen. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPanen(): List<Panen> = panenApiService.getPanen()
    override suspend fun getPanenById(id_panen: String): Panen {
        return panenApiService.getPanenById(id_panen)
    }
}
