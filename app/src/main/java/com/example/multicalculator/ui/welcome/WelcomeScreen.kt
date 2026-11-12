package com.example.multicalculator.ui.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF040615),
                        Color(0xFF0A1138),
                        Color(0xFF140D36)
                    )
                )
            )
            .clickable { onGetStarted() }
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Section: Icon badge & Title
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Top Icon Badge
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF2A3990), Color(0xFF1A1A40))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Calculate,
                        contentDescription = null,
                        tint = Color(0xFF4DD8EC),
                        modifier = Modifier.size(40.dp)
                    )
                }

                // Title & Subtitle
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Multi Calculator",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "All Calculations in One App",
                        fontSize = 16.sp,
                        color = Color(0xFF97F0FF),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Central Visual Art / Feature Representation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Background glow / ring effect
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF2D2BB8).copy(alpha = 0.35f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Central floating calculator card representation
                Card(
                    modifier = Modifier.size(220.dp, 240.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1B2347)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Calculator screen preview
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF0E132D)),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                text = "0",
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        }

                        // Keypad grid preview
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val rows = listOf(
                                listOf("7", "8", "9"),
                                listOf("4", "5", "6"),
                                listOf("1", "2", "3"),
                                listOf("0", ".", "=")
                            )
                            rows.forEach { row ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    row.forEach { key ->
                                        val isEquals = key == "="
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(32.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(
                                                    if (isEquals) Color(0xFFFF9800) else Color(0xFF2C3868)
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = key,
                                                color = Color.White,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Bottom Section: Feature Icons, Tagline, and Dots
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Feature Icons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FeatureItem(icon = Icons.Rounded.Calculate, label = "Standard")
                    FeatureItem(icon = Icons.Rounded.Percent, label = "Percentage")
                    FeatureItem(icon = Icons.Rounded.SquareFoot, label = "Square Root")
                    FeatureItem(icon = Icons.Rounded.Functions, label = "Scientific")
                    FeatureItem(icon = Icons.Rounded.Balance, label = "And More...")
                }

                // Tagline
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Calculate Smarter.",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Do More.",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC6C0FF)
                    )
                }

                // Pagination Dots & Continue Button / Prompt
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.4f))
                    )
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4DD8EC))
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.4f))
                    )
                }

                Text(
                    text = "Tap anywhere to start",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun FeatureItem(icon: ImageVector, label: String) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF222B5E), Color(0xFF14193B))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF4DD8EC),
                modifier = Modifier.size(26.dp)
            )
        }
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.85f),
            maxLines = 1
        )
    }
}
