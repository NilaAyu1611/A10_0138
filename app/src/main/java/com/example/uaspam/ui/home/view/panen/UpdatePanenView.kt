package com.example.uaspam.ui.home.view.panen

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
import com.example.uaspam.ui.home.viewmodel.panen.UpdatePanenViewModel
import com.example.uaspam.ui.home.viewmodel.panen.UpdatepaUiEvent
import com.example.uaspam.ui.home.viewmodel.panen.UpdatepaUiState

import kotlinx.coroutines.launch

object DestinasiUpdatePanen : DestinasiNavigasi {
    override val route = "update_view"
    override val titleRes = "Update Catatan Panen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePanenView(
    id_panen: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_panen) {
        viewModel.getPanenById(id_panen)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdatePanen.titleRes,
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
            UpdateBodyPanen(
                updatepaUiState = viewModel.updatepaUiState,
                onPanenValueChange = viewModel::updatePnState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updatePn()
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
fun UpdateBodyPanen(
    updatepaUiState: UpdatepaUiState,
    onPanenValueChange: (UpdatepaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInputPanen(
            updatepaUiEvent = updatepaUiState.updatepaUiEvent,
            onValueChange = onPanenValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8C9A67),
                contentColor = Color.White
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
fun FormInputPanen(
    updatepaUiEvent: UpdatepaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatepaUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val borderColor = Color.White
        val iconColor = Color.White

        OutlinedTextField(
            value = updatepaUiEvent.id_panen,
            onValueChange = { onValueChange(updatepaUiEvent.copy(id_panen = it)) },
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
            value = updatepaUiEvent.tanggal_panen,
            onValueChange = { onValueChange(updatepaUiEvent.copy(tanggal_panen = it)) },
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
            value = updatepaUiEvent.jumlah_panen,
            onValueChange = { onValueChange(updatepaUiEvent.copy(jumlah_panen = it)) },
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
            value = updatepaUiEvent.keterangan,
            onValueChange = { onValueChange(updatepaUiEvent.copy(keterangan = it)) },
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

    }
}
