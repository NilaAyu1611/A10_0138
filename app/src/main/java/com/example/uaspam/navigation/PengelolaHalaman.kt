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
import com.example.uaspam.ui.home.view.aktivitaspertanian.DestinasiHomeAktivitasPertanian
import com.example.uaspam.ui.home.view.aktivitaspertanian.DestinasiInsertAktivitasPertanian
import com.example.uaspam.ui.home.view.aktivitaspertanian.DetailAktivitasPertanianView
import com.example.uaspam.ui.home.view.aktivitaspertanian.EntryAkView
import com.example.uaspam.ui.home.view.aktivitaspertanian.HomeAktivitasPertanianView
import com.example.uaspam.ui.home.view.aktivitaspertanian.UpdateAktivitasPertanianView
import com.example.uaspam.ui.home.view.panen.DestinasiHomePanen
import com.example.uaspam.ui.home.view.panen.DestinasiInsertPanen
import com.example.uaspam.ui.home.view.panen.EntryPnView
import com.example.uaspam.ui.home.view.panen.HomePanenView
import com.example.uaspam.ui.home.view.panen.UpdatePanenView
import com.example.uaspam.ui.home.view.pekerja.DestinasiHomePekerja
import com.example.uaspam.ui.home.view.pekerja.DestinasiInsertPekerja
import com.example.uaspam.ui.home.view.pekerja.DetailPekerjaView
import com.example.uaspam.ui.home.view.pekerja.EntryPkjView
import com.example.uaspam.ui.home.view.pekerja.HomePekerjaView
import com.example.uaspam.ui.home.view.pekerja.UpdatePekerjaView
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


        //----------------------------------------------------------------------------------------------------------------------------------------------

        // Halaman Home Pekerja
        composable(DestinasiHomePekerja.route) {
            HomePekerjaView(
                navigateBack = {
                    navController.navigate(DestinasiHalamanUtama.route) {
                        popUpTo(DestinasiHalamanUtama.route) { inclusive = true }
                    }
                },
                navigateToItemEntry = { navController.navigate(DestinasiInsertPekerja.route) }, // Navigasi ke EntryMhsScreen
                onDetailClick = { id_pekerja ->
                    navController.navigate("pekerja_detail/$id_pekerja") // Navigasi ke halaman detail dengan
                }
            )
        }

        // Halaman Insert Pekerja
        composable(DestinasiInsertPekerja.route) {
            EntryPkjView(navigateBack = {
                navController.navigate(DestinasiHomePekerja.route) { // Navigasi kembali ke HomeScreen
                    popUpTo(DestinasiHomePekerja.route) {
                        inclusive = true // Bersihkan layar sebelumnya
                    }
                }
            })
        }

        // Halaman Update pkj
        composable(
            route = "update_pekerja/{id_pekerja}",
            arguments = listOf(navArgument("id_pekerja") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_pekerja = backStackEntry.arguments?.getString("id_pekerja") ?: ""
            UpdatePekerjaView(
                id_pekerja = id_pekerja,
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }

        // Halaman Detailpkj
        composable(
            route = "pekerja_detail/{id_pekerja}",
            arguments = listOf(navArgument("id_pekerja") { type = NavType.StringType }) // Definisikan parameter ""
        ) { backStackEntry ->
            val id_pekerja = backStackEntry.arguments?.getString("id_pekerja") ?: "" // Ambil nilai dari parameter
            DetailPekerjaView(
                id_pekerja = id_pekerja,
                navigateEditPekerja = { editId_pekerja ->
                    navController.navigate("update_pekerja/$editId_pekerja")                   // Navigasi ke halaman update dengan
                },
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }




        //----------------------------------------------------------------------------------------------------------------------------


        // Halaman Home Panen
        composable(DestinasiHomePanen.route) {
            HomePanenView(
                navigateBack = {
                    navController.navigate(DestinasiHalamanUtama.route) {
                        popUpTo(DestinasiHalamanUtama.route) { inclusive = true }
                    }
                },
                onEditClick = { id_panen -> // Pastikan parameter ini diteruskan
                    navController.navigate("update_panen/$id_panen")
                }


            )
        }

        // Halaman Insert Panen
        composable(
            route = "${DestinasiInsertPanen.route}/{id_tanaman}",
            arguments = listOf(navArgument("id_tanaman") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_tanaman = backStackEntry.arguments?.getString("id_tanaman") ?: ""
            EntryPnView(
                id_tanaman = id_tanaman,
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }


        // Halaman Update
        composable(
            route = "update_panen/{id_panen}",
            arguments = listOf(navArgument("id_panen") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_panen = backStackEntry.arguments?.getString("id_panen") ?: ""
            UpdatePanenView(
                id_panen = id_panen,
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }


        //----------------------------------------------------------------------------------------------------------------------------------------------

        // Halaman Home
        composable(DestinasiHomeAktivitasPertanian.route) {
            HomeAktivitasPertanianView(
                navigateBack = {
                    navController.navigate(DestinasiHalamanUtama.route) {
                        popUpTo(DestinasiHalamanUtama.route) { inclusive = true }
                    }
                },

                navigateToItemEntry = { navController.navigate(DestinasiInsertAktivitasPertanian.route) }, // Navigasi ke EntryMhsScreen
                onDetailClick = { id_aktivitas ->
                    navController.navigate("detail_aktivitas/$id_aktivitas") // Navigasi ke halaman detail dengan
                }
            )
        }

        // Halaman Insert
        composable(DestinasiInsertAktivitasPertanian.route) {
            EntryAkView(navigateBack = {
                navController.navigate(DestinasiHomeAktivitasPertanian.route) { // Navigasi kembali ke HomeScreen
                    popUpTo(DestinasiHomeAktivitasPertanian.route) {
                        inclusive = true // Bersihkan layar sebelumnya
                    }
                }
            })
        }

        // Halaman Update aktivitas
        composable(
            route = "update_aktivitas/{id_aktivitas}",
            arguments = listOf(navArgument("id_aktivitas") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_aktivitas = backStackEntry.arguments?.getString("id_aktivitas") ?: ""
            UpdateAktivitasPertanianView(
                id_aktivitas = id_aktivitas,
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }

        // Halaman Detail aktivitas
        composable(
            route = "detail_aktivitas/{id_aktivitas}",
            arguments = listOf(navArgument("id_aktivitas") { type = NavType.StringType }) // Definisikan parameter ""
        ) { backStackEntry ->
            val id_aktivitas = backStackEntry.arguments?.getString("id_aktivitas") ?: "" // Ambil nilai dari parameter
            DetailAktivitasPertanianView(
                id_aktivitas = id_aktivitas,
                navigateEditAktivitas = { editId_aktivitas ->
                    navController.navigate("update_aktivitas/$editId_aktivitas")                   // Navigasi ke halaman update dengan
                },
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }


    }
}