package com.example.uaspam.ui.home.viewmodel.panen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Panen
import com.example.uaspam.repository.PanenRepository
import kotlinx.coroutines.launch


// ViewModel untuk mengatur data dan logika form update mahasiswa
class UpdatePanenViewModel(private val pn: PanenRepository) : ViewModel() {

    // Data untuk menyimpan keadaan form (seperti input dari pengguna)
    var updatepaUiState by mutableStateOf(UpdatepaUiState())
        private set

    // Fungsi untuk mendapatkan data mahasiswa berdasarkan NIM
    fun getPanenById(id_panen: String) {
        viewModelScope.launch {
            try {
                val panen = pn.getPanenById(id_panen)
                updatepaUiState = updatepaUiState.copy(
                    updatepaUiEvent = UpdatepaUiEvent(
                        id_panen =panen.id_panen,
                        id_tanaman = panen.id_tanaman,
                        tanggal_panen = panen.tanggal_panen,
                        jumlah_panen = panen.jumlah_panen,
                        keterangan = panen.keterangan
                    )
                )
            } catch (e: Exception) {
                // Handle error jika diperlukan
            }
        }
    }

    // Fungsi untuk mengubah data form ketika ada input dari pengguna
    fun updatePnState(updatepaUiEvent: UpdatepaUiEvent) {
        updatepaUiState = UpdatepaUiState(updatepaUiEvent = updatepaUiEvent) // Perbarui data berdasarkan event
    }

    // Fungsi untuk memuat data mahasiswa ke dalam form untuk di-update
    fun loadPanenData(panen: Panen) {
        updatepaUiState = panen.toUpdatepaUiStatePn()
    }

    // Fungsi untuk memperbarui data mahasiswa ke database
    fun updatePn() {
        viewModelScope.launch {
            try {
                val panen = updatepaUiState.updatepaUiEvent.toPn()
                pn.updatePanen(updatepaUiState.updatepaUiEvent.id_panen, panen)
                println("Update berhasil: $panen")
            } catch (e: Exception) {
                e.printStackTrace() // Log error jika ada
            }
        }
    }

}

// Menyimpan state form update mahasiswa
data class UpdatepaUiState(
    val updatepaUiEvent: UpdatepaUiEvent = UpdatepaUiEvent() // State default berisi objek kosong dari UpdateUiEvent
)

// Menyimpan data input pengguna untuk form update mahasiswa
data class UpdatepaUiEvent(
    val id_panen: String = "",
    val id_tanaman: String = "",
    val tanggal_panen: String = "",
    val jumlah_panen: String = "",
    val keterangan: String = ""
)


fun UpdatepaUiEvent.toPn(): Panen = Panen( // UpdateUiEvent > Mahasiswa > Simpan data Mhs ke db
    id_panen = id_panen,
    id_tanaman=id_tanaman,
    tanggal_panen=tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan = keterangan
)

// Fungsi untuk mengubah data panen menjadi UpdateUiState
fun Panen.toUpdatepaUiStatePn(): UpdatepaUiState = UpdatepaUiState( // Mahasiswa > updateUiEvent > Masuk ke UpdateUiState
    updatepaUiEvent = toUpdatepaUiEvent() // Memanggil fungsi toUpdateUiEvent untuk mengonversi data Mahasiswa
)

// Fungsi untuk mengubah data pane menjadi data UpdateUiEvent
fun Panen.toUpdatepaUiEvent(): UpdatepaUiEvent = UpdatepaUiEvent(
    id_panen = id_panen,
    id_tanaman=id_tanaman,
    tanggal_panen=tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan = keterangan
)

