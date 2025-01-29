package com.example.uaspam.ui.home.view.panen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.customwidget.CustomTopAppBar
import com.example.uaspam.ui.home.PenyediaViewModel
import com.example.uaspam.ui.home.viewmodel.panen.InsertPanenViewModel
import com.example.uaspam.ui.home.viewmodel.panen.InsertpaUiEvent
import com.example.uaspam.ui.home.viewmodel.panen.InsertpaUiState

import kotlinx.coroutines.launch

// Membuat objek DestinasiEntry yang mengimplementasikan interface DestinasiNavigasi
object DestinasiInsertPanen : DestinasiNavigasi {

    override val route = "item_entry_panen"

    override val titleRes = "Tambah Catatan Panen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPnView(
    navigateBack: () -> Unit,
    id_panen: String? = null,
    id_tanaman: String,
    modifier: Modifier = Modifier,
    viewModel: InsertPanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    LaunchedEffect(id_tanaman) {
        viewModel.initializeWithIdTanaman(id_tanaman)
    }

    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiInsertPanen.titleRes,
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
                        colors = listOf(Color(0xFF777109), Color(0xFF112C02), Color(0xFF495C02))
                    )
                )
                .padding(innerPadding)
        ) {
            EntryBodyPanen(
                insertpaUiState = viewModel.insertpaUiState,
                onPanenValueChange = viewModel::updateInsertPnState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.insertPn()
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
fun EntryBodyPanen(
    insertpaUiState: InsertpaUiState,
    onPanenValueChange: (InsertpaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInputPanen(
            insertpaUiEvent = insertpaUiState.insertpaUiEvent,
            onValueChange = onPanenValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8C9A67), // Warna kuning cerah
                contentColor = Color.White // Warna teks tombol
            )
        ) {
            Text(text = "Simpan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPanen(
    insertpaUiEvent: InsertpaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertpaUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val borderColor = Color.White // Warna border putih agar kontras
        val iconColor = Color.White // Warna ikon putih agar terlihat

        OutlinedTextField(
            value = insertpaUiEvent.id_panen,
            onValueChange = { onValueChange(insertpaUiEvent.copy(id_panen = it)) },
            label = { Text("ID Panen", color = Color.White) },
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
            value = insertpaUiEvent.id_tanaman,
            onValueChange = { onValueChange(insertpaUiEvent.copy(id_tanaman = it)) },
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
            value = insertpaUiEvent.tanggal_panen,
            onValueChange = { onValueChange(insertpaUiEvent.copy(tanggal_panen = it)) },
            label = { Text("Tanggal Panen", color = Color.White) },
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
            value = insertpaUiEvent.jumlah_panen,
            onValueChange = { onValueChange(insertpaUiEvent.copy(jumlah_panen = it)) },
            label = { Text("Jumlah Panen", color = Color.White) },
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
            value = insertpaUiEvent.keterangan,
            onValueChange = { onValueChange(insertpaUiEvent.copy(keterangan = it)) },
            label = { Text("Keterangan", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.ArrowForward, contentDescription = null, tint = iconColor) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
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
