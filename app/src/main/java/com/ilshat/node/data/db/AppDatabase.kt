package com.ilshat.node.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ilshat.node.data.dao.NodeDao
import com.ilshat.node.domain.Node


@Database(entities = [Node::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nodeDao(): NodeDao
}