package com.themarto.recimechallenge.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.features.explore.ExploreDestination
import com.themarto.recimechallenge.features.saved.SavedDestination
import com.themarto.recimechallenge.features.search.SearchDestination
import com.themarto.recimechallenge.core.ui.theme.ReciMeChallengeTheme

data class BottomBar(
    val labelRes: Int,
    val icon: ImageVector,
    val route: String,
)

val bottomTabs = listOf(
    BottomBar(
        labelRes = R.string.explore_tab_label,
        icon = Icons.Filled.Explore,
        route = ExploreDestination.route
    ),
    BottomBar(
        labelRes = R.string.search_tab_label,
        icon = Icons.Filled.Search,
        route = SearchDestination.route
    ),
    BottomBar(
        labelRes = R.string.saved_tab_label,
        icon = Icons.Filled.Bookmark,
        route = SavedDestination.route
    )
)


@Composable
fun BottomNavBar(
    currentRoute: String?,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.surfaceContainerHighest
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            bottomTabs.forEach { tab ->
                val selected = currentRoute?.startsWith(tab.route) == true
                NavigationBarItem(
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(width = 56.dp, height = 32.dp)
                                .clip(CircleShape)
                                .background(
                                    if (selected) MaterialTheme.colorScheme.primaryContainer
                                    else Color.Transparent
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = null,
                                tint = if (selected) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(tab.labelRes),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                        )
                    },
                    selected = selected,
                    onClick = { onTabSelected(tab.route) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    ReciMeChallengeTheme {
        BottomNavBar(
            currentRoute = SearchDestination.route,
            onTabSelected = {}
        )
    }
}