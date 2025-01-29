package com.example.uaspam.ui.home.view.pekerja

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.customwidget.CustomTopAppBar
import com.example.uaspam.ui.home.PenyediaViewModel
import com.example.uaspam.ui.home.viewmodel.pekerja.InsertPekerjaViewModel
import com.example.uaspam.ui.home.viewmodel.pekerja.InsertpeUiEvent
import com.example.uaspam.ui.home.viewmodel.pekerja.InsertpeUiState

import kotlinx.coroutines.launch

// Membuat objek DestinasiEntry yang mengimplementasikan interface DestinasiNavigasi
object DestinasiInsertPekerja : DestinasiNavigasi {
    override val route = "item_entry_pekerja"
    override val titleRes = "Tambah Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPkjView(
    navigateBack: () -> Unit,
    id_pekerja: String? = null,
    modifier: Modifier = Modifier,
    viewModel: InsertPekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiInsertPekerja.titleRes,
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
                        colors = listOf(Color(0xFF3C8D58), Color(0xFF163B2A), Color(0xFF1A5B3D))
                    )
                )
                .padding(innerPadding)
        ) {
            EntryBodyPekerja(
                insertpeUiState = viewModel.insertpeUiState,
                onPekerjaValueChange = viewModel::updateInsertPkjState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.insertPkj()
                        navigateBack()
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
fun EntryBodyPekerja(
    insertpeUiState: InsertpeUiState,
    onPekerjaValueChange: (InsertpeUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInputPekerja(
            insertpeUiEvent = insertpeUiState.insertpeUiEvent,
            onValueChange = onPekerjaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7C8B5A), // Warna hijau cerah
                contentColor = Color.White // Warna teks tombol
            )
        ) {
            Text(
                text = "Simpan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPekerja(
    insertpeUiEvent: InsertpeUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertpeUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val borderColor = Color.White // Warna border putih
        val iconColor = Color.White // Warna ikon putih

        // Input untuk "Nama Pekerja"
        OutlinedTextField(
            value = insertpeUiEvent.nama_pekerja,
            onValueChange = { onValueChange(insertpeUiEvent.copy(nama_pekerja = it)) },
            label = { Text("Nama Pekerja", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = iconColor) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = Color.White
            )
        )

        // Input untuk "ID Pekerja"
        OutlinedTextField(
            value = insertpeUiEvent.id_pekerja,
            onValueChange = { onValueChange(insertpeUiEvent.copy(id_pekerja = it)) },
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

        // Input untuk "Jabatan"
        OutlinedTextField(
            value = insertpeUiEvent.jabatan,
            onValueChange = { onValueChange(insertpeUiEvent.copy(jabatan = it)) },
            label = { Text("Jabatan", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.ThumbUp, contentDescription = null, tint = iconColor) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = Color.White
            )
        )

        // Input untuk "Kontak Pekerja"
        OutlinedTextField(
            value = insertpeUiEvent.kontak_pekerja,
            onValueChange = { onValueChange(insertpeUiEvent.copy(kontak_pekerja = it)) },
            label = { Text("Kontak Pekerja", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = iconColor) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
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
                color = Color.Yellow,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Divider(thickness = 2.dp, modifier = Modifier.padding(top = 16.dp), color = Color.White)
    }
}
