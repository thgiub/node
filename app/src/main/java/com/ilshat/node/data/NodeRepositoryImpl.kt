package com.ilshat.node.data

import android.util.Log
import com.ilshat.node.data.db.LocalDataSource
import com.ilshat.node.domain.Node
import com.ilshat.node.domain.NodeRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class NodeRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
) : NodeRepository {
    override suspend fun insertNode(node: Node) {
        localDataSource.insertNode(node)
    }

    override suspend fun deleteNode(node: Node) {
        localDataSource.deleteNode(node)
    }

    override  fun getRootNodes(): Flow<List<Node>> {
        return localDataSource.getNodes().toDbResult()
    }

    override  fun getNodes(parentId: Long?): Flow<List<Node>> {
        return localDataSource.getNodes(parentId).toDbResult()
    }
}

fun <T> Flow<T>.toDbResult() = flowOn(Dispatchers.IO).catch { Log.e("Room error", it.toString()) }
