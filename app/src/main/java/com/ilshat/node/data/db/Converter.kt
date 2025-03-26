package com.ilshat.node.data.db

import androidx.room.TypeConverter
import com.ilshat.node.domain.Node
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromNodeList(value: List<Node>?): String? {
        val gson = Gson()
        return gson.toJson(value)
    }
    @TypeConverter
    fun toNodeList(value: String?): List<Node>? {
        val gson = Gson()
        val listType = object : TypeToken<List<Node>>() {}.type
        return gson.fromJson(value, listType)
    }
}