package com.mediaflix.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mediaflix.app.data.model.MediaItem
import com.mediaflix.app.ui.components.MediaCard
import com.mediaflix.app.ui.components.MediaCardLarge
import com.mediaflix.app.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val trending by viewModel.trending.collectAsState()
    val popularMovies by viewModel.popularMovies.collectAsState()
    val popularSeries by viewModel.popularSeries.collectAsState()
    val trendingAnime by viewModel.trendingAnime.collectAsState()
    val popularManhwa by viewModel.popularManhwa.collectAsState()
    val currentSeasonAnime by viewModel.currentSeasonAnime.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "MediaFlix",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp, 24.dp, 16.dp, 8.dp)
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Column
        }

        if (trending.isNotEmpty()) {
            SectionHeader("پرطرفدار 🔥", Icons.Filled.Whatshot)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(trending.take(10)) { item ->
                    MediaCardLarge(
                        item = item,
                        onClick = { navController.navigate("detail/${item.id}") }
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
        }

        if (popularMovies.isNotEmpty()) {
            SectionHeader("فیلم‌های متبوب 🎬")
            HorizontalMediaList(popularMovies.take(15), navController)
        }

        if (popularSeries.isNotEmpty()) {
            SectionHeader("سریال‌های متبوب 📺")
            HorizontalMediaList(popularSeries.take(15), navController)
        }

        if (trendingAnime.isNotEmpty()) {
            SectionHeader("انیمه‌های پرطرفدار ⚡")
            HorizontalMediaList(trendingAnime.take(15), navController)
        }

        if (currentSeasonAnime.isNotEmpty()) {
            SectionHeader("فصل جاری 🆕")
            HorizontalMediaList(currentSeasonAnime.take(15), navController)
        }

        if (popularManhwa.isNotEmpty()) {
            SectionHeader("مانهواهای محبوب 📖")
            HorizontalMediaList(popularManhwa.take(15), navController)
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun SectionHeader(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun HorizontalMediaList(items: List<MediaItem>, navController: NavHostController) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items) { item ->
            MediaCard(
                item = item,
                onClick = { navController.navigate("detail/${item.id}") }
            )
        }
    }
    Spacer(Modifier.height(8.dp))
}