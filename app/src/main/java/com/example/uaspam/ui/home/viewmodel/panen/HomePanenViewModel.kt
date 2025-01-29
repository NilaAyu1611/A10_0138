package com.example.uaspam.ui.home.viewmodel.panen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Panen
import com.example.uaspam.repository.PanenRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class HomepaUiState{ // Digunakan untuk membatasi subclass yang dapat di-extend dari kelas ini.

    // Subclass Success
    data class Success(val panen: List<Panen>): HomepaUiState()

    // Subclass Error berupa object. Menunjukkan bahwa terjadi kesalahan tanpa detail tambahan.
    object Error: HomepaUiState()

    // Subclass Loading. Menunjukkan bahwa aplikasi sedang dalam proses memuat data.
    object Loading: HomepaUiState()
}

class HomePanenViewModel(private val pn: PanenRepository): ViewModel() {

    // tnmUiState digunakan untuk menyimpan keadaan UI (state) tnm.
    // mutableStateOf digunakan untuk membuat state yang dapat berubah dan otomatis memicu pembaruan UI ketika nilainya berubah.
    // State awalnya diset ke HomeUiState.Loading.
    var pnUiState: HomepaUiState by mutableStateOf(HomepaUiState.Loading)
        private set // Setter-nya dibuat private agar state hanya dapat diubah oleh ViewModel.

    init {
        getPn()
    }

    fun getPn() {
        viewModelScope.launch {

            // Set state ke Loading untuk menunjukkan bahwa data sedang diproses.
            pnUiState = HomepaUiState.Loading

            // Mencoba mengambil data tanaman dari repository menggunakan blok try-catch.
            pnUiState = try {

                // Jika berhasil, state diubah menjadi Success dengan daftar mahasiswa sebagai datanya.
                HomepaUiState.Success(pn.getPanen())
            }catch (e: IOException) {

                // Jika terjadi kesalahan jaringan atau I/O, set state ke Error.
                HomepaUiState.Error
            }catch (e: HttpException) {

                // Jika terjadi kesalahan HTTP (misalnya, 404 atau 500), set state ke Error.
                HomepaUiState.Error
            }
        }
    }

    fun deletePn(id_panen: String) {
        viewModelScope.launch {

            // Menggunakan blok try-catch untuk menangani kemungkinan kesalahan selama proses penghapusan.
            try {

                // Memanggil fungsi deleteTanaman pada repository untuk menghapus data mahasiswa berdasarkan NIM.
                pn.deletePanen(id_panen)
            }catch (e: IOException) {
                HomepaUiState.Error
            }catch (e: HttpException) {
                HomepaUiState.Error
            }
        }
    }
}