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
fun AlternatingColumn(
    items: List<Photo>,
    onPhotoClick: (Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items) { index, item ->
            val isEvenRow = index % 2 == 0

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isEvenRow) {
                    Spacer(Modifier.weight(1f))
                }

                Column(Modifier.weight(5f)) {
                    ImageWithDescriptionAndDate(
                        photo = item,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onPhotoClick(item.id) }
                    )
                }

                if (!isEvenRow) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}