package com.example.uaspam.repository

import com.example.uaspam.model.Pekerja

interface PekerjaRepository {
    suspend fun getPekerja(): List<Pekerja>
    suspend fun insertPekerja(pekerja: Pekerja)
    suspend fun updatePekerja(id_pekerja: String, pekerja: Pekerja)
    suspend fun deletePekerja(id_pekerja: String)
    suspend fun getPekerjaById(id_pekerja: String): Pekerja
}