package com.example.uaspam.ui.home.view.tanaman

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.customwidget.CustomTopAppBar
import com.example.uaspam.ui.home.PenyediaViewModel
import com.example.uaspam.ui.home.viewmodel.tanaman.UpdateTanamanViewModel
import com.example.uaspam.ui.home.viewmodel.tanaman.UpdateUiEvent
import com.example.uaspam.ui.home.viewmodel.tanaman.UpdateUiState

import kotlinx.coroutines.launch

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update_view"
    override val titleRes = "Update Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTanamanView(
    id_tanaman: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Memuat data tanaman berdasarkan ID saat layar dibuka
    LaunchedEffect(id_tanaman) {
        viewModel.getTanamanById(id_tanaman)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF062501), Color(0xFF848112), Color(0xFF022F1F))
                    )
                )
        ) {
            UpdateBody(
                updateUiState = viewModel.updateUiState,
                onTanamValueChange = viewModel::updateTnmState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updateTnm()
                        navigateBack() // Kembali setelah berhasil memperbarui data
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            )
        }
    }
}

@Composable
fun UpdateBody(
    updateUiState: UpdateUiState,
    onTanamValueChange: (UpdateUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        FormInputWithIcon(
            updateUiEvent = updateUiState.updateUiEvent,
            onValueChange = onTanamValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF07470D), // Warna hijau tua
                contentColor = Color.White // Warna teks tombol
            )
        ) {
            Text(
                text = "Simpan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputWithIcon(
    updateUiEvent: UpdateUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        InputWithIcon(
            value = updateUiEvent.nama_tanaman,
            onValueChange = { onValueChange(updateUiEvent.copy(nama_tanaman = it)) },
            label = "Nama Tanaman",
            icon = Icons.Default.Create,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )

        InputWithIcon(
            value = updateUiEvent.id_tanaman,
            onValueChange = { onValueChange(updateUiEvent.copy(id_tanaman = it)) },
            label = "ID Tanaman",
            icon = Icons.Default.Info,
            modifier = Modifier.fillMaxWidth(),
            enabled = false // Tidak diizinkan mengubah ID Tanaman
        )

        InputWithIcon(
            value = updateUiEvent.periode_tanam,
            onValueChange = { onValueChange(updateUiEvent.copy(periode_tanam = it)) },
            label = "Periode Tanam",
            icon = Icons.Default.DateRange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )

        InputWithIcon(
            value = updateUiEvent.deskripsi_tanaman,
            onValueChange = { onValueChange(updateUiEvent.copy(deskripsi_tanaman = it)) },
            label = "Deskripsi Tanaman",
            icon = Icons.Default.ArrowForward,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        Text(
            text = "Perbarui Semua Data yang Dibutuhkan!",
            modifier = Modifier.padding(5.dp),
            color = Color(0xFF00FFD6) // Warna teks yang mencolok
        )

        Divider(
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}
