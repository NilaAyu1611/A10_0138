package com.example.uaspam.ui.home.viewmodel.tanaman

import com.example.uaspam.model.Tanaman

sealed class HomeUiState{ // Digunakan untuk membatasi subclass yang dapat di-extend dari kelas ini.

    // Subclass Success
    data class Success(val tanaman: List<Tanaman>): HomeUiState()

    // Subclass Error berupa object. Menunjukkan bahwa terjadi kesalahan tanpa detail tambahan.
    object Error: HomeUiState()

    // Subclass Loading. Menunjukkan bahwa aplikasi sedang dalam proses memuat data.
    object Loading: HomeUiState()
}
