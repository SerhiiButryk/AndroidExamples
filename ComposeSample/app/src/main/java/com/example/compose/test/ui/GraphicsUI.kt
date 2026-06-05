package com.example.compose.test.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Canvas drawing example
 */
@Preview(showBackground = true)
@Composable
fun TestUI() {

    Column {

        val gradient = Brush.linearGradient(listOf(Color.Blue, Color.Gray))

        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)) {

            val color = Color.Blue

            drawLine(
                color = color,
                strokeWidth = 10f,
                start = Offset(0f, 0f),
                end = Offset(size.width / 2.toLong(), 0f)
            )

            drawLine(
                color = color,
                strokeWidth = 10f,
                start = Offset(size.width, size.height),
                end = Offset(size.width / 2, size.height)
            )

            val center = size.height / 2 - (size.height / 4)

            drawRect(
                brush = gradient,
                size = size / 2f,
                topLeft = Offset(0f, center)
            )

            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height),
                strokeWidth = 10f
            )
        }
    }

}