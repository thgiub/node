package com.ilshat.node.ui.screen

import android.util.Log
import androidx.compose.foundation.interaction.Interaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ilshat.node.domain.Node
import com.ilshat.node.domain.NodeRepository
import com.ilshat.node.domain.generateName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NodeViewModel @Inject constructor(
    private val nodeRepository: NodeRepository
) : ViewModel() {

    private val mutableStateFlow = MutableStateFlow(NodeState(emptyList()))
    val stateFlow: StateFlow<NodeState> = mutableStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            nodeRepository.getRootNodes().collect { nodes ->
                mutableStateFlow.update { state ->
                    state.copy(
                        nodes = nodes
                    )
                }
            }
        }
    }


    private fun getPreviewNodesList(parentId: Long?) = viewModelScope.launch(Dispatchers.IO) {
        val idParent = nodeRepository.getRootNodes(parentId)
        if (idParent == 1L) {
            nodeRepository.getRootNodes().collect { nodes ->
                mutableStateFlow.update { state ->
                    state.copy(
                        nodes = nodes
                    )
                }
            }
        } else
            nodeRepository.getNodes(idParent).collect { nodes ->
                Log.i("getNodes", "getNodes: $parentId")
                if (nodes.isNotEmpty())
                    mutableStateFlow.update { state ->
                        state.copy(
                            nodes = nodes
                        )
                    }
            }
    }


    private fun getNodes(parentId: Long?) = viewModelScope.launch(Dispatchers.IO) {
        nodeRepository.getNodes(parentId).collect { nodes ->
            Log.i("getNodes", "getNodes: $parentId")
            if (nodes.isNotEmpty())
                mutableStateFlow.update { state ->
                    state.copy(
                        nodes = nodes
                    )
                }
        }
    }

    private fun addNewRootNode() {
        viewModelScope.launch(Dispatchers.IO) {
            val newNode = Node(name = Node().generateName())
            nodeRepository.insertNode(newNode)
        }
    }


    private fun addNode(parentId: Long?) {
        Log.i("addNode", "getNodes:$parentId ")
        viewModelScope.launch(Dispatchers.IO) {
            val newNode = Node(name = Node().generateName(), parentId = parentId)
            nodeRepository.insertNode(newNode)
        }
    }

    fun deleteNode(node: Node) {
        viewModelScope.launch(Dispatchers.IO) {
            nodeRepository.deleteNode(node)
        }
    }

    data class NodeState(
        val nodes: List<Node>
    )

    sealed interface NodeInteraction : Interaction {
        data object AddRootNode : NodeInteraction
        data class DeleteNote(val node: Node) : NodeInteraction
        data class AddNote(val parentId: Long?) : NodeInteraction
        data class OpenNodeById(val nodeId: Long?) : NodeInteraction
        data class GetPreviewNodeList(val nodeId: Long?) : NodeInteraction
    }

    fun interaction(interaction: NodeInteraction) =
        when (interaction) {
            is NodeInteraction.AddNote -> addNode(interaction.parentId)
            is NodeInteraction.DeleteNote -> deleteNode(interaction.node)
            is NodeInteraction.OpenNodeById -> getNodes(interaction.nodeId)
            NodeInteraction.AddRootNode -> addNewRootNode()
            is NodeInteraction.GetPreviewNodeList -> getPreviewNodesList(interaction.nodeId)
        }
}