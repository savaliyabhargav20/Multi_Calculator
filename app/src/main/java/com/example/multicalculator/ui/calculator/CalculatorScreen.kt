package com.example.multicalculator.ui.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.multicalculator.domain.calculator.AngleUnit
import com.example.multicalculator.domain.calculator.CalculationHistoryItem
import com.example.multicalculator.ui.calculator.components.CalculatorDisplay
import com.example.multicalculator.ui.calculator.components.CalculatorKeypad
import com.example.multicalculator.ui.calculator.components.HistoryBottomSheet
import com.example.multicalculator.ui.theme.MultiCalculatorTheme

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    CalculatorScreenContent(
        uiState = uiState,
        onInput = viewModel::onInput,
        onClear = viewModel::onClear,
        onDelete = viewModel::onDelete,
        onEvaluate = viewModel::onEvaluate,
        onToggleAngleUnit = viewModel::toggleAngleUnit,
        onToggleScientific = viewModel::toggleScientificExpanded,
        onSelectItem = viewModel::selectHistoryItem,
        onClearHistory = viewModel::clearHistory,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreenContent(
    uiState: CalculatorUiState,
    onInput: (String) -> Unit,
    onClear: () -> Unit,
    onDelete: () -> Unit,
    onEvaluate: () -> Unit,
    onToggleAngleUnit: () -> Unit,
    onToggleScientific: () -> Unit,
    onSelectItem: (CalculationHistoryItem) -> Unit,
    onClearHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showHistorySheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Display Area
            CalculatorDisplay(
                expression = uiState.expression,
                result = uiState.result,
                angleUnit = uiState.angleUnit,
                isScientificExpanded = uiState.isScientificExpanded,
                historyCount = uiState.history.size,
                errorMessage = uiState.errorMessage,
                onToggleAngleUnit = onToggleAngleUnit,
                onToggleScientific = onToggleScientific,
                onOpenHistory = { showHistorySheet = true },
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )

            // Keypad Section
            CalculatorKeypad(
                isScientificExpanded = uiState.isScientificExpanded,
                onInput = onInput,
                onClear = onClear,
                onDelete = onDelete,
                onEvaluate = onEvaluate,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (showHistorySheet) {
        HistoryBottomSheet(
            history = uiState.history,
            sheetState = sheetState,
            onDismissRequest = { showHistorySheet = false },
            onSelectItem = onSelectItem,
            onClearHistory = onClearHistory
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreviewLight() {
    MultiCalculatorTheme(darkTheme = false) {
        CalculatorScreenContent(
            uiState = CalculatorUiState(
                expression = "sin(45) + 12.5 × 8",
                result = "100.7071",
                angleUnit = AngleUnit.DEGREE,
                isScientificExpanded = true,
                history = listOf(
                    CalculationHistoryItem(expression = "50 * 2", result = "100"),
                    CalculationHistoryItem(expression = "√144", result = "12")
                )
            ),
            onInput = {},
            onClear = {},
            onDelete = {},
            onEvaluate = {},
            onToggleAngleUnit = {},
            onToggleScientific = {},
            onSelectItem = {},
            onClearHistory = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreviewDark() {
    MultiCalculatorTheme(darkTheme = true) {
        CalculatorScreenContent(
            uiState = CalculatorUiState(
                expression = "150 % 20",
                result = "10",
                angleUnit = AngleUnit.DEGREE,
                isScientificExpanded = false
            ),
            onInput = {},
            onClear = {},
            onDelete = {},
            onEvaluate = {},
            onToggleAngleUnit = {},
            onToggleScientific = {},
            onSelectItem = {},
            onClearHistory = {}
        )
    }
}
