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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.uaspam.ui.home.viewmodel.tanaman.InsertTanamanViewModel
import com.example.uaspam.ui.home.viewmodel.tanaman.InsertUiEvent
import com.example.uaspam.ui.home.viewmodel.tanaman.InsertUiState

import kotlinx.coroutines.launch

// Membuat objek DestinasiEntry yang mengimplementasikan interface DestinasiNavigasi
object DestinasiEntry : DestinasiNavigasi {

    // Mendefinisikan properti route yang mengacu pada string "item_entry"
    override val route = "item_entry_tanaman"

    // Mendefinisikan properti titleRes yang mengacu pada string "Entry Mhs"
    override val titleRes = "Tambah Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTnmView(
    navigateBack: () -> Unit,
    id_tanaman: String? = null, // Tambahkan parameter nim dengan nilai default null
    modifier: Modifier = Modifier,
    viewModel: InsertTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiEntry.titleRes,
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
            EntryBody(
                insertUiState = viewModel.insertUiState,
                onSiswaValueChange = viewModel::updateInsertTnmState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.insertTnm()
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
fun EntryBody(
    insertUiState: InsertUiState,
    onSiswaValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF07470D), // Warna kuning cerah
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
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        InputWithIcon(
            value = insertUiEvent.nama_tanaman,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_tanaman = it)) },
            label = "Nama Tanaman",
            icon = Icons.Default.Create,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )

        InputWithIcon(
            value = insertUiEvent.id_tanaman,
            onValueChange = { onValueChange(insertUiEvent.copy(id_tanaman = it)) },
            label = "ID Tanaman",
            icon = Icons.Default.Info,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )

        InputWithIcon(
            value = insertUiEvent.periode_tanam,
            onValueChange = { onValueChange(insertUiEvent.copy(periode_tanam = it)) },
            label = "Periode Tanam",
            icon = Icons.Default.DateRange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )

        InputWithIcon(
            value = insertUiEvent.deskripsi_tanaman,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi_tanaman = it)) },
            label = "Deskripsi Tanaman",
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
                tint = Color(0xFF022F1F) // Ikon berwarna kuning cerah
            )
        },
        modifier = modifier
            .background(Color(0xFFE1E2A3), MaterialTheme.shapes.medium)
            .padding(horizontal = 12.dp),
        enabled = enabled,
        singleLine = singleLine,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF022F1F), // Border warna kuning cerah
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
