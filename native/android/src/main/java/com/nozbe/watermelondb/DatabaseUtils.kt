package com.nozbe.watermelondb

import android.database.Cursor
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

typealias SQL = String
typealias RecordID = String
typealias TableName = String
typealias QueryArgs = ArrayList<Any>
typealias RawQueryArgs = Array<String>
typealias ConnectionTag = Int
typealias SchemaVersion = Int

data class Schema(val version: SchemaVersion, val sql: SQL)
data class MigrationSet(val from: SchemaVersion, val to: SchemaVersion, val sql: SQL)

fun mapCursorToWritableMap(cursor: Cursor): WritableMap =
    Arguments.createMap().also {
        for (i in 0 until cursor.columnCount) {
            when (cursor.getType(i)) {
                Cursor.FIELD_TYPE_NULL -> it.putNull(cursor.getColumnName(i))
                Cursor.FIELD_TYPE_INTEGER -> it.putDouble(cursor.getColumnName(i),
                        cursor.getDouble(i))
                Cursor.FIELD_TYPE_FLOAT -> it.putDouble(cursor.getColumnName(i),
                        cursor.getDouble(i))
                Cursor.FIELD_TYPE_STRING -> it.putString(cursor.getColumnName(i),
                        cursor.getString(i))
                else -> it.putString(cursor.getColumnName(i), "")
            }
        }
    }