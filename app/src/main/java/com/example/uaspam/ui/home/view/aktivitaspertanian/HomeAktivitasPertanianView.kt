package com.example.uaspam.ui.home.view.aktivitaspertanian

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.model.AktivitasPertanian
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.customwidget.CustomTopAppBar
import com.example.uaspam.ui.home.PenyediaViewModel
import com.example.uaspam.ui.home.view.panen.OnError
import com.example.uaspam.ui.home.view.panen.OnLoading
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.HomeAktivitasPertanianViewModel
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.HomeakUiState


object DestinasiHomeAktivitasPertanian: DestinasiNavigasi {
    override val route= "home pekerja"
    override val titleRes= "Daftar Aktivitas Pertanian"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAktivitasPertanianView(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeAktivitasPertanianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier,
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeAktivitasPertanian.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getAk()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add Aktivitas")
            }
        },
        containerColor = Color(0xFFf4f4f4) // Light background for elegance
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF826B0D), Color(0xFF022F1F), Color(0xFF909B00))
                    )
                ) // Gradient background
        ) {
            HomeStatusAktivitasPertanian(
                homeakUiState = viewModel.akUiState,
                retryAction = { viewModel.getAk() },
                modifier = Modifier.fillMaxSize(),
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.deleteAk(it.id_aktivitas)
                    viewModel.getAk()
                }
            )
        }
    }
}

@Composable
fun HomeStatusAktivitasPertanian(
    homeakUiState: HomeakUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (AktivitasPertanian) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeakUiState) {
        is HomeakUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeakUiState.Success -> {
            if (homeakUiState.aktivitasPertanian.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada data Aktivitas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                AkLayout(
                    aktivitasPertanian = homeakUiState.aktivitasPertanian,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_aktivitas) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeakUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun AkLayout(
    aktivitasPertanian: List<AktivitasPertanian>,
    modifier: Modifier = Modifier,
    onDetailClick: (AktivitasPertanian) -> Unit,
    onDeleteClick: (AktivitasPertanian) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(aktivitasPertanian) { ak ->
            AkCard(
                aktivitasPertanian = ak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(ak) },
                onDeleteClick = { onDeleteClick(ak) }
            )
        }
    }
}

@Composable
fun AkCard(
    aktivitasPertanian: AktivitasPertanian,
    modifier: Modifier = Modifier,
    onDeleteClick: (AktivitasPertanian) -> Unit = {}
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = Color(0x80587156), // Semi-transparent background
        tonalElevation = 8.dp // Surface elevation
    ) {
        Column(
            modifier = Modifier.padding(12.dp), // Adjust padding
            verticalArrangement = Arrangement.spacedBy(3.dp) // Adjust space between elements
        ) {
            Text(
                text = aktivitasPertanian.id_aktivitas,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = FontFamily.Serif, // Elegant serif font
                    fontSize = 20.sp // Adjusted font size
                ),
                color = Color.White
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "ID Aktivitas",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = aktivitasPertanian.id_tanaman,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    ),
                    color = Color.White
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(aktivitasPertanian) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Aktivitas",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "ID Pekerja",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = aktivitasPertanian.id_pekerja,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    ),
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Tanggal Aktivitas",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = aktivitasPertanian.tanggal_aktivitas,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    ),
                    color = Color.White
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Deskripsi Aktivitas",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = aktivitasPertanian.deskripsi_aktivitas,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    ),
                    color = Color.White
                )
            }
        }
    }
}
