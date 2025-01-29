package com.example.uaspam.ui.home.view.aktivitaspertanian

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.customwidget.CustomTopAppBar
import com.example.uaspam.ui.home.PenyediaViewModel
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.InsertAktivitasPertanianViewModel
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.InsertakUiEvent
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.InsertakUiState

import kotlinx.coroutines.launch

object DestinasiInsertAktivitasPertanian : DestinasiNavigasi {
    override val route = "item_entry_aktivitas"
    override val titleRes = "Tambah Aktivitas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAkView(
    navigateBack: () -> Unit,
    id_aktivitas: String? = null,
    modifier: Modifier = Modifier,
    viewModel: InsertAktivitasPertanianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiInsertAktivitasPertanian.titleRes,
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
            EntryBodyAktivitasPertanian(
                insertakUiState = viewModel.insertakUiState,
                onAktivitasValueChange = viewModel::updateInsertAkState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.insertAk()
                        navigateBack()
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
fun EntryBodyAktivitasPertanian(
    insertakUiState: InsertakUiState,
    onAktivitasValueChange: (InsertakUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        FormInputAktivitasPertanian(
            insertakUiEvent = insertakUiState.insertakUiEvent,
            onValueChange = onAktivitasValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.fillMaxWidth(),
            colors = buttonColors(
                containerColor = Color(0xFF07470D), // Warna tombol
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
fun FormInputAktivitasPertanian(
    insertakUiEvent: InsertakUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertakUiEvent) -> Unit = {},
    enabled: Boolean = true
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        InputWithIcon(
            value = insertakUiEvent.id_aktivitas,
            onValueChange = { onValueChange(insertakUiEvent.copy(id_aktivitas = it)) },
            label = "ID Aktivitas Pertanian",
            icon = Icons.Default.Info,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )

        InputWithIcon(
            value = insertakUiEvent.id_tanaman,
            onValueChange = { onValueChange(insertakUiEvent.copy(id_tanaman = it)) },
            label = "ID Tanaman",
            icon = Icons.Default.Info,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )

        InputWithIcon(
            value = insertakUiEvent.id_pekerja,
            onValueChange = { onValueChange(insertakUiEvent.copy(id_pekerja = it)) },
            label = "ID Pekerja",
            icon = Icons.Default.Info,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )

        InputWithIcon(
            value = insertakUiEvent.tanggal_aktivitas,
            onValueChange = { onValueChange(insertakUiEvent.copy(tanggal_aktivitas = it)) },
            label = "Tanggal Aktivitas",
            icon = Icons.Default.DateRange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )

        InputWithIcon(
            value = insertakUiEvent.deskripsi_aktivitas,
            onValueChange = { onValueChange(insertakUiEvent.copy(deskripsi_aktivitas = it)) },
            label = "Deskripsi Aktivitas",
            icon = Icons.Default.ArrowForward,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(5.dp),
                color = Color(0xFF00FFD6) // Merah terang agar lebih mencolok
            )
        }

        Divider(
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputWithIcon(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF022F1F) // Warna ikon
            )
        },
        modifier = modifier
            .background(Color(0xFFE1E2A3), MaterialTheme.shapes.medium)
            .padding(horizontal = 12.dp),
        enabled = enabled,
        singleLine = singleLine,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF022F1F), // Border warna
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
