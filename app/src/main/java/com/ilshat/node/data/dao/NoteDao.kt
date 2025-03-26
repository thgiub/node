package com.ilshat.node.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ilshat.node.domain.Node
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {

    @Insert
    suspend fun insertNode(node: Node)

    @Delete
    suspend fun deleteNode(node: Node)

    @Query("SELECT * FROM nodes WHERE parentId IS NULL")
    fun getRootNodes(): Flow<List<Node>>

    @Query("SELECT * FROM nodes WHERE parentId = :parentId")
    fun getChildNodes(parentId: Long?): Flow<List<Node>>
}