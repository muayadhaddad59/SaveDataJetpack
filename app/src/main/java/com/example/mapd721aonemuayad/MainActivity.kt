package com.example.mapd721aonemuayad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mapd721aonemuayad.datastore.datastore.UserPreferencesDataStore
import com.example.mapd721aonemuayad.ui.theme.MAPD721AOneMuayadTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAPD721AOneMuayadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    // context
    val context = LocalContext.current
    // Scope
    val scope = rememberCoroutineScope()
    // Database email
    val dataBase = UserPreferencesDataStore(context)

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    val usernameData = dataBase.getUsername
    val usernameState by usernameData.collectAsState(initial = "")
    val emailState by dataBase.getUserId.collectAsState(initial = "")
    var nameStorge by remember {
        mutableStateOf("")
    }
    var idStorge by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
       OutlinedTextField(value = username, onValueChange = {username = it}, modifier = Modifier.fillMaxWidth(),
           placeholder = { Text(text = "Username")}
           )
       Spacer(modifier = Modifier.padding(top = 12.dp))
       OutlinedTextField(value = email, onValueChange = {email = it}, modifier = Modifier.fillMaxWidth(),
           placeholder = { Text(text = "Email")}
       )
       Spacer(modifier = Modifier.padding(top = 12.dp))
       OutlinedTextField(value = id, onValueChange = {id = it}, modifier = Modifier.fillMaxWidth(),
           placeholder = { Text(text = "ID")}
       )
       Spacer(modifier = Modifier.padding(top = 20.dp))

       ButtonRow(
        onClear = {
            username = ""
            email = ""
            id = ""
            scope.launch {
                dataBase.clearAllData()
            }
            nameStorge = ""
            idStorge = ""
        },
           onSave = {
               scope.launch {
                   dataBase.saveUserDetails(username = username, email = email, id = id)
               }
           },
           onLoad = {
               nameStorge = usernameState.toString()
               idStorge = emailState.toString()
           }
       )
       Spacer(modifier = Modifier.height( 350.dp))

       GenerateWidget(name = nameStorge, id = idStorge)

   }


}

@Composable
fun GenerateWidget(name: String?, id: String?) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .border(width = 2.dp, color = Color.Black)
        .padding(20.dp)) {
        Column {
            name?.let { Text(text = it) }
            id?.let { Text(text = it) }
        }
    }
}

@Composable
fun ButtonRow(
onClear:() -> Unit,
    onSave: ()-> Unit,
    onLoad: () -> Unit
) {
    Row {
        Button(onClick = {
onLoad()
        }) {
            Text(text = "Load")
        }
        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        Row {
            Button(onClick = {
               onSave()
            }) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Row {
                Button(onClick = {
                    onClear()
                }) {
                    Text(text = "Clear")
                }
                Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            }}}
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MAPD721AOneMuayadTheme {
        Greeting()
    }
}