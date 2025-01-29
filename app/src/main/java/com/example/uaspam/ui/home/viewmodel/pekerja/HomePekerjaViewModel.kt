package com.example.uaspam.ui.home.viewmodel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Pekerja
import com.example.uaspam.repository.PekerjaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomepeUiState{ // Digunakan untuk membatasi subclass yang dapat di-extend dari kelas ini.

    // Subclass Success
    data class Success(val pekerja: List<Pekerja>): HomepeUiState()

    // Subclass Error berupa object. Menunjukkan bahwa terjadi kesalahan tanpa detail tambahan.
    object Error: HomepeUiState()

    // Subclass Loading. Menunjukkan bahwa aplikasi sedang dalam proses memuat data.
    object Loading: HomepeUiState()
}

class HomePekerjaViewModel(private val pkj: PekerjaRepository): ViewModel() {

    // tnmUiState digunakan untuk menyimpan keadaan UI (state) tnm.
    // mutableStateOf digunakan untuk membuat state yang dapat berubah dan otomatis memicu pembaruan UI ketika nilainya berubah.
    // State awalnya diset ke HomeUiState.Loading.
    var pkjUiState: HomepeUiState by mutableStateOf(HomepeUiState.Loading)
        private set // Setter-nya dibuat private agar state hanya dapat diubah oleh ViewModel.

    init {
        getPkj()
    }

    fun getPkj() {
        viewModelScope.launch {

            // Set state ke Loading untuk menunjukkan bahwa data sedang diproses.
            pkjUiState = HomepeUiState.Loading

            // Mencoba mengambil data tanaman dari repository menggunakan blok try-catch.
            pkjUiState = try {

                // Jika berhasil, state diubah menjadi Success dengan daftar mahasiswa sebagai datanya.
                HomepeUiState.Success(pkj.getPekerja())
            }catch (e: IOException) {

                // Jika terjadi kesalahan jaringan atau I/O, set state ke Error.
                HomepeUiState.Error
            }catch (e: HttpException) {

                // Jika terjadi kesalahan HTTP (misalnya, 404 atau 500), set state ke Error.
                HomepeUiState.Error
            }
        }
    }

    fun deletePkj(id_pekerja: String) {
        viewModelScope.launch {

            // Menggunakan blok try-catch untuk menangani kemungkinan kesalahan selama proses penghapusan.
            try {

                // Memanggil fungsi deleteTanaman pada repository untuk menghapus data mahasiswa berdasarkan NIM.
                pkj.deletePekerja(id_pekerja)
            }catch (e: IOException) {
                HomepeUiState.Error
            }catch (e: HttpException) {
                HomepeUiState.Error
            }
        }
    }
}