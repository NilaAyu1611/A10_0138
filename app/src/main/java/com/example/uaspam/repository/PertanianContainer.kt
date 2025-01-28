package com.example.uaspam.repository

// Interface yang mendefinisikan kontainer aplikasi untuk mengelola berbagai repository
interface AppContainer {
    val tanamanRepository: TanamanRepository
    val pekerjaRepository: PekerjaRepository
    val aktivitaspertanianRepository: AktivitaspertanianRepository
    val panenRepository: PanenRepository
}
