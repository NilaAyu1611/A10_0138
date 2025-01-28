package com.example.uaspam.repository

import com.example.uaspam.model.AktivitasPertanian
import com.example.uaspam.servive_api.AktivitaspertanianService
import java.io.IOException

interface AktivitaspertanianRepository {
    suspend fun getAktivitasPertanian(): List<AktivitasPertanian>
    suspend fun insertAktivitasPertanian(aktivitasPertanian: AktivitasPertanian)
    suspend fun updateAktivitasPertanian(id_aktivitas: String, aktivitasPertanian: AktivitasPertanian)
    suspend fun deleteAktivitasPertanian(id_aktivitas: String)
    suspend fun getAktivitasPertanianById(id_aktivitas: String): AktivitasPertanian
}

class NetworkaktivitaspertanianRepository(
    private val aktivitaspertanianApiService: AktivitaspertanianService
) : AktivitaspertanianRepository {
    override suspend fun insertAktivitasPertanian(aktivitasPertanian: AktivitasPertanian) {
        aktivitaspertanianApiService.insertAktivitasPertanian(aktivitasPertanian)
    }

    override suspend fun updateAktivitasPertanian(id_aktivitas: String, aktivitasPertanian: AktivitasPertanian) {
        aktivitaspertanianApiService.updateAktivitasPertanian(id_aktivitas,aktivitasPertanian)
    }

    override suspend fun deleteAktivitasPertanian(id_aktivitas: String){
        try {
            val response = aktivitaspertanianApiService.deleteAktivitasPertanian(id_aktivitas)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete AktivitasPertanian. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun getAktivitasPertanian(): List<AktivitasPertanian> = aktivitaspertanianApiService.getAktivitasPertanian()
    override suspend fun getAktivitasPertanianById(id_aktivitas: String): AktivitasPertanian {
        return aktivitaspertanianApiService.getAktivitasPertanianById(id_aktivitas)
    }
}