package com.example.uaspam.ui.home.view.pekerja

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.customwidget.CustomTopAppBar
import com.example.uaspam.ui.home.PenyediaViewModel
import com.example.uaspam.ui.home.viewmodel.pekerja.UpdatePekerjaViewModel
import com.example.uaspam.ui.home.viewmodel.pekerja.UpdatepeUiEvent
import com.example.uaspam.ui.home.viewmodel.pekerja.UpdatepeUiState

import kotlinx.coroutines.launch

object DestinasiUpdatePekerja : DestinasiNavigasi {
    override val route = "update_view"
    override val titleRes = "Update Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePekerjaView(
    id_pekerja: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Tambahkan LaunchedEffect untuk mengambil data saat layar dibuka
    LaunchedEffect(id_pekerja) {
        viewModel.getPekerjaById(id_pekerja)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdatePekerja.titleRes,
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
                        colors = listOf(Color(0xFF8C9A67), Color(0xFF495C02))
                    )
                )
                .padding(innerPadding)
        ) {
            UpdateBodyPekerja(
                updatepeUiState = viewModel.updatepeUiState,
                onPekerjaValueChange = viewModel::updatePkjState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updatePkj()
                        navigateBack() // Pindah layar hanya setelah data berhasil diupdate
                    }
                },
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun UpdateBodyPekerja(
    updatepeUiState: UpdatepeUiState,
    onPekerjaValueChange: (UpdatepeUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInputPekerja(
            updatepeUiEvent = updatepeUiState.updatepeUiEvent,
            onValueChange = onPekerjaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8C9A67), // Warna hijau cerah
                contentColor = Color.White // Warna teks tombol
            )
        ) {
            Text(text = "Update", fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPekerja(
    updatepeUiEvent: UpdatepeUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatepeUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val borderColor = Color.White // Warna border putih agar kontras
        val iconColor = Color.White // Warna ikon putih agar terlihat

        OutlinedTextField(
            value = updatepeUiEvent.nama_pekerja,
            onValueChange = { onValueChange(updatepeUiEvent.copy(nama_pekerja = it)) },
            label = { Text("Nama Pekerja", color = Color.White) },
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
            value = updatepeUiEvent.id_pekerja,
            onValueChange = { onValueChange(updatepeUiEvent.copy(id_pekerja = it)) },
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
            value = updatepeUiEvent.jabatan,
            onValueChange = { onValueChange(updatepeUiEvent.copy(jabatan = it)) },
            label = { Text("Jabatan", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null, tint = iconColor) },
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
            value = updatepeUiEvent.kontak_pekerja,
            onValueChange = { onValueChange(updatepeUiEvent.copy(kontak_pekerja = it)) },
            label = { Text("Kontak Pekerja", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.ArrowForward, contentDescription = null, tint = iconColor) },
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

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(top = 8.dp),
                color = Color.Yellow, // Ubah warna menjadi kuning agar lebih terlihat
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp // Membesarkan ukuran teks
            )
        }

        Divider(thickness = 2.dp, modifier = Modifier.padding(top = 16.dp), color = Color.White)
    }
}
