package com.themarto.recimechallenge.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {

}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    filtersApplied: Int,
    filterSelected: Boolean
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                searchQuery = searchQuery,
                onQueryChange = onQueryChange,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterBtn(
                onClick = onFilterClick,
                selected = filterSelected,
                filtersApplied = filtersApplied
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenContentPreview() {
    ReciMeChallengeTheme {
        SearchScreenContent(
            searchQuery = "Pasta",
            onQueryChange = {},
            onFilterClick = {},
            filtersApplied = 2,
            filterSelected = true
        )
    }
}
