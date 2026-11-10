package com.themarto.recimechallenge.features.search.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.core.components.getDietaryAttrString
import com.themarto.recimechallenge.core.data.model.DietaryAttribute
import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.features.search.model.ServingOption
import com.themarto.recimechallenge.core.ui.theme.ReciMeChallengeTheme

@Composable
fun FiltersCard(
    filtersStatus: FiltersStatus,
    onDietaryPreferenceSelected: (DietaryAttribute) -> Unit,
    onServingOptionSelected: (ServingOption) -> Unit,
    onRemoveFromIncluded: (String) -> Unit,
    onAddToIncluded: (String) -> Unit,
    onRemoveFromExcluded: (String) -> Unit,
    onAddToExcluded: (String) -> Unit,
    onResetFilters: () -> Unit,
    onApplyFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var includeDialog by remember { mutableStateOf(false) }
    var excludeDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        DietarySection(
            dietaryAttrsSelected = filtersStatus.dietaryAttrsSelected,
            onDietaryPreferenceSelected = onDietaryPreferenceSelected
        )
        ServingsSection(
            selectedServingOption = filtersStatus.selectedServingOption,
            onServingOptionSelected = onServingOptionSelected
        )
        IncludeIngredientsSection(
            includeIngredients = filtersStatus.includedIngredients,
            removeFromIncluded = onRemoveFromIncluded,
            onAddIngredient = { includeDialog = true }
        )
        ExcludeIngredientsSection(
            excludeIngredients = filtersStatus.excludedIngredients,
            removeFromExcluded = onRemoveFromExcluded,
            onAddIngredient = { excludeDialog = true }
        )
        FooterSection(
            onResetFilters = onResetFilters,
            onApplyFilters = onApplyFilters
        )
    }

    if (includeDialog) {
        IngredientPromptDialog(
            title = stringResource(R.string.add_ingredient),
            onDismiss = { includeDialog = false },
            onConfirm = {
                onAddToIncluded(it)
                includeDialog = false
            }
        )
    }
    if (excludeDialog) {
        IngredientPromptDialog(
            title = stringResource(R.string.exclude_ingredient),
            onDismiss = { excludeDialog = false },
            onConfirm = {
                onAddToExcluded(it)
                excludeDialog = false
            }
        )
    }
}

@Composable
private fun DietarySection(
    dietaryAttrsSelected: List<DietaryAttribute>,
    onDietaryPreferenceSelected: (DietaryAttribute) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            icon = Icons.Outlined.Eco,
            title = stringResource(R.string.dietary_preferences),
            countText = stringResource(R.string.active_count, dietaryAttrsSelected.size),
            countColor = MaterialTheme.colorScheme.primary
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DietaryAttribute.entries.forEach { preference ->
                FilterChip(
                    text = getDietaryAttrString(preference),
                    selected = preference in dietaryAttrsSelected,
                    onClick = { onDietaryPreferenceSelected(preference) }
                )
            }
        }
    }
}

@Composable
private fun ServingsSection(
    selectedServingOption: ServingOption,
    onServingOptionSelected: (ServingOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            icon = Icons.Outlined.Group,
            title = stringResource(R.string.servings_title)
        )
        ServingsSelector(
            selectedOption = selectedServingOption,
            onServingOptionSelected = onServingOptionSelected
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun IncludeIngredientsSection(
    includeIngredients: List<String>,
    removeFromIncluded: (String) -> Unit,
    onAddIngredient: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            icon = Icons.Default.AddCircleOutline,
            title = stringResource(R.string.include_ingredients),
            countText = stringResource(R.string.included_count, includeIngredients.size),
            countColor = MaterialTheme.colorScheme.primary
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            includeIngredients.forEach { ingredient ->
                IngredientChip(
                    text = ingredient,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    onRemove = { removeFromIncluded(ingredient) }
                )
            }
            AddIngredientButton(
                onClick = onAddIngredient
            )
        }
    }
}

