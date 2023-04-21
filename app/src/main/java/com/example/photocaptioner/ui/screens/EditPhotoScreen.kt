package com.example.photocaptioner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.R
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme

@Composable
fun EditPhotoScreen(
    photoToEdit: Photo,
    description: String,
    onPhotoDescriptionChange : (String) -> Unit,
    onPhotoSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = modifier
        ) {
            TopBar(title = R.string.edit_photo)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = photoToEdit.image),
                contentDescription = stringResource(id = photoToEdit.description)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { onPhotoDescriptionChange(it) },
                label = {
                    Text(
                        text = stringResource(id = R.string.photo_description),
                        style = MaterialTheme.typography.body1
                    )
                }
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            Button(
                text = R.string.save,
                onClick = onPhotoSave
            )
        }
    }
}

@Preview
@Composable
fun EditPhotoScreenPreview() {
    PhotoCaptionerTheme {
        EditPhotoScreen(Datasource.defaultPhoto, "", {}, {})
    }
}