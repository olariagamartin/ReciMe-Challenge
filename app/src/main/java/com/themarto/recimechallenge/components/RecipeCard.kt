package com.themarto.recimechallenge.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.model.Recipe
import com.themarto.recimechallenge.ui.preview.PreviewData

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = recipe.imgUrl,
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceDim),
                    contentDescription = recipe.title,
                    contentScale = ContentScale.Crop
                )
                BookmarkButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    saved = recipe.saved,
                    onClick = { onBookmarkClick() }
                )
                RatingChip(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    rating = recipe.rating
                )
            }
            DietaryBadges(
                modifier = Modifier
                    .padding(8.dp),
                dietaryAttributes = recipe.dietaryAttributes.take(3)
            )
            Text(
                text = recipe.title,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricChip(
                    icon = Icons.Outlined.Schedule,
                    label = stringResource(id = R.string.time_min, recipe.timeInMinutes)
                )
                MetricChip(
                    icon = Icons.Filled.Group,
                    label = stringResource(id = R.string.servings, recipe.servings)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Preview(widthDp = 400)
@Composable
private fun RecipeCardPrev() {
    RecipeCard(
        recipe = PreviewData.recipe,
        onClick = {},
        onBookmarkClick = {}
    )
}
