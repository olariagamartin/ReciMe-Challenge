package com.themarto.recimechallenge.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.model.Rating
import com.themarto.recimechallenge.ui.preview.PreviewData
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun RatingChip(
    rating: Rating,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Rating",
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "${rating.stars} (${rating.reviews})") // todo: format count
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
        onClick = onClick
    ) {
        Icon(
            imageVector = if (saved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
            contentDescription = stringResource(id = if(saved) R.string.remove_from_saved else R.string.save_recipe)
        )
    }
}

@Composable
fun DietaryBadges(
    dietaryAttributes: List<String>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // todo: add icons
        for (attribute in dietaryAttributes) {
            Text(
                text = attribute,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(4.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun MetricChip(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Rating",
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            modifier = Modifier.padding(8.dp),
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
            dietaryAttributes = PreviewData.recipe.dietaryAttributes
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
