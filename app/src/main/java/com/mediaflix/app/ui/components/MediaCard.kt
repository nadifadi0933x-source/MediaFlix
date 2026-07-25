package com.mediaflix.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mediaflix.app.data.model.MediaItem
import com.mediaflix.app.data.model.MediaType

@Composable
fun MediaCard(item: MediaItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.width(130.dp).clickable(onClick = onClick)) {
        Box(modifier = Modifier.height(190.dp).width(130.dp)) {
            if (item.posterUrl != null) {
                AsyncImage(model = item.posterUrl, contentDescription = item.title, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)))
            } else {
                Box(modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant), contentAlignment = Alignment.Center) { Text("🎬", fontSize = 32.sp) }
            }
            if (item.rating != null) {
                Surface(modifier = Modifier.align(Alignment.TopEnd).padding(6.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f), shape = RoundedCornerShape(6.dp)) {
                    Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = String.format("%.1f", item.rating), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Icon(Icons.Filled.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(10.dp))
                    }
                }
            }
            val typeLabel = when (item.type) {
                MediaType.ANIME -> "انیمه"
                MediaType.MANGA -> "مانگا"
                MediaType.MANHWA -> "مانهوا"
                MediaType.MOVIE -> "فیلم"
                MediaType.SERIES -> "سریال" }
            Surface(modifier = Modifier.align(Alignment.BottomStart).padding(6.dp), color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f), shape = RoundedCornerShape(4.dp)) {
                Text(text = typeLabel, color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)) } }
        Text(text = item.title, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 6.dp)) } }

@Composable
fun MediaCardLarge(item: MediaItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.width(280.dp).height(160.dp).clip(RoundedCornerShape(16.dp)).clickable(onClick = onClick)) {
        val imageUrl = item.backdropUrl ?: item.posterUrl
        if (imageUrl != null) {
            AsyncImage(model = imageUrl, contentDescription = item.title, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f)))))
        } else { Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant)) }
        Column(modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)) {
            Text(text = item.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
            if (item.rating != null) {
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(14.dp))
                    Text(" ${String.format("%.1f", item.rating)}", color = Color.White, fontSize = 13.sp) } } }
        Icon(Icons.Filled.PlayArrow, "پخش", tint = Color.White, modifier = Modifier.align(Alignment.Center).size(48.dp).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f), RoundedCornerShape(50)).padding(6.dp)) } }