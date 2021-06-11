package com.bismillah.subfundamental2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int?,
    var photo: String?,
    var username: String?,
) : Parcelable