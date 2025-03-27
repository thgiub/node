package com.ilshat.node.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ilshat.node.domain.Node
import com.ilshat.node.ui.util.collect

@Composable
fun NodeScreen() {
    val viewModel = hiltViewModel<NodeViewModel>()
    val state by viewModel.stateFlow.collectAsState()
    val interaction = remember { MutableInteractionSource() }
    interaction.collect<NodeViewModel.NodeInteraction> { viewModel.interaction(it) }
    NodeScreen(
        state = state,
        interaction = interaction
    )
}

@Composable
fun NodeScreen(
    state: NodeViewModel.NodeState,
    interaction: MutableInteractionSource
) {
    Log.i("NodeScreen", "NodeScreen: ${state.nodes}")
    val scrollState = rememberLazyListState()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            state = scrollState
        ) {
            items(state.nodes.size) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    NodeItem(
                        state.nodes[index],
                        interaction
                    )

                }
            }
        }
        Button(
            onClick = { interaction.tryEmit(NodeViewModel.NodeInteraction.AddRootNode) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text("Add Rood Node")
        }
    }


}

@Composable
fun NodeItem(node: Node, interaction: MutableInteractionSource) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Blue)
            .clickable {
                interaction.tryEmit(
                    NodeViewModel.NodeInteraction.OpenNodeById(node.id)
                )
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = node.name,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(4.dp),
            color = Color.White
        )
        Button(
            onClick = { interaction.tryEmit(NodeViewModel.NodeInteraction.AddNote(node.id)) },
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(4.dp)
        ) {
            Text("Add")
        }
        Button(
            onClick = { interaction.tryEmit(NodeViewModel.NodeInteraction.DeleteNote(node)) },
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(4.dp)
        ) {
            Text("Delete")
        }
        Button(
            onClick = {
                if (node.parentId != null)
                    interaction.tryEmit(NodeViewModel.NodeInteraction.GetPreviewNodeList(node.parentId))
                else
                    Toast.makeText(context, "Нет Родителя", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(4.dp)
        ) {
            Text("Parent")
        }
    }

}

