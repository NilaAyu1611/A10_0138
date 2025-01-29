package com.example.uaspam.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uaspam.ui.home.Cover
import com.example.uaspam.ui.home.DestinasiHalamanCover
import com.example.uaspam.ui.home.DestinasiHalamanUtama
import com.example.uaspam.ui.home.PertanianApp
import com.example.uaspam.ui.home.view.tanaman.DestinasiEntry
import com.example.uaspam.ui.home.view.tanaman.DestinasiHomeTanaman
import com.example.uaspam.ui.home.view.tanaman.DetailTanamanView
import com.example.uaspam.ui.home.view.tanaman.EntryTnmView
import com.example.uaspam.ui.home.view.tanaman.HomeTanamanView
import com.example.uaspam.ui.home.view.tanaman.UpdateTanamanView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    // Mengatur navigasi antar layar menggunakan NavHost
    NavHost(
        navController = navController, // Controller navigasi
        startDestination = DestinasiHalamanCover.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHalamanCover.route) {
            Cover(
                navController = navController, // Pass the navController to HomeView

            )
        }

        composable(DestinasiHalamanUtama.route) {
            PertanianApp(
                navController = navController, // Pass the navController to HomeView

            )
        }

        // Halaman Home
        composable(DestinasiHomeTanaman.route) {
            HomeTanamanView(
                navigateBack = {
                    navController.navigate(DestinasiHalamanUtama.route) {
                        popUpTo(DestinasiHalamanUtama.route) { inclusive = true }
                    }
                },
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { id_tanaman ->
                    navController.navigate("detail/$id_tanaman")
                }
            )
        }


        // Halaman EntryMhs
        composable(DestinasiEntry.route) {
            EntryTnmView(navigateBack = {
                navController.navigate(DestinasiHomeTanaman.route) { // Navigasi kembali ke HomeScreen
                    popUpTo(DestinasiHomeTanaman.route) {
                        inclusive = true // Bersihkan layar sebelumnya
                    }
                }
            })
        }

        // Halaman UpdateMhs
        composable(
            route = "update/{id_tanaman}",
            arguments = listOf(navArgument("id_tanaman") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_tanaman = backStackEntry.arguments?.getString("id_tanaman") ?: ""
            UpdateTanamanView(
                id_tanaman = id_tanaman,
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }

        // Menambahkan navigasi dari DetailTanamanView
        composable(
            route = "detail/{id_tanaman}",
            arguments = listOf(navArgument("id_tanaman") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_tanaman = backStackEntry.arguments?.getString("id_tanaman") ?: ""
            DetailTanamanView(
                id_tanaman = id_tanaman,
                navigateBack = {
                    navController.popBackStack()
                },
                navigateEdit = { editId_tanaman ->
                    navController.navigate("update/$editId_tanaman")
                },
                navigateToInsertPanen = {
                    navController.navigate("${DestinasiInsertPanen.route}/$id_tanaman")
                }
            )
        }