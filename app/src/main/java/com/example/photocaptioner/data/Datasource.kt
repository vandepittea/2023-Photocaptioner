package com.example.photocaptioner.data

import CameraPageDestination
import com.example.photocaptioner.R
import com.example.photocaptioner.data.database.PhotoDB
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.NavigationItemContent
import com.example.photocaptioner.model.Photo
import com.example.photocaptioner.ui.HomeDestination
import com.example.photocaptioner.ui.screens.album.AlbumsDestination
import java.time.LocalDate
import java.time.LocalDateTime

object Datasource {
    val defaultAlbum = getAlbums()[0]
    val defaultPhoto = defaultAlbum.photos[0]

    fun getAlbums(): List<AlbumWithImages> {
        return listOf(
            AlbumWithImages(
                Album(
                    0,
                    "R.string.album1_title",
                    "R.string.album1_description",
                    LocalDateTime.of(2022, 4, 13, 12, 0, 0)
                ),
                listOf(
                    Photo(0,"R.string.album1_picture1_description",LocalDateTime.of(2022, 4, 14, 12, 0, 0), "", 0),
                    Photo(1, "R.string.album1_picture1_description",LocalDateTime.of(2022, 4, 14, 12, 0, 0), "", 0),
                    Photo(2, "R.string.album1_picture1_description",LocalDateTime.of(2022, 4, 14, 12, 0, 0), "", 0)
                )
            ),
            AlbumWithImages(
                Album(
                    1,
                    "R.string.album2_title",
                    "R.string.album2_description",
                    LocalDateTime.of(2022, 4, 14, 12, 0, 0)
                ),
                listOf(
                    Photo(3, "R.string.album2_picture1_description",LocalDateTime.of(2022, 4, 14, 12, 0, 0), "", 1),
                    Photo(4, "R.string.album2_picture1_description",LocalDateTime.of(2022, 4, 14, 12, 0, 0), "", 1),
                    Photo(5, "R.string.album2_picture1_description",LocalDateTime.of(2022, 4, 14, 12, 0, 0), "", 1)
                )
            ),
            AlbumWithImages(
                Album(
                    2,
                    "R.string.album3_title",
                    "R.string.album3_description",
                    LocalDateTime.of(2022, 4, 14, 12, 0, 0)
                ),
                listOf(
                    Photo(6, "R.string.album3_picture1_description",LocalDateTime.of(2022, 4, 14, 12, 0, 0), "", 2),
                    Photo(7, "R.string.album3_picture1_description",LocalDateTime.of(2022, 4, 14, 12, 0, 0), "", 2),
                    Photo(8, "R.string.album3_picture1_description",LocalDateTime.of(2022, 4, 14, 12, 0, 0), "", 2)
                )
            )
        )
    }

    val navigationItemContentList = listOf(
        NavigationItemContent(
            route = HomeDestination.route,
            menuItemType = MenuItemType.Home,
            icon = R.drawable.baseline_home_24,
            text = R.string.home_menu,
            title = "Home"
        ),
        NavigationItemContent(
            route = "${CameraPageDestination.route}/${-1}",
            menuItemType = MenuItemType.Photo,
            icon = R.drawable.baseline_camera_alt_24,
            text = R.string.camera_menu,
            title = "Camera"
        ),
        NavigationItemContent(
            route = AlbumsDestination.route,
            menuItemType = MenuItemType.Albums,
            icon = R.drawable.baseline_photo_album_24,
            text = R.string.album_menu,
            title = "Albums"
        )
    )
}
