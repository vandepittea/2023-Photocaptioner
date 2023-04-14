package com.example.photocaptioner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photocaptioner.ui.theme.PhotoCaptionerTheme
import com.example.photocaptioner.data.Datasource
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.R

@Composable
fun AlbumDetailScreen(
    album: Album,
    onDownloadClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            AlbumDetails(
                album = album,
                onDownloadClick = onDownloadClick,
                onEditClick = onEditClick,
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            AlbumFooter(
                onAddClick = onAddClick,
                onShareClick = onShareClick,
            )
        }
    }
}

@Composable
fun AlbumDetails(
    album: Album,
    onDownloadClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    AlbumHeader(
        name = stringResource(id = album.name),
        onDownloadClick = onDownloadClick,
        onEditClick = onEditClick,
    )

    DescriptionRow(
        description = stringResource(id = album.description),
    )

    TimeRow(
        lastChanged = "${album.lastChanged}",
    )

    AlternatingColumn(
        items = album.photos
    )
}

@Composable
fun AlbumHeader(
    name: String,
    onDownloadClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .weight(6f)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            IconButton(
                onClick = onDownloadClick,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_download_24),
                    contentDescription = stringResource(R.string.download_icon),
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.size(45.dp)
                )
            }

            IconButton(
                onClick = onEditClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_icon),
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.size(45.dp)
                )
            }
        }
    }
}

@Composable
fun DescriptionRow(
    description: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_description_24),
            contentDescription = stringResource(id = R.string.description_icon),
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun TimeRow(
    lastChanged: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_access_time_filled_24),
            contentDescription = stringResource(id = R.string.time_icon),
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = lastChanged,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
        )
    }
}

@Composable
fun AlbumFooter(
    onAddClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onAddClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = stringResource(R.string.add_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(45.dp)
            )
        }

        IconButton(
            onClick = onShareClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_share_24),
                contentDescription = stringResource(R.string.share_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(45.dp)
            )
        }
    }
}

@Preview
@Composable
fun AlbumDetailScreenPreview(){
    PhotoCaptionerTheme {
        AlbumDetailScreen(Datasource.defaultAlbum, {}, {}, {}, {})
    }
}