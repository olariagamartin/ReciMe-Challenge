package com.themarto.recimechallenge.features.recipe_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun InstructionSection(
    instructions: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.FormatListNumbered,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.instructions_section_title),
                style = MaterialTheme.typography.titleLarge,
            )
        }

        instructions.forEachIndexed { index, instruction ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceContainerLow, RoundedCornerShape(16.dp)
                    )
            ) {
                Text(
                    text = "${index + 1}.",
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = AnnotatedString.fromHtml(instruction),
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InstructionSectionPreview() {
    ReciMeChallengeTheme {
        InstructionSection(
            instructions = listOf(
                "Preheat the oven to 200°C (400°F).",
                "In a large bowl, toss the <b>potatoes</b> with olive oil, salt, and pepper.",
                "Spread the potatoes in a single layer on a baking sheet.",
                "Roast for 25-30 minutes, or until golden brown and crispy.",
                "Serve hot and enjoy!"
            )
        )
    }
}