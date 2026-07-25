package com.mediaflix.app.ui.screens

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.mediaflix.app.viewmodel.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfReaderScreen(
    navController: NavHostController,
    mediaId: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val pdfUrl by viewModel.pdfUrl.collectAsState()
    val details by viewModel.details.collectAsState()
    val scope = rememberCoroutineScope()

    var localPdfPath by remember { mutableStateOf<String?>(null) }
    var currentPage by remember { mutableIntStateOf(0) }
    var totalPages by remember { mutableIntStateOf(0) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(mediaId) { viewModel.loadDetails(mediaId) }

    LaunchedEffect(pdfUrl) {
        if (pdfUrl == null) return@LaunchedEffect
        isLoading = true;
        error = null
        try {
            withContext(Dispatchers.IO) {
                val client = OkHttpClient()
                val request = Request.Builder().url(pdfUrl).build()
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) throw Exception("Failed to download: ${response.code}")
                val file = File.createTempFile("mediaflix_pdf_", ".pdf")
                response.body?.byteStream()?.use { input ->
                    FileOutputStream(file).use { output -> input.copyTo(output) }
                }
                localPdfPath = file.absolutePath
            }
        } catch (e: Exception) {
            error = e.message ?: "خطا در دانلود PDF"
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF1A1A1A))) {
        Row(
            modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, "بازگشت", tint = Color.White)
            }
            Text(details?.title ?: "PDF Reader", color = Color.White, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f).padding(start = 8.dp))
            if (totalPages > 0) { Text("${currentPage + 1} / $totalPages", color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.bodyMedium) }
        }

        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color.White)
                    Spacer(Modifier.height(16.dp))
                    Text("در حال بارܯیری PDF...", color = Color.White.copy(alpha = 0.7f))
                }
            }
            error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("❌", fontSize = MaterialTheme.typography.headlineLarge.fontSize)
                    Spacer(Modifier.height(12.dp))
                    Text(error ?: "خطا", color = Color.Red)
                }
            }
            pdfUrl == null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("لینک PDF تنظیم نشده است", color = Color.White.copy(alpha = 0.7f))
            }
            localPdfPath != null -> AndroidView(
                factory = { ctx ->
                    PDFView(ctx, null).apply {
                        fromFile(File(localPdfPath))
                            .enableSwipe(true)
                            .enableDoubletap(true)
                            .defaultPage(0)
                            .onPageChange(object : OnPageChangeListener {
                                override fun onPageChanged(page: Int, pageCount: Int) {
                                    currentPage = page
                                    totalPages = pageCount
                                }
                            })
                            .onLoad(object : OnLoadCompleteListener {
                                override fun loadComplete(nbPages: Int) {
                                    totalPages = nbPages
                                }
                            })
                            .load()
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}