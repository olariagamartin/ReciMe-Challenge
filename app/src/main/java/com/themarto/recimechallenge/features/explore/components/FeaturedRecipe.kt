package com.themarto.recimechallenge.features.explore.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.core.components.BookmarkButton
import com.themarto.recimechallenge.core.components.DietaryBadges
import com.themarto.recimechallenge.core.components.MetricChip
import com.themarto.recimechallenge.core.components.RatingChip
import com.themarto.recimechallenge.core.data.model.Recipe
import com.themarto.recimechallenge.core.ui.preview.PreviewData
import com.themarto.recimechallenge.core.ui.theme.ReciMeChallengeTheme

@Composable
fun FeaturedRecipe(
    recipe: Recipe,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Badge(
                modifier = Modifier
                    .padding(start = 8.dp, end = 4.dp, bottom = 4.dp)
                    .size(8.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = stringResource(R.string.recipe_of_the_day_label),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        FeaturedRecipeCard(
            recipe = recipe,
            onClick = onClick,
            onBookmarkClick = onBookmarkClick,
            modifier = modifier
        )
    }
}

@Composable
fun FeaturedRecipeCard(
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
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        ),
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
                    rating = recipe.rating,
                    showReviews = true
                )
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DietaryBadges(
                    dietaryAttributes = recipe.dietaryAttributes.take(3)
                )
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.surfaceContainer,
                            RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MetricChip(
                        icon = Icons.Outlined.Schedule,
                        label = stringResource(id = R.string.time_min, recipe.timeInMinutes)
                    )
                    MetricChip(
                        icon = Icons.Filled.Group,
                        label = stringResource(id = R.string.servings, recipe.servings)
                    )
                }
                Button(
                    onClick = onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(R.string.view_recipe), style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.size(8.dp))
                    Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }

        }
    }
}

@Preview(widthDp = 400, showBackground = true)
@Composable
private fun RecipeCardPrev() {
    ReciMeChallengeTheme {
        FeaturedRecipe(
            recipe = PreviewData.recipe,
            onClick = {},
            onBookmarkClick = {}
        )
    }
}