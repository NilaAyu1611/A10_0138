package com.example.uaspam.ui.customwidget

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    canNavigateBack: Boolean, // Menentukan apakah ikon navigasi kembali akan ditampilkan.
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null, // Mendukung animasi saat toolbar di-scroll.
    navigateUp: () -> Unit = {}, // Fungsi yang dipanggil saat tombol navigasi kembali ditekan.
    onRefresh: () -> Unit = {} // Fungsi yang dipanggil saat tombol refresh ditekan.
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground, // Menggunakan warna teks sesuai tema
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold // Menambahkan ketebalan font agar lebih elegan
                ),
                maxLines = 1, // Membatasi teks pada satu baris
                overflow = TextOverflow.Ellipsis, // Tambahkan "..." jika teks terlalu panjang
                modifier = Modifier.padding(horizontal = 20.dp) // Menambahkan padding horizontal agar lebih simetris
            )
        },
        actions = {
            // Menambahkan ikon refresh di toolbar dengan desain halus
            IconButton(onClick = { onRefresh() }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f) // Warna ikon lebih soft dengan transparansi
                )
            }
        },
        modifier = modifier.height(72.dp), // Menambahkan sedikit tinggi untuk kenyamanan visual
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Kembali",
                        tint = MaterialTheme.colorScheme.primary // Warna ikon sesuai tema
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFFc8e6c9), // Menambahkan warna background yang ditentukan
            scrolledContainerColor = MaterialTheme.colorScheme.surface // Warna saat di-scroll
        )
    )
}
