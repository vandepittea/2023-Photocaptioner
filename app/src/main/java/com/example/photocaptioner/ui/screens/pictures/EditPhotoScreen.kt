package com.example.photocaptioner.ui.screens.pictures

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.photocaptioner.R
import com.example.photocaptioner.ui.AppViewModelProvider
import com.example.photocaptioner.ui.screens.Button
import com.example.photocaptioner.ui.screens.ButtonIcon
import com.example.photocaptioner.ui.screens.navigation.NavigationDestination
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import kotlinx.coroutines.launch

object EditPhotoDestination : NavigationDestination {
    override val route = "edit_photo"
    override val titleRes = R.string.edit_photo
    const val photoIdArg = "photoId"
    const val albumIdArg = "albumId"
    override val routeWithArgs = "$route/{$albumIdArg}/{$photoIdArg}/{title}"
}

@Composable
fun EditPhotoScreen(
    navigateBack: (route: String, include: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditPhotoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 4.dp, bottom = 35.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        ButtonIcon(
                            onClick = { viewModel.openPhotoInAnotherApp(context) },
                            icon = Icons.Default.Info,
                            description = R.string.info_icon,
                            modifier = Modifier.size(45.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        ButtonIcon(
                            onClick = {
                                viewModel.deletePhoto()
                                navigateBack(EditPhotoDestination.routeWithArgs, true)
                            },
                            icon = Icons.Default.Delete,
                            description = R.string.delete_icon,
                            modifier = Modifier.size(45.dp)
                        )
                    }

                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(viewModel.editPhotoUiState.photoDetails.filePath)
                            .build(),
                        contentDescription = viewModel.editPhotoUiState.photoDetails.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.editPhotoUiState.photoDetails.description,
                onValueChange = { viewModel.updatePhotoDescriptionUiState(it) },
                textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onBackground),
                label = {
                    Text(
                        text = stringResource(R.string.photo_description),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.body1
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth(),
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
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveItem()
                        navigateBack(EditPhotoDestination.routeWithArgs, true)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun EditPhotoScreenPreview() {
    PhotoCaptionerTheme {
        EditPhotoScreen({_, _ ->})
    }
}