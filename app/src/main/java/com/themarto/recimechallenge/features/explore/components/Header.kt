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

@Composable
fun Header(
    onSearchClick: () -> Unit,
    dietaryFilters: List<String>,
    onDietaryFilterClick: (String) -> Unit,
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
        DietaryFilters(
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
fun DietaryFilters(
    dietaryFilters: List<String>,
    onDietaryFilterClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (attribute in dietaryFilters) {
            DietaryFilter(
                text = attribute,
                onClick = { onDietaryFilterClick(attribute) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPrev() {
    Header(
        onSearchClick = { },
        dietaryFilters = listOf("Vegan", "Vegetarian", "Gluten-free"),
        onDietaryFilterClick = { }
    )
}