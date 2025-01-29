package com.example.uaspam.ui.home.viewmodel.tanaman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Tanaman
import com.example.uaspam.repository.TanamanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailTanamanViewModel(private val tnmRepo: TanamanRepository) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    fun getTanamanById(id_tanaman: String) {
        viewModelScope.launch {
            try {
                val tanaman = tnmRepo.getTanamanById(id_tanaman)
                if (tanaman != null) {
                    _detailUiState.value = DetailUiState.Success(tanaman)
                } else {
                    _detailUiState.value = DetailUiState.Error("Data Tanaman tidak ditemukan.")
                }
            } catch (e: Exception) {
                _detailUiState.value = DetailUiState.Error(e.localizedMessage ?: "Terjadi kesalahan.")
            }
        }
    }

    fun deleteTnm(id_tanaman: String) {
        viewModelScope.launch {
            try {
                tnmRepo.deleteTanaman(id_tanaman)
                _detailUiState.value = DetailUiState.Error("Data Tanaman telah dihapus.")
            } catch (e: Exception) {
                _detailUiState.value = DetailUiState.Error(e.localizedMessage ?: "Gagal menghapus data.")
            }
        }
    }
}

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val tanaman: Tanaman) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}




