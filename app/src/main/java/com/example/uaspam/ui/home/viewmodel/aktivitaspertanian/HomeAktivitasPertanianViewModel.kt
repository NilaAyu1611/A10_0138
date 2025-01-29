package com.example.uaspam.ui.home.viewmodel.aktivitaspertanian


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.AktivitasPertanian
import com.example.uaspam.repository.AktivitaspertanianRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeakUiState{ // Digunakan untuk membatasi subclass yang dapat di-extend dari kelas ini.

    // Subclass Success
    data class Success(val aktivitasPertanian: List<AktivitasPertanian>): HomeakUiState()

    // Subclass Error berupa object. Menunjukkan bahwa terjadi kesalahan tanpa detail tambahan.
    object Error: HomeakUiState()

    // Subclass Loading. Menunjukkan bahwa aplikasi sedang dalam proses memuat data.
    object Loading: HomeakUiState()
}

class HomeAktivitasPertanianViewModel(private val ak: AktivitaspertanianRepository): ViewModel() {

    // tnmUiState digunakan untuk menyimpan keadaan UI (state) tnm.
    // mutableStateOf digunakan untuk membuat state yang dapat berubah dan otomatis memicu pembaruan UI ketika nilainya berubah.
    // State awalnya diset ke HomeUiState.Loading.
    var akUiState: HomeakUiState by mutableStateOf(HomeakUiState.Loading)
        private set // Setter-nya dibuat private agar state hanya dapat diubah oleh ViewModel.

    init {
        getAk()
    }

    fun getAk() {
        viewModelScope.launch {

            // Set state ke Loading untuk menunjukkan bahwa data sedang diproses.
            akUiState = HomeakUiState.Loading

            // Mencoba mengambil data tanaman dari repository menggunakan blok try-catch.
            akUiState = try {

                // Jika berhasil, state diubah menjadi Success dengan daftar mahasiswa sebagai datanya.
                HomeakUiState.Success(ak.getAktivitasPertanian())
            }catch (e: IOException) {

                // Jika terjadi kesalahan jaringan atau I/O, set state ke Error.
                HomeakUiState.Error
            }catch (e: HttpException) {

                // Jika terjadi kesalahan HTTP (misalnya, 404 atau 500), set state ke Error.
                HomeakUiState.Error
            }
        }
    }

    fun deleteAk(id_aktivitas: String) {
        viewModelScope.launch {

            // Menggunakan blok try-catch untuk menangani kemungkinan kesalahan selama proses penghapusan.
            try {

                // Memanggil fungsi deleteTanaman pada repository untuk menghapus data mahasiswa berdasarkan NIM.
                ak.deleteAktivitasPertanian(id_aktivitas)
            }catch (e: IOException) {
                HomeakUiState.Error
            }catch (e: HttpException) {
                HomeakUiState.Error
            }
        }
    }
}
