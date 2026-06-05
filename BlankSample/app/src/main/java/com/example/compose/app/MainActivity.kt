package com.example.compose.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.app.ui.theme.AppTheme

val tag = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppUI()
        }
    }
}

@Composable
fun AppUI(modifier: Modifier = Modifier) {

    val viewModel = viewModel<AppViewModel>()

    AppTheme {
        Log.i(tag, "AppTheme: called")
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Log.i(tag, "Scaffold: called")
            Content(
                modifier = Modifier.padding(innerPadding),
                viewModel = viewModel,
            )
        }
    }
}

@Composable
fun Content(modifier: Modifier = Modifier, viewModel: AppViewModel) {

    Log.i(tag, "Content: called")

    val state = viewModel.stateFlow.collectAsStateWithLifecycle()

    val items = state.value.names

    Column(modifier = modifier) {

        Log.i(tag, "Column: called")

        var toggleState by remember { mutableStateOf(false) }

        Checkbox(checked = toggleState, onCheckedChange = { toggleState = it })

        ContactList(Contacts(false, items))

        ChangeButton(onClick = {
            val newList = mutableListOf<String>()
            items.forEach { newList.add(it) }
            newList.add("Oleg")
            viewModel.changeState(names = newList)
        })

    }

}

@Composable
fun ContactList(isLoading: Boolean, names: List<String>) {
    if (names.isNotEmpty()) {
        Text("items List:")
        names.forEach { item ->
            Text(item)
        }
    } else if (isLoading) {
        CircularProgressIndicator()
    }
}

@Composable
fun ContactList(contacts: Contacts) {
    Log.i(tag, "ContactList: called")
    if (contacts.isLoading) {
        CircularProgressIndicator()
    } else {
        Text("items List:")
        contacts.names.forEach { item ->
            Text(item)
        }
    }
}

@Composable
fun ChangeButton(onClick: () -> Unit) {
    Log.i(tag, "ChangeButton: called")
    Button(onClick = onClick) {
        Text(text = "Add item")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AppUI()
}