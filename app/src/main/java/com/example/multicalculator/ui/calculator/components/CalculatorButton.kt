package com.example.multicalculator.ui.calculator.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class CalculatorButtonType {
    NUMBER,
    OPERATOR,
    ACTION,
    SCIENTIFIC,
    EQUALS
}

@Composable
fun CalculatorButton(
    text: String? = null,
    icon: ImageVector? = null,
    type: CalculatorButtonType = CalculatorButtonType.NUMBER,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    contentPadding: Dp = 4.dp,
    isHighlighted: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1.0f,
        animationSpec = tween(durationMillis = 100),
        label = "button_scale"
    )

    val (defaultBgColor, defaultTextColor) = when (type) {
        CalculatorButtonType.NUMBER -> Pair(
            MaterialTheme.colorScheme.surfaceContainerHigh,
            MaterialTheme.colorScheme.onSurface
        )
        CalculatorButtonType.OPERATOR -> Pair(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer
        )
        CalculatorButtonType.ACTION -> Pair(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.onTertiaryContainer
        )
        CalculatorButtonType.SCIENTIFIC -> Pair(
            if (isHighlighted) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainerHighest,
            if (isHighlighted) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.primary
        )
        CalculatorButtonType.EQUALS -> Pair(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    val animatedBgColor by animateColorAsState(
        targetValue = if (isPressed) defaultBgColor.copy(alpha = 0.75f) else defaultBgColor,
        animationSpec = tween(durationMillis = 100),
        label = "button_bg_color"
    )

    Surface(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onClick()
        },
        modifier = modifier
            .padding(contentPadding)
            .scale(scale),
        shape = shape,
        color = animatedBgColor,
        contentColor = defaultTextColor,
        interactionSource = interactionSource,
        shadowElevation = if (type == CalculatorButtonType.EQUALS) 4.dp else 1.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (text != null) {
                Text(
                    text = text,
                    fontSize = when {
                        type == CalculatorButtonType.SCIENTIFIC -> 15.sp
                        type == CalculatorButtonType.EQUALS -> 26.sp
                        type == CalculatorButtonType.OPERATOR -> 24.sp
                        text.length > 3 -> 14.sp
                        text.length > 2 -> 16.sp
                        else -> 22.sp
                    },
                    fontWeight = when (type) {
                        CalculatorButtonType.NUMBER -> FontWeight.Medium
                        CalculatorButtonType.OPERATOR -> FontWeight.Bold
                        CalculatorButtonType.EQUALS -> FontWeight.ExtraBold
                        CalculatorButtonType.ACTION -> FontWeight.Bold
                        CalculatorButtonType.SCIENTIFIC -> FontWeight.SemiBold
                    },
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            } else if (icon != null) {
                androidx.compose.material3.Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = defaultTextColor
                )
            }
        }
    }
}
