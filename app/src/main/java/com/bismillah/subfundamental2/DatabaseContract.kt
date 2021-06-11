package com.bismillah.subfundamental2

import android.net.Uri
import android.provider.BaseColumns

internal class DatabaseContract {
    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "tbuser"
            const val ID = "user_id"
            const val PHOTO = "photo"
            const val USERNAME = "username"
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

    companion object {
        const val AUTHORITY = "com.bismillah.subfundamental2"
        const val SCHEME = "content"
    }
}