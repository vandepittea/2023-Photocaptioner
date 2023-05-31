package com.example.photocaptioner.ui.screens.pictures

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ChoosePicturesSourceViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val albumId: Long = checkNotNull(savedStateHandle[ChoosePicturesDestination.albumIdArg])
}