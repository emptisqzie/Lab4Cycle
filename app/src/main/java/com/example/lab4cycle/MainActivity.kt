package com.example.lab4cycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab4cycle.ui.theme.Lab4CycleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4CycleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4CycleTheme {
        Greeting("Android")
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
