package com.example.uaspam.repository

import com.example.uaspam.model.Tanaman

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