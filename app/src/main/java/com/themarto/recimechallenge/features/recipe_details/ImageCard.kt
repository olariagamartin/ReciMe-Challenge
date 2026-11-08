package com.themarto.recimechallenge.features.recipe_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.themarto.recimechallenge.components.BookmarkButton
import com.themarto.recimechallenge.components.DietaryBadges
import com.themarto.recimechallenge.components.RatingChip
import com.themarto.recimechallenge.model.Recipe
import com.themarto.recimechallenge.ui.preview.PreviewData
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun ImageCard(
    recipe: Recipe,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .height(300.dp)
        .clip(RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = recipe.imgUrl,
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceDim),
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
            rating = recipe.rating,
            showReviews = true,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopStart)
        )

        DietaryBadges(
            dietaryAttributes = recipe.dietaryAttributes,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomStart)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImageCardPreview() {
    ReciMeChallengeTheme {
        ImageCard(
            recipe = PreviewData.recipe,
            onBookmarkClick = {}
        )
    }
}
