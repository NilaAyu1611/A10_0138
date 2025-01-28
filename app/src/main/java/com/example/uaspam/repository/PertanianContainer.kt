package com.example.uaspam.repository

import com.example.uaspam.servive_api.AktivitaspertanianService
import com.example.uaspam.servive_api.PanenService
import com.example.uaspam.servive_api.PekerjaService
import com.example.uaspam.servive_api.TanamanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// Interface yang mendefinisikan kontainer aplikasi untuk mengelola berbagai repository
interface AppContainer {
    val tanamanRepository: TanamanRepository
    val pekerjaRepository: PekerjaRepository
    val aktivitaspertanianRepository: AktivitaspertanianRepository
    val panenRepository: PanenRepository
}

// Implementasi dari AppContainer yang menginisialisasi Retrofit dan repository
class PertanianContainer : AppContainer{
    private val baseurl = "http://10.0.2.2:81/pertanian/"       // URL dasar untuk API
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()      // Inisialisasi Retrofit dengan converter JSON
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseurl).build()

    //**------ Tanaman----------**
    // Inisialisasi layanan Tanaman dengan Retrofit
    private val tanamanService: TanamanService by lazy {
        retrofit.create(TanamanService::class.java) }
    // Inisialisasi repository Tanaman
    override val tanamanRepository: TanamanRepository by lazy {
        NetworktanamanRepository(tanamanService)}


    //**------ Pekerja----------**
    private val pekerjaService: PekerjaService by lazy {
        retrofit.create(PekerjaService::class.java) }
    override val pekerjaRepository: PekerjaRepository by lazy {
        NetworkpekerjaRepository(pekerjaService)}


    //**------ AktivitasPertanian----------**
    private val aktivitaspertanianService: AktivitaspertanianService by lazy {
        retrofit.create(AktivitaspertanianService::class.java) }
    override val aktivitaspertanianRepository: AktivitaspertanianRepository by lazy {
        NetworkaktivitaspertanianRepository(aktivitaspertanianService)}


    //**------ Panen----------**
    private val panenService: PanenService by lazy {
        retrofit.create(PanenService::class.java) }
    override val panenRepository: PanenRepository by lazy {
        NetworkpanenRepository(panenService)}
}