package com.example.photocaptioner.ui

import androidx.compose.ui.Alignment
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.photocaptioner.R
import java.time.LocalDate

@Composable
fun Button(@StringRes text: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        modifier = Modifier
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
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        modifier = Modifier
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
                tint = MaterialTheme.colors.onPrimary,
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
    image: Int,
    description: Int?,
    onClick: () -> Unit
) {
    Card(
        elevation = 2.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = if (description != null) stringResource(id = description) else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            if (description != null) {
                Text(
                    text = stringResource(id = description),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
                )
            }
        }
    }
}

/* TODO */
@Composable
fun ImageFromUrl(url: String, description: String?) {
    val painter: Painter = rememberImagePainter(
        data = url,
        builder = {
            crossfade(true)
        }
    )

    Image(
        painter = painter,
        contentDescription = description,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageWithDescriptionAndDate(
    image: Int,
    description: Int,
    date: LocalDate,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 2.dp,
        onClick = onClick,
        modifier = modifier
    ) {
        Column {
            Image(
                painter = painterResource(id = image),
                contentDescription = stringResource(id = description),
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = stringResource(id = description),
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
                    text = "$date",
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
    description: Int
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = description),
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(44.dp)
            )
        }
    )
}

@Composable
fun SearchBox(
    searchValue: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
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
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(8.dp)
                ),
            placeholder = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_icon),
                    tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
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
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.subtitle1
        )
    }
}
