package com.themarto.recimechallenge.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun FilterBtn(
    onClick: () -> Unit,
    selected: Boolean,
    filtersApplied: Int,
    modifier: Modifier = Modifier
) {
    BadgedBox(
        badge = {
            Badge {
                Text(
                    text = filtersApplied.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
    ) {
        Box(
            modifier = modifier
                .size(48.dp)
                .clip(RoundedCornerShape(48.dp))
                .background(
                    if (selected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.primaryContainer
                )
                .padding(4.dp)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.FilterList,
                contentDescription = "Filter",
                tint = if (selected) Color.White else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FilterBtnPreview() {
    ReciMeChallengeTheme {
        FilterBtn(
            onClick = {},
            selected = false,
            filtersApplied = 2
        )
    }
}