package com.example.multicalculator.ui.calculator.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Functions
import androidx.compose.material.icons.rounded.History
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multicalculator.domain.calculator.AngleUnit

@Composable
fun CalculatorDisplay(
    expression: String,
    result: String,
    angleUnit: AngleUnit,
    isScientificExpanded: Boolean,
    historyCount: Int,
    errorMessage: String?,
    onToggleAngleUnit: () -> Unit,
    onToggleScientific: () -> Unit,
    onOpenHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val exprScrollState = rememberScrollState()

    // Auto-scroll expression text to the end when typing
    LaunchedEffect(expression) {
        exprScrollState.animateScrollTo(exprScrollState.maxValue)
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header bar with DEG/RAD toggle, Scientific mode toggle, and History button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // DEG / RAD Pill button
                    AssistChip(
                        onClick = onToggleAngleUnit,
                        label = {
                            Text(
                                text = angleUnit.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        shape = RoundedCornerShape(16.dp),
                        border = null
                    )

                    // Scientific Mode Toggle Button
                    IconButton(
                        onClick = onToggleScientific,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (isScientificExpanded)
                                MaterialTheme.colorScheme.primaryContainer
                            else
                                MaterialTheme.colorScheme.surfaceContainerHigh,
                            contentColor = if (isScientificExpanded)
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(
                            imageVector = if (isScientificExpanded) Icons.Rounded.Functions else Icons.Rounded.Calculate,
                            contentDescription = "Toggle Scientific Panel"
                        )
                    }
                }

                // History Button with Badge
                IconButton(
                    onClick = onOpenHistory,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    BadgedBox(
                        badge = {
                            if (historyCount > 0) {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ) {
                                    Text(text = if (historyCount > 99) "99+" else historyCount.toString())
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.History,
                            contentDescription = "View Calculation History"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Expression Text (Scrollable horizontally if long)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(exprScrollState),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expression.isBlank()) "0" else expression,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = if (expression.length > 20) 22.sp else 28.sp,
                        color = if (expression.isBlank())
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        else
                            MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Result Preview or Error Message
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                AnimatedVisibility(
                    visible = errorMessage != null,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Text(
                        text = errorMessage ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (errorMessage == null) {
                    Text(
                        text = if (result.isNotBlank()) "= $result" else "",
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
