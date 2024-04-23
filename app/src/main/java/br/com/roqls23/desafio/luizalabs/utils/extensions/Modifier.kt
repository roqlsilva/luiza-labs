package br.com.roqls23.desafio.luizalabs.utils.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.customBorder(
    topStrokeWidth: Dp = 0.dp,
    endStrokeWidth: Dp = 0.dp,
    bottomStrokeWidth: Dp = 0.dp,
    startStrokeWidth: Dp = 0.dp,
    color: Color
) = composed(
    factory = {
        val density = LocalDensity.current
        val topStrokeWidthPx = density.run { topStrokeWidth.toPx() }
        val startStrokeWidthPx = density.run { startStrokeWidth.toPx() }
        val endStrokeWidthPx = density.run { endStrokeWidth.toPx() }
        val bottomStrokeWidthPx = density.run { bottomStrokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height

            topStrokeWidthPx.takeIf { it > 0 }?.let {
                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = width , y = 0f),
                    strokeWidth = topStrokeWidthPx,
                    cap = StrokeCap.Round
                )
            }

            endStrokeWidthPx.takeIf { it > 0 }?.let {
                drawLine(
                    color = color,
                    start = Offset(x = width, y = 0f),
                    end = Offset(x = width , y = height),
                    strokeWidth = endStrokeWidthPx,
                    cap = StrokeCap.Round
                )
            }

            bottomStrokeWidthPx.takeIf { it > 0 }?.let {
                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = height),
                    end = Offset(x = width , y = height),
                    strokeWidth = bottomStrokeWidthPx,
                    cap = StrokeCap.Round
                )
            }

            startStrokeWidthPx.takeIf { it > 0 }?.let {
                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = 0f , y = height),
                    strokeWidth = startStrokeWidthPx,
                    cap = StrokeCap.Round
                )
            }
        }
    }
)