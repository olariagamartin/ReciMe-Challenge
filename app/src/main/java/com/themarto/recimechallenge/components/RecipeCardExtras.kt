package com.themarto.recimechallenge.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.data.model.DietaryAttribute
import com.themarto.recimechallenge.data.model.Rating
import com.themarto.recimechallenge.ui.preview.PreviewData
import com.themarto.recimechallenge.ui.theme.DairyFreeBadgeContainer
import com.themarto.recimechallenge.ui.theme.DairyFreeBadgeContent
import com.themarto.recimechallenge.ui.theme.GlutenFreeBadgeContainer
import com.themarto.recimechallenge.ui.theme.GlutenFreeBadgeContent
import com.themarto.recimechallenge.ui.theme.HighProteinBadgeContainer
import com.themarto.recimechallenge.ui.theme.HighProteinBadgeContent
import com.themarto.recimechallenge.ui.theme.KetoBadgeContainer
import com.themarto.recimechallenge.ui.theme.KetoBadgeContent
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme
import com.themarto.recimechallenge.ui.theme.VeganBadgeContainer
import com.themarto.recimechallenge.ui.theme.VeganBadgeContent
import com.themarto.recimechallenge.ui.theme.VegetarianBadgeContainer
import com.themarto.recimechallenge.ui.theme.VegetarianBadgeContent

@Composable
fun RatingChip(
    rating: Rating,
    modifier: Modifier = Modifier,
    showReviews: Boolean = false,
) {
    Row(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                RoundedCornerShape(16.dp)
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Rating",
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = rating.stars.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
        if (showReviews) {
            Spacer(modifier = Modifier.width(4.dp))
            // todo: format
            Text(
                text = stringResource(R.string.reviews, rating.reviews),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Composable
fun BookmarkButton(
    saved: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledIconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = if (saved) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.surfaceContainerLowest
            },
            contentColor = if (saved) {
                MaterialTheme.colorScheme.onSecondary
            } else {
                MaterialTheme.colorScheme.secondary
            }
        )
    ) {
        Icon(
            imageVector = if (saved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
            contentDescription = stringResource(id = if(saved) R.string.remove_from_saved else R.string.save_recipe)
        )
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back)
        )
    }
}

@Composable
fun DietaryBadges(
    dietaryAttributes: List<DietaryAttribute>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (attribute in dietaryAttributes) {
            val (container, content) = attribute.badgeColors()
            Box(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = getDietaryAttrString(attribute),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(container)
                        .padding(4.dp),
                    color = content
                )
            }

        }
    }
}

@Composable
fun getDietaryAttrString(filter: DietaryAttribute): String {
    return when (filter) {
        DietaryAttribute.VEGAN -> stringResource(R.string.vegan_chip)
        DietaryAttribute.VEGETARIAN -> stringResource(R.string.vegetarian_chip)
        DietaryAttribute.GLUTEN_FREE -> stringResource(R.string.gluten_free_chip)
        DietaryAttribute.DAIRY_FREE -> stringResource(R.string.dairy_free_chip)
        DietaryAttribute.HIGH_PROTEIN -> stringResource(R.string.high_protein_chip)
        DietaryAttribute.KETO -> stringResource(R.string.keto_chip)
    }
}

private fun DietaryAttribute.badgeColors(): Pair<Color, Color> = when (this) {
    DietaryAttribute.VEGETARIAN -> VegetarianBadgeContainer to VegetarianBadgeContent
    DietaryAttribute.VEGAN -> VeganBadgeContainer to VeganBadgeContent
    DietaryAttribute.GLUTEN_FREE -> GlutenFreeBadgeContainer to GlutenFreeBadgeContent
    DietaryAttribute.DAIRY_FREE -> DairyFreeBadgeContainer to DairyFreeBadgeContent
    DietaryAttribute.KETO -> KetoBadgeContainer to KetoBadgeContent
    DietaryAttribute.HIGH_PROTEIN -> HighProteinBadgeContainer to HighProteinBadgeContent
}

@Composable
fun DietaryFilter(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun MetricChip(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Rating",
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingChipPreview() {
    ReciMeChallengeTheme {
        RatingChip(
            rating = PreviewData.recipe.rating
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookmarkButtonPreview() {
    ReciMeChallengeTheme {
        Row(modifier = Modifier.padding(8.dp)) {
            BookmarkButton(saved = false, onClick = {})
            Spacer(modifier = Modifier.width(8.dp))
            BookmarkButton(saved = true, onClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DietaryBadgesPreview() {
    ReciMeChallengeTheme {
        DietaryBadges(
            dietaryAttributes = DietaryAttribute.entries
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MetricChipPreview() {
    ReciMeChallengeTheme {
        MetricChip(
            icon = Icons.Outlined.Schedule,
            label = "30 min"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DietaryFilterPreview() {
    ReciMeChallengeTheme {
        DietaryFilter(
            text = "\uD83C\uDF31 Vegan",
            onClick = {}
        )
    }
}
