package com.themarto.recimechallenge.features.recipe_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalDining
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.data.model.Ingredient
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun IngredientsSection(
    ingredients: List<Ingredient>,
    checkedIngredients: Set<Int>,
    onItemCheckedChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.LocalDining,
                contentDescription = stringResource(R.string.ingredients),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.ingredient_section_title),
                style = MaterialTheme.typography.titleLarge
            )
        }

        ingredients.forEachIndexed { index, item ->
            IngredientRow(
                ingredient = item,
                checked = index in checkedIngredients,
                onCheckedChange = { onItemCheckedChange(index) }
            )
        }
    }
}

@Composable
private fun IngredientRow(
    ingredient: Ingredient,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(
                MaterialTheme.colorScheme.surfaceBright, RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = ingredient.name,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = if (checked) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "${ingredient.amount} ${ingredient.unit}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = if (checked) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun IngredientsSectionPreview() {
    ReciMeChallengeTheme {
        IngredientsSection(
            ingredients = listOf(
                Ingredient("Tomato", "2", "pcs"),
                Ingredient("Onion", "1", "pc"),
                Ingredient("Garlic", "3", "cloves"),
                Ingredient("Olive Oil", "2", "tbsp")
            ),
            checkedIngredients = setOf(0, 2),
            onItemCheckedChange = {},
        )
    }
}