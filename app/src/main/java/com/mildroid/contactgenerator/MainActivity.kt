package com.mildroid.contactgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mildroid.contactgenerator.ui.theme.ComposeMainTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeMainTheme {
                Surface {
                    Terminal()
                }

            }
        }
    }


    @Composable
    fun Terminal() {
        val focusManager = LocalFocusManager.current
        val scope = rememberCoroutineScope()
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A0A))
        ) {
            var name by remember { mutableStateOf("Contact Generator v0.1") }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(12f)
                    .height(IntrinsicSize.Max)
                    .verticalScroll(state = scrollState)
            ) {
                Text(
                    text = name,
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)

                )
            }


            val textField =
                remember { mutableStateOf(TextFieldValue()) }

            TextField(
                value = textField.value,
                onValueChange = {
                    textField.value = it
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                visualTransformation = PrefixTransformation("$ "),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    name += ("\n$ " + textField.value.text)

                    scope.launch {
                        scrollState.animateScrollTo(scrollState.maxValue + 50)
                    }
                    focusManager.clearFocus()
                    textField.value = TextFieldValue("")
                })
            )

        }
    }

}