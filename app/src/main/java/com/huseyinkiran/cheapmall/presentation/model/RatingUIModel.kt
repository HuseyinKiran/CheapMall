package com.huseyinkiran.cheapmall.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingUIModel(val rate: Double, val count: Int) : Parcelable
