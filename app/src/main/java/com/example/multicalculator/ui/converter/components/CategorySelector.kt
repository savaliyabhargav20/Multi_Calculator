package com.example.multicalculator.ui.converter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.GridOn
import androidx.compose.material.icons.rounded.Scale
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.Straighten
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.multicalculator.domain.converter.ConverterCategory

@Composable
fun CategorySelector(
    selectedCategory: ConverterCategory,
    onCategorySelected: (ConverterCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ConverterCategory.entries.toTypedArray()) { category ->
            val isSelected = category == selectedCategory
            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(category) },
                label = {
                    Text(
                        text = category.displayName,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = getCategoryIcon(category),
                        contentDescription = category.displayName,
                        modifier = Modifier.size(18.dp)
                    )
                },
                shape = RoundedCornerShape(20.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    iconColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                border = null
            )
        }
    }
}

private fun getCategoryIcon(category: ConverterCategory): ImageVector {
    return when (category) {
        ConverterCategory.LENGTH -> Icons.Rounded.Straighten
        ConverterCategory.WEIGHT -> Icons.Rounded.Scale
        ConverterCategory.TEMPERATURE -> Icons.Rounded.Thermostat
        ConverterCategory.AREA -> Icons.Rounded.GridOn
        ConverterCategory.VOLUME -> Icons.Rounded.WaterDrop
        ConverterCategory.SPEED -> Icons.Rounded.Speed
        ConverterCategory.CURRENCY -> Icons.Rounded.AttachMoney
    }
}
