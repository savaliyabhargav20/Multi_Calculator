package com.example.multicalculator.ui.converter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multicalculator.ui.calculator.components.CalculatorButton
import com.example.multicalculator.ui.calculator.components.CalculatorButtonType

@Composable
fun ConverterKeypad(
    currentValue: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    fun handleInput(key: String) {
        when (key) {
            "C" -> onValueChanged("0")
            "⌫" -> {
                if (currentValue.length <= 1 || (currentValue.length == 2 && currentValue.startsWith("-"))) {
                    onValueChanged("0")
                } else {
                    onValueChanged(currentValue.dropLast(1))
                }
            }
            "±" -> {
                if (currentValue == "0" || currentValue.isBlank()) return
                if (currentValue.startsWith("-")) {
                    onValueChanged(currentValue.removePrefix("-"))
                } else {
                    onValueChanged("-$currentValue")
                }
            }
            "." -> {
                if (!currentValue.contains(".")) {
                    onValueChanged(if (currentValue.isBlank()) "0." else "$currentValue.")
                }
            }
            "00" -> {
                if (currentValue == "0" || currentValue.isBlank()) return
                onValueChanged("${currentValue}00")
            }
            else -> {
                if (currentValue == "0") {
                    onValueChanged(key)
                } else {
                    onValueChanged("$currentValue$key")
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Row 1: 7, 8, 9, ⌫
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "7",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("7") }
            )
            CalculatorButton(
                text = "8",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("8") }
            )
            CalculatorButton(
                text = "9",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("9") }
            )
            CalculatorButton(
                icon = Icons.AutoMirrored.Rounded.Backspace,
                type = CalculatorButtonType.ACTION,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("⌫") }
            )
        }

        // Row 2: 4, 5, 6, C
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "4",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("4") }
            )
            CalculatorButton(
                text = "5",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("5") }
            )
            CalculatorButton(
                text = "6",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("6") }
            )
            CalculatorButton(
                text = "C",
                type = CalculatorButtonType.ACTION,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("C") }
            )
        }

        // Row 3: 1, 2, 3, ±
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "1",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("1") }
            )
            CalculatorButton(
                text = "2",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("2") }
            )
            CalculatorButton(
                text = "3",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("3") }
            )
            CalculatorButton(
                text = "±",
                type = CalculatorButtonType.OPERATOR,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("±") }
            )
        }

        // Row 4: 0, 00, .
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "0",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("0") }
            )
            CalculatorButton(
                text = "00",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(1f),
                onClick = { handleInput("00") }
            )
            CalculatorButton(
                text = ".",
                type = CalculatorButtonType.NUMBER,
                modifier = Modifier.weight(2f),
                onClick = { handleInput(".") }
            )
        }
    }
}
