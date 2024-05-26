package dev.nunu.mobile.konkuk.room

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UsersScreen(
    viewModel: UserViewModel = viewModel(modelClass = UserViewModel::class.java)
) {
    val users by viewModel.users.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "201811218 이현우")
        Row {
            Button(onClick = {
                // viewModel.insert()
            }) {
                Text(text = "Insert")
            }
            Button(onClick = {
                // viewModel.delete()
            }) {
                Text(text = "Delete")
            }
        }
    }
}
