package com.themarto.recimechallenge.features.search.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.components.getDietaryAttrString
import com.themarto.recimechallenge.data.model.DietaryAttribute
import com.themarto.recimechallenge.features.search.model.DietaryItem
import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.features.search.model.ServingOption
import com.themarto.recimechallenge.ui.theme.DietarySectionCountColor
import com.themarto.recimechallenge.ui.theme.DietarySelectedChip
import com.themarto.recimechallenge.ui.theme.ExcludeIngredientsSectionCountColor
import com.themarto.recimechallenge.ui.theme.ExcludedIngredientChip
import com.themarto.recimechallenge.ui.theme.FilterSelectedBg
import com.themarto.recimechallenge.ui.theme.FilterSelectedContent
import com.themarto.recimechallenge.ui.theme.IncludeIngredientsSectionCountColor
import com.themarto.recimechallenge.ui.theme.IncludedIngredientChip
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme
import com.themarto.recimechallenge.ui.theme.ServingOptionNotSelectedTint
import com.themarto.recimechallenge.ui.theme.ServingOptionSelectedTint

@Composable
fun FiltersCard(
    filtersStatus: FiltersStatus,
    onDietaryPreferenceSelected: (DietaryItem) -> Unit,
    onServingOptionSelected: (ServingOption) -> Unit,
    onRemoveFromIncluded: (String) -> Unit,
    onAddToIncluded: () -> Unit,
    onRemoveFromExcluded: (String) -> Unit,
    onAddToExcluded: () -> Unit,
    onResetFilters: () -> Unit,
    onApplyFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        DietarySection(
            dietaryItems = filtersStatus.dietaryItems,
            onDietaryPreferenceSelected = onDietaryPreferenceSelected
        )
        ServingsSection(
            selectedServingOption = filtersStatus.selectedServingOption,
            onServingOptionSelected = onServingOptionSelected
        )
        IncludeIngredientsSection(
            includeIngredients = filtersStatus.includedIngredients,
            removeFromIncluded = onRemoveFromIncluded,
            onAddIngredient = onAddToIncluded
        )
        ExcludeIngredientsSection(
            excludeIngredients = filtersStatus.excludedIngredients,
            removeFromExcluded = onRemoveFromExcluded,
            onAddIngredient = onAddToExcluded
        )
        FooterSection(
            onResetFilters = onResetFilters,
            onApplyFilters = onApplyFilters
        )
    }
}

@Composable
private fun DietarySection(
    dietaryItems: List<DietaryItem>,
    onDietaryPreferenceSelected: (DietaryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            icon = Icons.Outlined.Eco,
            title = stringResource(R.string.dietary_preferences),
            countText = stringResource(R.string.active_count, 1),
            countColor = DietarySectionCountColor
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            dietaryItems.forEach { preference ->
                FilterChip(
                    text = getDietaryAttrString(preference.dietaryAttribute),
                    selected = preference.isSelected,
                    selectedColor = DietarySelectedChip,
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
    modifier: Modifier = Modifier
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
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            icon = Icons.Default.AddCircleOutline,
            title = stringResource(R.string.include_ingredients),
            countText = stringResource(R.string.selected_count, includeIngredients.size),
            countColor = IncludeIngredientsSectionCountColor
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            includeIngredients.forEach { ingredient ->
                IngredientChip(
                    text = ingredient,
                    color = IncludedIngredientChip,
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
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            icon = Icons.Outlined.RemoveCircleOutline,
            title = stringResource(R.string.exclude_ingredients),
            countText = stringResource(R.string.excluded_count, 1),
            countColor = ExcludeIngredientsSectionCountColor
        )
        FlowRow (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            excludeIngredients.forEach { ingredient ->
                IngredientChip(
                    text = ingredient,
                    color = ExcludedIngredientChip,
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedButton(
            onClick = onApplyFilters,
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
            onClick = onApplyFilters,
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
    countColor: Color = Color.Unspecified
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
    selectedColor: Color = Color.Transparent,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .background(if (selected) selectedColor else FilterSelectedBg)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = FilterSelectedContent
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 2.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = FilterSelectedContent,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun IngredientChip(
    text: String,
    color: Color,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onRemove() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
        )
    }
}

@Composable
private fun AddIngredientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
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
    modifier: Modifier = Modifier
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF0E7E7))
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
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(height = 56.dp, width = 80.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (isSelected) ServingOptionSelectedTint else ServingOptionNotSelectedTint
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) ServingOptionSelectedTint else ServingOptionNotSelectedTint,
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
    modifier: Modifier = Modifier
) {
    BadgedBox(
        badge = {
            if (filtersApplied > 0) {
                Badge {
                    Text(
                        text = filtersApplied.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
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
                dietaryItems = listOf(
                    DietaryItem(DietaryAttribute.VEGETARIAN, true),
                    DietaryItem(DietaryAttribute.VEGAN, false),
                    DietaryItem(DietaryAttribute.GLUTEN_FREE, false),
                    DietaryItem(DietaryAttribute.DAIRY_FREE, false),
                    DietaryItem(DietaryAttribute.KETO, true),
                    DietaryItem(DietaryAttribute.HIGH_PROTEIN, false)
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