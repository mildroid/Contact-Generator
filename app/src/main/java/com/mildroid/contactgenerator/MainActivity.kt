package com.mildroid.contactgenerator

import android.Manifest
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mildroid.contactgenerator.core.hasPermissions
import com.mildroid.contactgenerator.core.log
import com.mildroid.contactgenerator.domain.model.state.IdleState
import com.mildroid.contactgenerator.domain.model.state.MainStateEvent
import com.mildroid.contactgenerator.domain.model.state.MainViewState
import com.mildroid.contactgenerator.ui.theme.ComposeMainTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val output = mutableStateOf("")

    init {
        lifecycleScope.launchWhenStarted {
            output.value += appDescription()

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect(::stateListener)
            }
        }
    }

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
        val scope = rememberCoroutineScope()
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A0A))
        ) {
            var output by remember { output }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(12f)
                    .height(IntrinsicSize.Max)
                    .verticalScroll(state = scrollState)
            ) {
                Text(
                    text = output,
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
                singleLine = true,
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
                    commander(textField.value.text)
                    output += ("\n$ " + textField.value.text)

                    scope.launch {
                        scrollState.animateScrollTo(scrollState.maxValue + 150)
                    }
                    textField.value = TextFieldValue("")
                })
            )

        }
    }

    private fun stateListener(state: MainViewState) {
        output.value += when (state) {
            is MainViewState.Canceled -> "\n${state.workerInfo.id} was canceled."
            is MainViewState.Finished -> "\n${state.workerInfo.id} was finished successfully."
            is MainViewState.Idle -> {
                when (state.idleState) {
                    IdleState.GenerateHelp -> getString(R.string.generate_description)
                    IdleState.Help -> getString(R.string.app_description)
                    IdleState.Permission -> getString(R.string.permission_warning)
                    IdleState.Idle -> ""
                    is IdleState.Invalid -> "\n\$${
                        (state.idleState as IdleState.Invalid)
                            .command.split(" ").first()
                    } is not" +
                            " a valid command. see \$help for more information."
                }
            }
            is MainViewState.Working -> "\n${state.workerInfo.id} is in progress"
        }
    }

    private fun commander(command: String) {
        viewModel.onEvent(
            when (command.split(" ")[0]) {
                "generate" -> {
                    if (hasPermissions(Manifest.permission.WRITE_CONTACTS)) {
                        MainStateEvent.Generate(command.removePrefix("generate "))
                    } else {
                        MainStateEvent.Help("permission")
                    }
                }
                "help" -> MainStateEvent.Help(command.removePrefix("help "))
                "export" -> MainStateEvent.Export(command.removePrefix("export "))
                "cancel" -> MainStateEvent.Cancel
                "clean" -> MainStateEvent.Clean
                else -> MainStateEvent.NotValid(command)
            }
        )
    }

    private fun appDescription(): String {
        return getString(R.string.app_name) +
                " v" + BuildConfig.VERSION_NAME +
                getString(R.string.app_description)
    }
}