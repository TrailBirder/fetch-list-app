package com.example.fetch_list_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fetch_list_app.data.Item
import com.example.fetch_list_app.ui.theme.FetchlistappTheme
import com.example.fetch_list_app.viewmodel.ItemGroup
import com.example.fetch_list_app.viewmodel.MainViewModel
import androidx.compose.foundation.ExperimentalFoundationApi


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FetchlistappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        val groups by viewModel.groups.collectAsState()
                        val error by viewModel.error.collectAsState()

                        if (error != null) {
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                        }

                        ItemList(groups)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemList(groups: List<ItemGroup>) {
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        groups.forEach { group ->
            stickyHeader {
                Text(
                    text = "List ID: ${group.listId}",
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillParentMaxWidth()
                        .padding(8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(group.items) { item ->
                ItemRow(item)
            }
        }
    }
}

@Composable
fun ItemRow(item: Item) {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
    ) {
        Text(text = item.name ?: "", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Item ID: ${item.id}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}
