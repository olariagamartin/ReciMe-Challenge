package com.themarto.recimechallenge.features.recipe_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.components.BackButton
import com.themarto.recimechallenge.components.BookmarkButton
import com.themarto.recimechallenge.components.DietaryBadges
import com.themarto.recimechallenge.model.Recipe
import com.themarto.recimechallenge.ui.preview.PreviewData
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun ImageCard(
    recipe: Recipe,
    onBookmarkClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(300.dp)
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

        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            onClick = { onBackClick() }
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
            onBookmarkClick = {},
            onBackClick = {}
        )
    }
}
