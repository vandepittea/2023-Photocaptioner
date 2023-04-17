package com.example.photocaptioner.model

import com.example.photocaptioner.data.MenuItemType

data class NavigationItemContent(
    val menuItemType: MenuItemType,
    val icon: Int,
    val text: Int
)