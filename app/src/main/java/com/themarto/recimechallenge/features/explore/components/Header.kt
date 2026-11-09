package com.themarto.recimechallenge.features.explore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.components.DietaryFilter
import com.themarto.recimechallenge.data.model.DietaryFilters

@Composable
fun Header(
    onSearchClick: () -> Unit,
    dietaryFilters: List<DietaryFilters>,
    onDietaryFilterClick: (DietaryFilters) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TitleHeader()
        SearchBarButton(
            onSearchClick = onSearchClick,
        )
        DietaryFiltersRow(
            dietaryFilters = dietaryFilters,
            onDietaryFilterClick = onDietaryFilterClick
        )
    }

}

@Composable
fun TitleHeader(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.explore_header_title),
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier
    )
}

@Composable
fun SearchBarButton(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onSearchClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_button_content_description)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(id = R.string.search_button_text))
        }

    }
}

@Composable
fun DietaryFiltersRow(
    dietaryFilters: List<DietaryFilters>,
    onDietaryFilterClick: (DietaryFilters) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (attribute in dietaryFilters) {
            DietaryFilter(
                text = getDietaryFilter(attribute),
                onClick = { onDietaryFilterClick(attribute) },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun getDietaryFilter(filter: DietaryFilters): String {
    return when (filter) {
        DietaryFilters.VEGAN -> stringResource(R.string.vegan_chip)
        DietaryFilters.VEGETARIAN -> stringResource(R.string.vegetarian_chip)
        DietaryFilters.GLUTEN_FREE -> stringResource(R.string.gluten_free_chip)
        DietaryFilters.DAIRY_FREE -> stringResource(R.string.dairy_free_chip)
        DietaryFilters.HIGH_PROTEIN -> stringResource(R.string.high_protein_chip)
        DietaryFilters.KETO -> stringResource(R.string.keto_chip)
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPrev() {
    Header(
        onSearchClick = { },
        dietaryFilters = DietaryFilters.entries,
        onDietaryFilterClick = { }
    )
}