package com.ilshat.node.domain

import kotlinx.coroutines.flow.Flow

interface NodeRepository {
    suspend fun insertNode(node: Node)
    suspend fun deleteNode(node: Node)
    fun getRootNodes(): Flow<List<Node>>
    fun getNodes(parentId: Long?): Flow<List<Node>>
    suspend fun getRootNodes(parentId: Long?): Long?
}