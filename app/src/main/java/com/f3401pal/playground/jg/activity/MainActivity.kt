package com.f3401pal.playground.jg.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.f3401pal.playground.jg.model.MainViewModel
import com.f3401pal.playground.jg.ui.theme.BalanceTrackTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BalanceTrackTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Root(viewModel: MainViewModel = viewModel()) {
    BottomSheetScaffold(
        sheetContent = {
            Text("Add Transaction")
        }
    ) {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            BalanceBox()
            TransactionList()
        }
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {

                }
            ) {
                Icon(
                    Icons.Filled.Add,
                    "Add Transaction"
                )
            }
        }
    }
}

@Composable
fun BalanceBox() {

}

@Composable
fun TransactionList() {

}