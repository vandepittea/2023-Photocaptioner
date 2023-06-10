package com.example.photocaptioner.ui.screens.navigation

import com.example.photocaptioner.ui.screens.navigation.MenuItemType

data class NavigationItemContent(
    val route: String,
    val menuItemType: MenuItemType,
    val icon: Int,
    val text: Int,
    val title: String
)