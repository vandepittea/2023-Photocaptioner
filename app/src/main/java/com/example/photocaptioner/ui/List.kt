package com.example.photocaptioner.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.model.Photo

@Composable
fun AlternatingColumn(items: List<Photo>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items) { index, item ->
            val row = row(index, 2)
            val col = column(index, 2)
            val isEvenRow = row % 2 == 0
            val isCol0 = col == 0
            val isCol1 = col == 1

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isEvenRow && isCol0 || !isEvenRow && isCol1) {
                    Spacer(Modifier.weight(1f))
                }

                Column(Modifier.weight(5f)) {
                    ImageWithDescriptionAndDate(
                        image = item.image,
                        description = item.description,
                        date = item.createdAt,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (isEvenRow && isCol1 || !isEvenRow && isCol0) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

fun row(index: Int, columns: Int): Int {
    return index / columns
}

fun column(index: Int, columns: Int): Int {
    return index % columns
}