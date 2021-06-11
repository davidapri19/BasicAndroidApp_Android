package com.bismillah.subfundamental2

import android.database.Cursor

object MappingHelper {
    fun mapCursortoArraylist(cursor: Cursor?):ArrayList<User>{
        val list = ArrayList<User>()
        cursor?.apply {
            while (moveToNext()){
                val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
                val id = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.ID)).toInt()
                val photo = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.PHOTO))
                list.add(User(id,photo,username))
            }
        }
        return list
    }
}