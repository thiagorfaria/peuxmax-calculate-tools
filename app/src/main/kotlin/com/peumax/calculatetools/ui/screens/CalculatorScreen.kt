package com.peumax.calculatetools.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peumax.calculatetools.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFFF5F7FA)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ResultCard(result = uiState.result)
            ConeDiagramCard(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                topDiameter = uiState.topDiameter,
                bottomDiameter = uiState.bottomDiameter,
                height = uiState.height
            )
            InputSection(
                topDiameter = uiState.topDiameter,
                bottomDiameter = uiState.bottomDiameter,
                height = uiState.height,
                onTopDiameterChange = viewModel::onTopDiameterChange,
                onBottomDiameterChange = viewModel::onBottomDiameterChange,
                onHeightChange = viewModel::onHeightChange
            )
        }
    }
}

@Composable
private fun ResultCard(result: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D2B6E))
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
    height: String = ""
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
            height = height
        )
    }
}

@Composable
private fun ConeDiagram(
    modifier: Modifier = Modifier,
    topDiameter: String = "",
    bottomDiameter: String = "",
    height: String = ""
) {
    val textMeasurer = rememberTextMeasurer()
    val teal = Color(0xFF0097A7)
    val dimColor = Color(0xFF90A4AE)
    val labelStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF546E7A)
    )
    val strokeWidth = 1.5f

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val topWidth = w * 0.22f
        val bottomWidth = w * 0.70f
        val coneTop = h * 0.12f
        val coneBottom = h * 0.82f
        val cx = w / 2f

        // Cone trapezoid fill
        val conePath = Path().apply {
            moveTo(cx - topWidth / 2, coneTop)
            lineTo(cx + topWidth / 2, coneTop)
            lineTo(cx + bottomWidth / 2, coneBottom)
            lineTo(cx - bottomWidth / 2, coneBottom)
            close()
        }
        drawPath(conePath, color = teal)

        // D1 dimension line (above top edge)
        val d1Y = coneTop - 10f
        drawLine(dimColor, Offset(cx - topWidth / 2, d1Y), Offset(cx + topWidth / 2, d1Y), strokeWidth)
        drawLine(dimColor, Offset(cx - topWidth / 2, d1Y - 4f), Offset(cx - topWidth / 2, d1Y + 4f), strokeWidth)
        drawLine(dimColor, Offset(cx + topWidth / 2, d1Y - 4f), Offset(cx + topWidth / 2, d1Y + 4f), strokeWidth)

        // D2 dimension line (below bottom edge)
        val d2Y = coneBottom + 12f
        drawLine(dimColor, Offset(cx - bottomWidth / 2, d2Y), Offset(cx + bottomWidth / 2, d2Y), strokeWidth)
        drawLine(dimColor, Offset(cx - bottomWidth / 2, d2Y - 4f), Offset(cx - bottomWidth / 2, d2Y + 4f), strokeWidth)
        drawLine(dimColor, Offset(cx + bottomWidth / 2, d2Y - 4f), Offset(cx + bottomWidth / 2, d2Y + 4f), strokeWidth)

        // H dimension line (right side)
        val hX = cx + bottomWidth / 2 + 18f
        drawLine(dimColor, Offset(hX, coneTop), Offset(hX, coneBottom), strokeWidth)
        drawLine(dimColor, Offset(hX - 4f, coneTop), Offset(hX + 4f, coneTop), strokeWidth)
        drawLine(dimColor, Offset(hX - 4f, coneBottom), Offset(hX + 4f, coneBottom), strokeWidth)

        // Labels
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
    onHeightChange: (String) -> Unit
) {
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color(0xFF1565C0),
        cursorColor = Color(0xFF1565C0),
        focusedTextColor = Color(0xFF1A1A1A),
        unfocusedTextColor = Color(0xFF1A1A1A)
    )

    DimensionField(
        shortLabel = "D1",
        label = "Diâmetro Superior",
        value = topDiameter,
        onValueChange = onTopDiameterChange,
        colors = fieldColors
    )
    DimensionField(
        shortLabel = "H",
        label = "Altura",
        value = height,
        onValueChange = onHeightChange,
        colors = fieldColors
    )
    DimensionField(
        shortLabel = "D2",
        label = "Diâmetro Inferior",
        value = bottomDiameter,
        onValueChange = onBottomDiameterChange,
        colors = fieldColors
    )
}

@Composable
private fun DimensionField(
    shortLabel: String,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    colors: TextFieldColors
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = shortLabel,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D2B6E),
            fontSize = 22.sp,
            modifier = Modifier.width(40.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, fontSize = 20.sp, color = Color(0xFF1A1A1A)) },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = colors,
            textStyle = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Start),
            suffix = { Text("mm", color = Color(0xFF90A4AE), fontSize = 18.sp) }
        )
    }
}
