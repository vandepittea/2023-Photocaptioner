package com.example.photocaptioner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.model.MapsPhoto
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.photocaptioner.R
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

@Composable
fun AddPicturesScreen(
    imagesList: List<MapsPhoto>,
    onImageSelected: (MapsPhoto) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onUploadButtonClick: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            SearchBox(onValueChange = onSearchQueryChange)

            PicturesList(imagesList = imagesList, onCheckChange = onImageSelected)
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(top = 10.dp)
        ) {
            UploadButton(onClick = onUploadButtonClick)
        }
    }
}

@Composable
fun PicturesList(imagesList: List<MapsPhoto>, onCheckChange: (MapsPhoto) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(imagesList) { image ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = image.selected,
                    onCheckedChange = { onCheckChange(image) }
                )
                Image(
                    painter = painterResource(id = R.drawable.album1_picture1),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

    @Composable
fun UploadButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        ButtonWithIcon(
            painter = painterResource(id = R.drawable.baseline_file_upload_24),
            contentDescription = R.string.upload_icon,
            text = R.string.upload,
            onClick = onClick
        )
    }
}

/* TODO */
val imagesList = listOf(
    MapsPhoto("https://picsum.photos/200/300"),
    MapsPhoto("https://picsum.photos/seed/picsum/200/300"),
    MapsPhoto("https://picsum.photos/id/237/200/300"),
    MapsPhoto("https://picsum.photos/id/238/200/300"),
    MapsPhoto("https://picsum.photos/id/239/200/300")
)

@Preview
@Composable
fun AddPicturesScreenPreview(){
    PhotoCaptionerTheme {
        AddPicturesScreen(imagesList = imagesList,
            onImageSelected = {},
            onSearchQueryChange = {},
            onUploadButtonClick = {})
    }
}