package com.themarto.recimechallenge.features.recipe_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.model.Recipe
import com.themarto.recimechallenge.ui.preview.PreviewData
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun RecipeDetailsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is RecipeDetailsUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is RecipeDetailsUiState.Success -> {
            RecipeDetailsContent(
                recipe = state.recipe,
                checkedIngredients = state.checkedIngredients,
                onBookmarkClick = viewModel::onBookmarkClick,
                onBackClick = onBackClick,
                onIngredientCheckedChange = viewModel::onIngredientCheckedChange,
                modifier = modifier
            )
        }

        is RecipeDetailsUiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsContent(
    recipe: Recipe,
    checkedIngredients: Set<Int>,
    onBookmarkClick: () -> Unit,
    onBackClick: () -> Unit,
    onIngredientCheckedChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.details_title)) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ImageCard(
                recipe = recipe,
                onBookmarkClick = onBookmarkClick
            )
            IngredientsSection(
                ingredients = recipe.ingredients,
                checkedIngredients = checkedIngredients,
                onItemCheckedChange = onIngredientCheckedChange
            )
            InstructionSection(
                instructions = recipe.instructions
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun RecipeDetailsContentPrev() {
    ReciMeChallengeTheme {
        RecipeDetailsContent(
            recipe = PreviewData.recipe,
            onBookmarkClick = {},
            onBackClick = {},
            checkedIngredients = setOf(0, 2),
            onIngredientCheckedChange = {}
        )
    }
}
