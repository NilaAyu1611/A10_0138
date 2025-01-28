package com.example.uaspam.repository

import com.example.uaspam.model.AktivitasPertanian

interface AktivitaspertanianRepository {
    suspend fun getAktivitasPertanian(): List<AktivitasPertanian>
    suspend fun insertAktivitasPertanian(aktivitasPertanian: AktivitasPertanian)
    suspend fun updateAktivitasPertanian(id_aktivitas: String, aktivitasPertanian: AktivitasPertanian)
    suspend fun deleteAktivitasPertanian(id_aktivitas: String)
    suspend fun getAktivitasPertanianById(id_aktivitas: String): AktivitasPertanian
}