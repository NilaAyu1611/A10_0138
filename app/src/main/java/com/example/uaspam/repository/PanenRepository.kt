package com.example.uaspam.repository

import com.example.uaspam.model.Panen

interface PanenRepository {
    suspend fun getPanen(): List<Panen>
    suspend fun insertPanen(panen: Panen)
    suspend fun updatePanen(id_panen: String, panen: Panen)
    suspend fun deletePanen(id_panen: String)
    suspend fun getPanenById(id_panen: String): Panen
}


