package com.example.homework4_2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import kotlinx.coroutines.flow.Flow

@Serializable
data class FunFact (var text:String, var source_url:String?=null)

class MyViewModel(private val repository: FactRepository) : ViewModel()
{
    private val funListMutable = MutableStateFlow(listOf<String>())
    val funListReadOnly : StateFlow<List<String>> = funListMutable
    val allFacts: Flow<List<FactData>> = repository.allFacts

    suspend fun addItem (item: String){
        repository.addItem()
    }
//    val allFacts: StateFlow<List<FactData>> = repository.allFacts.stateIn(
//        scope = repository.scope,
//        started = SharingStarted.WhileSubscribed(),
//        initialValue = listOf()
//    )
}

object FactViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MyViewModel(
                (this[AndroidViewModelFactory.APPLICATION_KEY]
                        as FactApplication).factRepository
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm:MyViewModel by viewModels{ FactViewModelProvider.Factory }
            TodoList(vm)
        }
    }
}

@Composable
fun TodoList(myVM: MyViewModel) {
    Column(Modifier.fillMaxWidth().padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        val observableList by myVM.funListReadOnly.collectAsState()
        var itemText by remember { mutableStateOf("") }
        val facts by myVM.allFacts.collectAsState(initial = emptyList())

        Row {
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch()
                {
                    myVM.addItem(itemText)
                }
            }) {
                Text("Fetch Item")
            }
        }
        Spacer(Modifier.height(20.dp))
        Text("FunFact List",
            modifier = Modifier
            .padding(20.dp),
            fontSize = 24.sp)
        Row{
            LazyColumn {
                items(facts) {factData ->
                    Text(text = factData.text,
                        modifier = Modifier
                            .padding(10.dp),
                        fontSize = 20.sp)
                }
            }
        }
    }
}