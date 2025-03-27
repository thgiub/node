package com.ilshat.node.data.db

import com.ilshat.node.domain.Node

class LocalDataSource(private val database: AppDatabase) {
    suspend fun deleteNode(node: Node) = database.nodeDao().deleteNode(node)
    suspend fun insertNode(node: Node) = database.nodeDao().insertNode(node)
    fun getNodes() = database.nodeDao().getParentId()
    fun getNodes(id: Long?) = database.nodeDao().getChildNodes(id)
    suspend fun getRootNodes(id: Long?) = database.nodeDao().getParentId(id)
}

