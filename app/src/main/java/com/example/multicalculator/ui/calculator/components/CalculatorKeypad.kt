package com.example.multicalculator.ui.calculator.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorKeypad(
    isScientificExpanded: Boolean,
    onInput: (String) -> Unit,
    onClear: () -> Unit,
    onDelete: () -> Unit,
    onEvaluate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Expandable Scientific Keypad
        AnimatedVisibility(
            visible = isScientificExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Sci Row 1
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CalculatorButton(
                        text = "sin",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("sin(") }
                    )
                    CalculatorButton(
                        text = "cos",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("cos(") }
                    )
                    CalculatorButton(
                        text = "tan",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("tan(") }
                    )
                    CalculatorButton(
                        text = "log",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("log(") }
                    )
                    CalculatorButton(
                        text = "ln",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("ln(") }
                    )
                }

                // Sci Row 2
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CalculatorButton(
                        text = "asin",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("asin(") }
                    )
                    CalculatorButton(
                        text = "acos",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("acos(") }
                    )
                    CalculatorButton(
                        text = "atan",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("atan(") }
                    )
                    CalculatorButton(
                        text = "√",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("√(") }
                    )
                    CalculatorButton(
                        text = "x^y",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("^") }
                    )
                }

                // Sci Row 3
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CalculatorButton(
                        text = "π",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("π") }
                    )
                    CalculatorButton(
                        text = "e",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("e") }
                    )
                    CalculatorButton(
                        text = "x!",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("!") }
                    )
                    CalculatorButton(
                        text = "1/x",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("1/(") }
                    )
                    CalculatorButton(
                        text = "|x|",
                        type = CalculatorButtonType.SCIENTIFIC,
                        modifier = Modifier.weight(1f),
                        onClick = { onInput("abs(") }
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        // Standard Keypad Grid
        // Row 1: AC, ⌫, %, ÷
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "AC",
                type = CalculatorButtonType.ACTION,
                modifier = Modifier.weight(1f),
                onClick = onClear
            )
            CalculatorButton(
                icon = Icons.AutoMirrored.Rounded.Backspace,
                type = CalculatorButtonType.ACTION,
                modifier = Modifier.weight(1f),
                onClick = onDelete
            )
            CalculatorButton(
                text = "%",
                type = CalculatorButtonType.ACTION,
                modifier = Modifier.weight(1f),
                onClick = { onInput("%") }
            )
            CalculatorButton(
                text = "÷",
                type = CalculatorButtonType.OPERATOR,
                modifier = Modifier.weight(1f),
                onClick = { onInput("÷") }
            )
        }

        // Row 2: 7, 8, 9, ×
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "7",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { onInput("7") }
            )
            CalculatorButton(
                text = "8",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { onInput("8") }
            )
            CalculatorButton(
                text = "9",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { onInput("9") }
            )
            CalculatorButton(
                text = "×",
                type = CalculatorButtonType.OPERATOR,
                modifier = Modifier.weight(1f),
                onClick = { onInput("×") }
            )
        }

        // Row 3: 4, 5, 6, -
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "4",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { onInput("4") }
            )
            CalculatorButton(
                text = "5",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { onInput("5") }
            )
            CalculatorButton(
                text = "6",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { onInput("6") }
            )
            CalculatorButton(
                text = "−",
                type = CalculatorButtonType.OPERATOR,
                modifier = Modifier.weight(1f),
                onClick = { onInput("−") }
            )
        }

        // Row 4: 1, 2, 3, +
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "1",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { onInput("1") }
            )
            CalculatorButton(
                text = "2",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { onInput("2") }
            )
            CalculatorButton(
                text = "3",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { onInput("3") }
            )
            CalculatorButton(
                text = "+",
                type = CalculatorButtonType.OPERATOR,
                modifier = Modifier.weight(1f),
                onClick = { onInput("+") }
            )
        }

        // Row 5: ( ), 0, ., =
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "( )",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = {
                    // Smart parenthesis: if last char or balanced open count
                    onInput("(")
                }
            )
            CalculatorButton(
                text = "0",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { onInput("0") }
            )
            CalculatorButton(
                text = ".",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { onInput(".") }
            )
            CalculatorButton(
                text = "=",
                type = CalculatorButtonType.EQUALS,
                modifier = Modifier.weight(1f),
                onClick = onEvaluate
            )
        }
    }
}
