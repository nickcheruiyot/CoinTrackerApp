package com.example.coinpulseapp2.presentation.components
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
@Composable
fun SparklineGraph(
    data: List<String>,
    color: Color
) {
    if (data.isEmpty()) return

    val floatData = data.mapNotNull { it.toFloatOrNull() }
    if (floatData.size < 2) return

    val maxValue = floatData.maxOrNull() ?: 0f
    val minValue = floatData.minOrNull() ?: 0f
    val range = (maxValue - minValue).takeIf { it != 0f } ?: 1f

    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path()
        val fillPath = Path()

        val stepX = size.width / (floatData.size - 1)
        val points = floatData.mapIndexed { index, value ->
            Offset(
                x = index * stepX,
                y = size.height - ((value - minValue) / range) * size.height
            )
        }

        if (points.isNotEmpty()) {
            path.moveTo(points.first().x, points.first().y)
            for (i in 1 until points.size) {
                val prev = points[i - 1]
                val curr = points[i]
                val midX = (prev.x + curr.x) / 2
                val midY = (prev.y + curr.y) / 2
                path.quadraticBezierTo(prev.x, prev.y, midX, midY)
            }

            // Create fill area under the curve
            fillPath.addPath(path)
            fillPath.lineTo(points.last().x, size.height)
            fillPath.lineTo(points.first().x, size.height)
            fillPath.close()

            // Gradient fill (fade effect)
            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        color.copy(alpha = 0.3f),
                        color.copy(alpha = 0f)
                    ),
                    startY = 0f,
                    endY = size.height
                )
            )

            // Main sparkline stroke
            drawPath(
                path = path,
                color = color,
                style = Stroke(
                    width = 3f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}