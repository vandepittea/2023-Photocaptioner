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
fun AlbumDetailScreen(album: Album) {
    val colors = MaterialTheme.colors

    Box(
        Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column(
            Modifier.fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = album.name),
                    style = MaterialTheme.typography.subtitle1,
                    color = colors.onBackground,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .weight(6f)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_download_24),
                            contentDescription = stringResource(R.string.edit_icon),
                            tint = colors.onBackground,
                            modifier = Modifier.
                            size(45.dp)
                        )
                    }

                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit_icon),
                            tint = colors.onBackground,
                            modifier = Modifier.
                            size(45.dp)
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_description_24),
                    contentDescription = stringResource(id = R.string.description_icon),
                    tint = colors.onBackground,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(id = album.description),
                    style = MaterialTheme.typography.body1,
                    color = colors.onBackground
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_access_time_filled_24),
                    contentDescription = stringResource(id = R.string.time_icon),
                    tint = colors.onBackground,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${album.lastChanged}",
                    style = MaterialTheme.typography.body2,
                    color = colors.onBackground,
                )
            }

            AlternatingColumn(items = album.photos)
        }

        Column(
            Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(colors.background)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_download_24),
                        contentDescription = stringResource(R.string.edit_icon),
                        tint = colors.onBackground,
                        modifier = Modifier.size(45.dp)
                    )
                }

                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_download_24),
                        contentDescription = stringResource(R.string.edit_icon),
                        tint = colors.onBackground,
                        modifier = Modifier.size(45.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AlbumDetailScreenPreview(){
    PhotoCaptionerTheme {
        AlbumDetailScreen(Datasource.albumList[0])
    }
}