package com.example.photocaptioner.data

import com.example.photocaptioner.R
import com.example.photocaptioner.data.database.PhotoDB
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.NavigationItemContent
import com.example.photocaptioner.model.Photo
import java.time.LocalDate

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
                    LocalDate.of(2022, 4, 13)
                ),
                listOf(
                    Photo(0, "R.drawable.album1_picture1", "R.string.album1_picture1_description",LocalDate.of(2022, 4, 14), "", 0),
                    Photo(1, "R.drawable.album1_picture1", "R.string.album1_picture1_description",LocalDate.of(2022, 4, 14), "", 0),
                    Photo(2, "R.drawable.album1_picture1", "R.string.album1_picture1_description",LocalDate.of(2022, 4, 14), "", 0)
                )
            ),
            AlbumWithImages(
                Album(
                    1,
                    "R.string.album2_title",
                    "R.string.album2_description",
                    LocalDate.of(2022, 4, 13)
                ),
                listOf(
                    Photo(3, "R.drawable.album2_picture1", "R.string.album2_picture1_description",LocalDate.of(2022, 4, 14), "", 1),
                    Photo(4, "R.drawable.album2_picture1", "R.string.album2_picture1_description",LocalDate.of(2022, 4, 14), "", 1),
                    Photo(5, "R.drawable.album2_picture1", "R.string.album2_picture1_description",LocalDate.of(2022, 4, 14), "", 1)
                )
            ),
            AlbumWithImages(
                Album(
                    2,
                    "R.string.album3_title",
                    "R.string.album3_description",
                    LocalDate.of(2022, 4, 13)
                ),
                listOf(
                    Photo(6, "R.drawable.album3_picture1", "R.string.album3_picture1_description",LocalDate.of(2022, 4, 14), "", 2),
                    Photo(7, "R.drawable.album3_picture1", "R.string.album3_picture1_description",LocalDate.of(2022, 4, 14), "", 2),
                    Photo(8, "R.drawable.album3_picture1", "R.string.album3_picture1_description",LocalDate.of(2022, 4, 14), "", 2)
                )
            )
        )
    }

    val navigationItemContentList = listOf(
        NavigationItemContent(
            menuItemType = MenuItemType.Home,
            icon = R.drawable.baseline_home_24,
            text = R.string.home_menu
        ),
        NavigationItemContent(
            menuItemType = MenuItemType.Photo,
            icon = R.drawable.baseline_camera_alt_24,
            text = R.string.camera_menu
        ),
        NavigationItemContent(
            menuItemType = MenuItemType.Albums,
            icon = R.drawable.baseline_photo_album_24,
            text = R.string.album_menu
        )
    )
}
