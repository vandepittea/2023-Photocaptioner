package com.example.photocaptioner.model

import com.example.photocaptioner.data.MenuItemType

data class NavigationItemContent(
    val route: String,
    val menuItemType: MenuItemType,
    val icon: Int,
    val text: Int,
    val title: String
)