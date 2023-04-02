package com.example.photocaptioner.ui

import androidx.compose.ui.Alignment
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import java.text.DateFormat
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

@Composable
fun ImageWithDescription(
    image: Int,
    description: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = description),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(id = description),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
        )
    }
}

@Composable
fun ImageWithDescriptionAndDate(
    image: Int,
    description: Int,
    date: LocalDate,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
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

        Text(
            text = "Created on $date",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(bottom = 8.dp)
        )
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
