package com.example.multicalculator.ui.converter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.multicalculator.domain.converter.ConverterCategory
import com.example.multicalculator.domain.converter.UnitItem
import com.example.multicalculator.ui.converter.components.CategorySelector
import com.example.multicalculator.ui.converter.components.ConverterKeypad
import com.example.multicalculator.ui.converter.components.SwapUnitsButton
import com.example.multicalculator.ui.converter.components.UnitCard
import com.example.multicalculator.ui.theme.MultiCalculatorTheme

@Composable
fun ConverterScreen(
    viewModel: ConverterViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    ConverterScreenContent(
        uiState = uiState,
        onCategorySelected = viewModel::onCategorySelected,
        onFromUnitSelected = viewModel::onFromUnitSelected,
        onToUnitSelected = viewModel::onToUnitSelected,
        onInputValueChanged = viewModel::onInputValueChanged,
        onSwapUnits = viewModel::onSwapUnits,
        onRefreshCurrency = viewModel::refreshCurrencyRates,
        modifier = modifier
    )
}

@Composable
fun ConverterScreenContent(
    uiState: ConverterUiState,
    onCategorySelected: (ConverterCategory) -> Unit,
    onFromUnitSelected: (UnitItem) -> Unit,
    onToUnitSelected: (UnitItem) -> Unit,
    onInputValueChanged: (String) -> Unit,
    onSwapUnits: () -> Unit,
    onRefreshCurrency: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Category Header Selector
                CategorySelector(
                    selectedCategory = uiState.selectedCategory,
                    onCategorySelected = onCategorySelected,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Cards & Swap Layout
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // From Unit Card
                    UnitCard(
                        label = "From",
                        value = uiState.inputValue,
                        selectedUnit = uiState.fromUnit,
                        availableUnits = uiState.availableUnits,
                        isInput = true,
                        onUnitSelected = onFromUnitSelected
                    )

                    // Swap Button positioned overlapping or between
                    Box(
                        modifier = Modifier.padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SwapUnitsButton(onSwap = onSwapUnits)
                    }

                    // To Unit Card
                    UnitCard(
                        label = "To",
                        value = uiState.outputValue,
                        selectedUnit = uiState.toUnit,
                        availableUnits = uiState.availableUnits,
                        isInput = false,
                        onUnitSelected = onToUnitSelected
                    )

                    // Offline Currency Banner
                    AnimatedVisibility(
                        visible = uiState.selectedCategory == ConverterCategory.CURRENCY,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Info,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Currency exchange rates (Offline)",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        ),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                IconButton(
                                    onClick = onRefreshCurrency,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Refresh,
                                        contentDescription = "Refresh Currency Rates",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Keypad Section
            ConverterKeypad(
                currentValue = uiState.inputValue,
                onValueChanged = onInputValueChanged,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterScreenPreviewLight() {
    MultiCalculatorTheme(darkTheme = false) {
        ConverterScreenContent(
            uiState = ConverterUiState(
                selectedCategory = ConverterCategory.LENGTH,
                availableUnits = listOf(
                    UnitItem("m", "Meter", "m"),
                    UnitItem("km", "Kilometer", "km"),
                    UnitItem("ft", "Foot", "ft")
                ),
                fromUnit = UnitItem("m", "Meter", "m"),
                toUnit = UnitItem("ft", "Foot", "ft"),
                inputValue = "100",
                outputValue = "328.084"
            ),
            onCategorySelected = {},
            onFromUnitSelected = {},
            onToUnitSelected = {},
            onInputValueChanged = {},
            onSwapUnits = {},
            onRefreshCurrency = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterScreenPreviewDark() {
    MultiCalculatorTheme(darkTheme = true) {
        ConverterScreenContent(
            uiState = ConverterUiState(
                selectedCategory = ConverterCategory.CURRENCY,
                availableUnits = listOf(
                    UnitItem("USD", "US Dollar", "$"),
                    UnitItem("EUR", "Euro", "€")
                ),
                fromUnit = UnitItem("USD", "US Dollar", "$"),
                toUnit = UnitItem("EUR", "Euro", "€"),
                inputValue = "50",
                outputValue = "46.0"
            ),
            onCategorySelected = {},
            onFromUnitSelected = {},
            onToUnitSelected = {},
            onInputValueChanged = {},
            onSwapUnits = {},
            onRefreshCurrency = {}
        )
    }
}
