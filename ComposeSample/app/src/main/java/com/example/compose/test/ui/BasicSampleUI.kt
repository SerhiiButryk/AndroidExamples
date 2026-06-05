package com.example.compose.test.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.persistentListOf

/**
 * Sample which demonstrates basic recomposition and stability principles in Compose
 */

data class ImmutableClass(val state: Boolean, val message: String)

data class MutableClass(var state: Boolean, var message: String)

@Stable
data class MutableStableClass(var state: Boolean, var message: String, var list: List<String> = mutableListOf("hello"))

@Preview
@Composable
fun SampleUI() {

    Scaffold { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {

           var checked by remember { mutableStateOf(false) }

           ListUI(checked) {
               checked = it
           }
        }

    }
}

@Composable
fun ListUI(state: Boolean, onChange: (Boolean) -> Unit) {

    Column {

        Checkbox(
            checked = state,
            onCheckedChange = {
                onChange(it)
            }
        )

        // It's always recomposed
        //ActionUI(state = false, mutableListOf("String1"))

        // This one is NOT recomposed
        // ActionUI(state = false, listOf("String1"))

        // This one is NOT recomposed
        //ActionUI2(state = ImmutableClass(false, "Some message"))

        // It's always recomposed
        //ActionUI3(state = MutableClass(false, "Some message"))

        // This one is NOT recomposed
        //ActionUI4(state = MutableStableClass(false, "Some message"))

        // This one is NOT recomposed
        //ActionUI5(state = persistentListOf("hello"))
    }
}

@Composable
fun ActionUI(state: Boolean, actions: List<String>) {
    Button(onClick = {}) {
        Text(text = "Click me!")
        Log.i("SampleUI", "ActionUI: $actions $state")
    }
}

@Composable
fun ActionUI2(state: ImmutableClass) {
    Button(onClick = {}) {
        Text(text = "Click me!")
        Log.i("SampleUI", "ActionUI2: $state")
    }
}

@Composable
fun ActionUI3(state: MutableClass) {
    Button(onClick = {}) {
        Text(text = "Click me!")
        Log.i("SampleUI", "ActionUI3: $state")
    }
}

@Composable
fun ActionUI4(state: MutableStableClass) {
    Button(onClick = {}) {
        Text(text = "Click me!")
        Log.i("SampleUI", "ActionUI4: $state")
    }
}

@Composable
fun ActionUI5(state: ImmutableList<String>) {
    Button(onClick = {}) {
        Text(text = "Click me!")
        Log.i("SampleUI", "ActionUI5: $state")
    }
}