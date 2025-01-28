package com.example.uaspam.model

import kotlinx.serialization.Serializable

@Serializable
data class Tanaman(
    val id_tanaman: String,
    val nama_tanaman: String,
    val periode_tanam: String,
    val deskripsi_tanaman: String
)

//Tabel pekerja
@Serializable
data class Pekerja(
    val id_pekerja: String,
    val nama_pekerja: String,
    val jabatan: String,
    val kontak_pekerja: String
)

//tabel panen
@Serializable
data class Panen(
    val id_panen: String,
    val id_tanaman: String,
    val tanggal_panen: String,
    val jumlah_panen: String,
    val keterangan: String
)