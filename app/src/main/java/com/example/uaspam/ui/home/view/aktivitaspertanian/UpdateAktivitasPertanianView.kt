package com.example.uaspam.ui.home.view.aktivitaspertanian

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.customwidget.CustomTopAppBar
import com.example.uaspam.ui.home.PenyediaViewModel
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.UpdateAktivitasPertanianViewModel
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.UpdateakUiEvent
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.UpdateakUiState

import kotlinx.coroutines.launch

object DestinasiUpdateAktivitasPertanian : DestinasiNavigasi {
    override val route = "update_view"
    override val titleRes = "Update Aktivitas Pertanian"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAktivitasPertanianView(
    id_aktivitas: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateAktivitasPertanianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Memuat data aktivitas pertanian berdasarkan ID saat layar dibuka
    LaunchedEffect(id_aktivitas) {
        viewModel.getAktivitasPertanianById(id_aktivitas)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateAktivitasPertanian.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF826B0D), Color(0xFF022F1F), Color(0xFF909B00))
                    )
                )
                .padding(innerPadding)
        ) {
            UpdateBodyAktivitas(
                updateakUiState = viewModel.updateakUiState,
                onAktivitasValueChange = viewModel::updateAkState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updateAk()
                        navigateBack() // Kembali setelah berhasil memperbarui data
                    }
                },
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun UpdateBodyAktivitas(
    updateakUiState: UpdateakUiState,
    onAktivitasValueChange: (UpdateakUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        FormInputAktivitas(
            updateakUiEvent = updateakUiState.updateakUiEvent,
            onValueChange = onAktivitasValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8C9A67), // Warna hijau tua
                contentColor = Color.White // Warna teks tombol
            )
        ) {
            Text(
                text = "Update",
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
fun FormInputAktivitas(
    updateakUiEvent: UpdateakUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateakUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val borderColor = Color.White
        val iconColor = Color.White

        OutlinedTextField(
            value = updateakUiEvent.id_aktivitas,
            onValueChange = { onValueChange(updateakUiEvent.copy(id_aktivitas = it)) },
            label = { Text("ID Aktivitas", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = iconColor) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = Color.White
            )
        )

        OutlinedTextField(
            value = updateakUiEvent.id_tanaman,
            onValueChange = { onValueChange(updateakUiEvent.copy(id_tanaman = it)) },
            label = { Text("ID Tanaman", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = iconColor) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = Color.White
            )
        )

        OutlinedTextField(
            value = updateakUiEvent.id_pekerja,
            onValueChange = { onValueChange(updateakUiEvent.copy(id_pekerja = it)) },
            label = { Text("ID Pekerja", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = iconColor) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = Color.White
            )
        )

        OutlinedTextField(
            value = updateakUiEvent.tanggal_aktivitas,
            onValueChange = { onValueChange(updateakUiEvent.copy(tanggal_aktivitas = it)) },
            label = { Text("Tanggal Aktivitas", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null, tint = iconColor) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = Color.White
            )
        )

        OutlinedTextField(
            value = updateakUiEvent.deskripsi_aktivitas,
            onValueChange = { onValueChange(updateakUiEvent.copy(deskripsi_aktivitas = it)) },
            label = { Text("Deskripsi Aktivitas", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null, tint = iconColor) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = Color.White
            )
        )
    }
}
