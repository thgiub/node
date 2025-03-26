package com.ilshat.node.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.MessageDigest

@Entity(tableName = "nodes")
data class Node(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String = "No have Node",
    val parentId: Long? = null,
    val children: List<Node> = emptyList()
)

fun Node.generateName(): String {
    val hash = MessageDigest.getInstance("SHA-256").digest(name.toByteArray())
    return hash.takeLast(20).joinToString("") { "%02x".format(it) }
}
