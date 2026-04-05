package com.peumax.calculatetools.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peumax.calculatetools.ui.components.ClearButton
import com.peumax.calculatetools.ui.components.DimensionField
import com.peumax.calculatetools.ui.theme.BackgroundLight
import com.peumax.calculatetools.ui.theme.ConeTeal
import com.peumax.calculatetools.ui.theme.DiagramDim
import com.peumax.calculatetools.ui.theme.DiagramLabel
import com.peumax.calculatetools.ui.theme.PeumaxBlue
import com.peumax.calculatetools.ui.theme.PeumaxNavy
import com.peumax.calculatetools.ui.theme.TextPrimary
import com.peumax.calculatetools.viewmodel.CalculatorUiState
import com.peumax.calculatetools.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val state = uiState as? CalculatorUiState.Filled ?: CalculatorUiState.Filled()

    val d1FocusRequester = remember { FocusRequester() }
    val hFocusRequester = remember { FocusRequester() }
    val d2FocusRequester = remember { FocusRequester() }

    Scaffold(
        containerColor = BackgroundLight
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ResultCard(result = state.result)
            ConeDiagramCard(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                topDiameter = state.topDiameter,
                bottomDiameter = state.bottomDiameter,
                height = state.height,
                onD1Click = { d1FocusRequester.requestFocus() },
                onHClick = { hFocusRequester.requestFocus() },
                onD2Click = { d2FocusRequester.requestFocus() }
            )
            InputSection(
                topDiameter = state.topDiameter,
                bottomDiameter = state.bottomDiameter,
                height = state.height,
                onTopDiameterChange = viewModel::onTopDiameterChange,
                onBottomDiameterChange = viewModel::onBottomDiameterChange,
                onHeightChange = viewModel::onHeightChange,
                d1FocusRequester = d1FocusRequester,
                hFocusRequester = hFocusRequester,
                d2FocusRequester = d2FocusRequester
            )
            ClearButton(onClick = {
                viewModel.clearInputs()
                d1FocusRequester.requestFocus()
            })
        }
    }
}