@Composable
private fun ExcludeIngredientsSection(
    excludeIngredients: List<String>,
    removeFromExcluded: (String) -> Unit,
    onAddIngredient: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            icon = Icons.Outlined.RemoveCircleOutline,
            title = stringResource(R.string.exclude_ingredients),
            countText = stringResource(R.string.excluded_count, excludeIngredients.size),
            countColor = MaterialTheme.colorScheme.secondary
        )
        FlowRow (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            excludeIngredients.forEach { ingredient ->
                IngredientChip(
                    text = ingredient,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    onRemove = { removeFromExcluded(ingredient) }
                )
            }
            ExcludePlaceholder(
                onClick = onAddIngredient
            )
        }
    }
}

@Composable
private fun FooterSection(
    onResetFilters: () -> Unit,
    onApplyFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedButton(
            onClick = onResetFilters,
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.outline),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.RestartAlt,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.reset_all),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onApplyFilters()
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.apply),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }

    }
}

@Composable
private fun SectionHeader(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    countText: String? = null,
    countColor: Color = Color.Unspecified,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        if (countText != null) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = countText,
                style = MaterialTheme.typography.bodySmall,
                color = countColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun FilterChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val container = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceContainerLowest
    }
    val content = if (selected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val borderColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outlineVariant
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .background(container)
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = content
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 2.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = content,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun IngredientChip(
    text: String,
    containerColor: Color,
    contentColor: Color,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(containerColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onRemove() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = contentColor
        )
    }
}

@Composable
private fun AddIngredientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(R.string.add),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ExcludePlaceholder(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Remove,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(R.string.exclude_placeholder),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ServingsSelector(
    selectedOption: ServingOption,
    onServingOptionSelected: (ServingOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ServingOptionItem(
            text = "Any",
            icon = Icons.Outlined.Tag,
            isSelected = selectedOption == ServingOption.ANY,
            onClick = { onServingOptionSelected(ServingOption.ANY) }
        )
        ServingOptionItem(
            text = "1-2",
            icon = Icons.Outlined.Person,
            isSelected = selectedOption == ServingOption.ONE_TWO,
            onClick = { onServingOptionSelected(ServingOption.ONE_TWO) }
        )
        ServingOptionItem(
            text = "3-4",
            icon = Icons.Outlined.Group,
            isSelected = selectedOption == ServingOption.THREE_FOUR,
            onClick = { onServingOptionSelected(ServingOption.THREE_FOUR) }
        )
        ServingOptionItem(
            text = "5+",
            icon = Icons.Outlined.Groups,
            isSelected = selectedOption == ServingOption.FIVE_PLUS,
            onClick = { onServingOptionSelected(ServingOption.FIVE_PLUS) }
        )
    }
}

@Composable
fun ServingOptionItem(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(height = 56.dp, width = 80.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.surfaceContainerLowest
                else Color.Transparent
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

@Composable
fun FilterBtn(
    onClick: () -> Unit,
    selected: Boolean,
    filtersApplied: Int,
    modifier: Modifier = Modifier,
) {
    BadgedBox(
        badge = {
            if (filtersApplied > 0) {
                Badge(containerColor = MaterialTheme.colorScheme.secondary) {
                    Text(
                        text = filtersApplied.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    ) {
        Box(
            modifier = modifier
                .size(48.dp)
                .clip(RoundedCornerShape(48.dp))
                .background(
                    if (selected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.primaryContainer
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.FilterList,
                contentDescription = "Filter",
                tint = if (selected) Color.White else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FiltersCardPreview() {
    ReciMeChallengeTheme {
        FiltersCard(
            filtersStatus = FiltersStatus(
                dietaryAttrsSelected = listOf(
                    DietaryAttribute.VEGETARIAN,
                    DietaryAttribute.KETO,
                ),
                selectedServingOption = ServingOption.ONE_TWO,
                includedIngredients = listOf("Potatoes", "Carrots"),
                excludedIngredients = listOf("Spinach"),
            ),
            onDietaryPreferenceSelected = {},
            onServingOptionSelected = {},
            onRemoveFromIncluded = {},
            onAddToIncluded = {},
            onRemoveFromExcluded = {},
            onAddToExcluded = {},
            onResetFilters = {},
            onApplyFilters = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterBtnPreview() {
    ReciMeChallengeTheme {
        FilterBtn(
            onClick = {},
            selected = false,
            filtersApplied = 2
        )
    }
}