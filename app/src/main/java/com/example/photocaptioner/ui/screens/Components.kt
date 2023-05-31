package com.example.photocaptioner.ui

import androidx.compose.ui.Alignment
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.photocaptioner.R
import com.example.photocaptioner.model.Photo

@Composable
fun Button(
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colors.primary,
            contentColor = colors.onPrimary
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(60.dp)
    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.button
        )
    }
}

@Composable
fun ButtonWithIcon(
    painter: Painter,
    contentDescription: Int,
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colors.primary,
            contentColor = colors.onPrimary
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(60.dp),
        contentPadding = PaddingValues(start = 0.dp, end = 0.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painter,
                contentDescription = stringResource(id = contentDescription),
                tint = colors.onPrimary,
                modifier = Modifier.size(50.dp)
            )

            Text(
                text = stringResource(id = text),
                style = MaterialTheme.typography.button,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageWithDescription(
    photo: Photo?,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 2.dp,
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (photo != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(photo.filePath)
                        .build(),
                    contentDescription = photo.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = description,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
            )
        }
    }
}

/* TODO */
/*@Composable
fun ImageFromUrl(
    url: String,
    description: String?,
    modifier: Modifier = Modifier
) {
    val painter: Painter = rememberImagePainter(
        data = url,
        builder = {
            crossfade(true)
        }
    )

    Image(
        painter = painter,
        contentDescription = description,
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}*/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageWithDescriptionAndDate(
    photo: Photo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 2.dp,
        onClick = onClick,
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(photo.filePath)
                    .build(),
                contentDescription = photo.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = photo.description,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_access_time_filled_24),
                    contentDescription = stringResource(id = R.string.time_icon),
                    tint = colors.onBackground
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${photo.createdAt}",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun ButtonIcon(
    onClick: () -> Unit,
    icon: ImageVector,
    description: Int,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = description),
                tint = colors.onPrimary,
                modifier = Modifier.size(44.dp)
            )
        },
        modifier = modifier
    )
}

@Composable
fun SearchBox(
    searchValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        TextField(
            value = searchValue,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colors.surface,
                    shape = RoundedCornerShape(8.dp)
                ),
            placeholder = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.body1,
                    color = colors.onSurface.copy(alpha = 0.5f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_icon),
                    tint = colors.onSurface.copy(alpha = 0.5f)
                )
            },
            textStyle = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun TopBar(
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun ImageOptions(
    onChooseCamera: () -> Unit,
    onChooseGallery: () -> Unit,
    onChooseMaps: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        ButtonWithIcon(
            painter = painterResource(id = R.drawable.baseline_camera_alt_24),
            contentDescription = R.string.camera_icon,
            text = R.string.take_picture,
            onClick = onChooseCamera
        )
        Spacer(modifier = Modifier.height(8.dp))
        ButtonWithIcon(
            painter = painterResource(id = R.drawable.baseline_camera_alt_24),
            contentDescription = R.string.camera_icon,
            text = R.string.gallery,
            onClick = onChooseGallery
        )
        Spacer(modifier = Modifier.height(8.dp))
        ButtonWithIcon(
            painter = painterResource(id = R.drawable.baseline_camera_alt_24),
            contentDescription = R.string.camera_icon,
            text = R.string.maps,
            onClick = onChooseMaps
        )
    }
}