@Composable
private fun ResultCard(result: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PeumaxNavy)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 40.dp)
        ) {
            Text(
                text = "ÂNGULO",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = if (result.isEmpty()) "—" else result,
                    color = Color.White,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 80.sp
                )
                if (result.isNotEmpty()) {
                    Text(
                        text = "°",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ConeDiagramCard(
    modifier: Modifier = Modifier,
    topDiameter: String = "",
    bottomDiameter: String = "",
    height: String = "",
    onD1Click: () -> Unit = {},
    onHClick: () -> Unit = {},
    onD2Click: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        ConeDiagram(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            topDiameter = topDiameter,
            bottomDiameter = bottomDiameter,
            height = height,
            onD1Click = onD1Click,
            onHClick = onHClick,
            onD2Click = onD2Click
        )
    }
}

@Composable
private fun ConeDiagram(
    modifier: Modifier = Modifier,
    topDiameter: String = "",
    bottomDiameter: String = "",
    height: String = "",
    onD1Click: () -> Unit = {},
    onHClick: () -> Unit = {},
    onD2Click: () -> Unit = {}
) {
    val textMeasurer = rememberTextMeasurer()
    val teal = ConeTeal
    val dimColor = DiagramDim
    val labelStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = DiagramLabel
    )
    val strokeWidth = 1.5f

    var canvasSize by remember { mutableStateOf(Size.Zero) }

    Canvas(
        modifier = modifier
            .onSizeChanged { canvasSize = Size(it.width.toFloat(), it.height.toFloat()) }
            .pointerInput(canvasSize) {
                detectTapGestures { offset ->
                    val w = canvasSize.width
                    val h = canvasSize.height
                    if (w == 0f || h == 0f) return@detectTapGestures

                    val topWidth = w * 0.22f
                    val bottomWidth = w * 0.70f
                    val coneTop = h * 0.12f
                    val coneBottom = h * 0.82f
                    val cx = w / 2f
                    val hX = cx + bottomWidth / 2 + 18f
                    val hitPad = 48f

                    val d1Area = Rect(
                        left = cx - topWidth / 2 - hitPad,
                        top = coneTop - 80f,
                        right = cx + topWidth / 2 + hitPad,
                        bottom = coneTop - 4f
                    )
                    val d2Area = Rect(
                        left = cx - bottomWidth / 2,
                        top = coneBottom + 8f,
                        right = cx + bottomWidth / 2,
                        bottom = coneBottom + 80f
                    )
                    val hMid = (coneTop + coneBottom) / 2f
                    val hArea = Rect(
                        left = hX,
                        top = hMid - hitPad,
                        right = hX + 120f,
                        bottom = hMid + hitPad
                    )

                    when {
                        d1Area.contains(offset) -> onD1Click()
                        d2Area.contains(offset) -> onD2Click()
                        hArea.contains(offset) -> onHClick()
                    }
                }
            }
    ) {
        val w = size.width
        val h = size.height

        val topWidth = w * 0.22f
        val bottomWidth = w * 0.70f
        val coneTop = h * 0.12f
        val coneBottom = h * 0.82f
        val cx = w / 2f

        val conePath = Path().apply {
            moveTo(cx - topWidth / 2, coneTop)
            lineTo(cx + topWidth / 2, coneTop)
            lineTo(cx + bottomWidth / 2, coneBottom)
            lineTo(cx - bottomWidth / 2, coneBottom)
            close()
        }
        drawPath(conePath, color = teal)

        val d1Y = coneTop - 10f
        drawLine(dimColor, Offset(cx - topWidth / 2, d1Y), Offset(cx + topWidth / 2, d1Y), strokeWidth)
        drawLine(dimColor, Offset(cx - topWidth / 2, d1Y - 4f), Offset(cx - topWidth / 2, d1Y + 4f), strokeWidth)
        drawLine(dimColor, Offset(cx + topWidth / 2, d1Y - 4f), Offset(cx + topWidth / 2, d1Y + 4f), strokeWidth)

        val d2Y = coneBottom + 12f
        drawLine(dimColor, Offset(cx - bottomWidth / 2, d2Y), Offset(cx + bottomWidth / 2, d2Y), strokeWidth)
        drawLine(dimColor, Offset(cx - bottomWidth / 2, d2Y - 4f), Offset(cx - bottomWidth / 2, d2Y + 4f), strokeWidth)
        drawLine(dimColor, Offset(cx + bottomWidth / 2, d2Y - 4f), Offset(cx + bottomWidth / 2, d2Y + 4f), strokeWidth)

        val hX = cx + bottomWidth / 2 + 18f
        drawLine(dimColor, Offset(hX, coneTop), Offset(hX, coneBottom), strokeWidth)
        drawLine(dimColor, Offset(hX - 4f, coneTop), Offset(hX + 4f, coneTop), strokeWidth)
        drawLine(dimColor, Offset(hX - 4f, coneBottom), Offset(hX + 4f, coneBottom), strokeWidth)

        val d1Label = if (topDiameter.isNotEmpty()) "${topDiameter}mm" else "D1"
        val d1Text = textMeasurer.measure(d1Label, labelStyle)
        drawText(d1Text, topLeft = Offset(cx - d1Text.size.width / 2f, d1Y - d1Text.size.height - 2f))

        val d2Label = if (bottomDiameter.isNotEmpty()) "${bottomDiameter}mm" else "D2"
        val d2Text = textMeasurer.measure(d2Label, labelStyle)
        drawText(d2Text, topLeft = Offset(cx - d2Text.size.width / 2f, d2Y + 2f))

        val hLabel = if (height.isNotEmpty()) "${height}mm" else "H"
        val hText = textMeasurer.measure(hLabel, labelStyle)
        drawText(hText, topLeft = Offset(hX + 6f, (coneTop + coneBottom) / 2f - hText.size.height / 2f))
    }
}

@Composable
private fun InputSection(
    topDiameter: String,
    bottomDiameter: String,
    height: String,
    onTopDiameterChange: (String) -> Unit,
    onBottomDiameterChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    d1FocusRequester: FocusRequester,
    hFocusRequester: FocusRequester,
    d2FocusRequester: FocusRequester
) {
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = PeumaxBlue,
        cursorColor = PeumaxBlue,
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary
    )

    DimensionField(
        shortLabel = "D1",
        label = "Diâmetro Superior",
        value = topDiameter,
        onValueChange = onTopDiameterChange,
        colors = fieldColors,
        focusRequester = d1FocusRequester
    )
    DimensionField(
        shortLabel = "H",
        label = "Altura",
        value = height,
        onValueChange = onHeightChange,
        colors = fieldColors,
        focusRequester = hFocusRequester
    )
    DimensionField(
        shortLabel = "D2",
        label = "Diâmetro Inferior",
        value = bottomDiameter,
        onValueChange = onBottomDiameterChange,
        colors = fieldColors,
        focusRequester = d2FocusRequester
    )
}
