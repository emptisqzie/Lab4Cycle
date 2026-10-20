package com.example.lab4cycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.lab4cycle.ui.theme.Lab4CycleTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Lab4CycleTheme {
                CycleLabScreen()
            }
        }
    }
}


@Composable
fun CycleLabScreen() {
    var epsilonText by remember { mutableStateOf("0.000001") }
    var result by remember {
        mutableStateOf<CalculationResult?>(null)
    }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Вычисление суммы обратных факториалов",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = epsilonText,
            onValueChange = {
                epsilonText = it
                error = ""
            },
            label = { Text("Точность ε") },
            supportingText = {
                Text("Например, 0.000001")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val epsilon = epsilonText
                    .trim()
                    .replace(',', '.')
                    .toDoubleOrNull()

                if (
                    epsilon == null ||
                    !epsilon.isFinite() ||
                    epsilon <= 0.0 ||
                    epsilon > 1.0
                ) {
                    error = "Введите число больше 0 и не больше 1."
                    result = null
                } else {
                    result = calculateSum(epsilon)
                    error = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Вычислить")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                result = null
                error = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Очистить результат")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }

        result?.let { calculation ->
            Text(
                text = "Сумма: ${
                    String.format(
                        java.util.Locale.US,
                        "%.12f",
                        calculation.sum
                    )
                }"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Последнее слагаемое: ${
                    String.format(
                        java.util.Locale.US,
                        "%.12e",
                        calculation.lastTerm
                    )
                }"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Количество повторений: ${calculation.iterations}"
            )
        }
    }
}


fun factorial(n: Int): Double {
    var result = 1.0

    for (i in 1..n) {
        result *= i
    }

    return result
}
data class CalculationResult(
    val sum: Double,
    val lastTerm: Double,
    val iterations: Int
)

fun calculateSum(epsilon: Double): CalculationResult {
    var sum = 0.0
    var n = 1
    var term = 1.0
    var lastTerm = 0.0
    var iterations = 0

    while (true) {
        if (kotlin.math.abs(term) < epsilon) {
            break
        }

        sum += term
        lastTerm = term
        iterations++

        n += 2
        term = 1.0 / factorial(n)
    }

    return CalculationResult(
        sum = sum,
        lastTerm = lastTerm,
        iterations = iterations
    )
}

@Preview(showBackground = true)
@Composable
fun CycleLabScreenPreview() {
    Lab4CycleTheme {
        CycleLabScreen()
    }
}