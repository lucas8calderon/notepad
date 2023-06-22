package com.example.blocodenotascompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blocodenotascompose.datastore.StoreAnotacao
import com.example.blocodenotascompose.ui.theme.BLACK
import com.example.blocodenotascompose.ui.theme.BlocoDeNotasComposeTheme
import com.example.blocodenotascompose.ui.theme.WHITE
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlocoDeNotasComposeTheme {
                BlocoDeNotasComponentes()
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BlocoDeNotasComponentes() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val storeAnotacao = StoreAnotacao(context)
    val anotacaoSalva = storeAnotacao.getAnotacao.collectAsState(initial = "")

    var anotacao by remember {
        mutableStateOf("")

    }

    anotacao = anotacaoSalva.value

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = BLACK,

                ) {
                Text(
                    text = "Bloco de Notas",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = WHITE,
                    modifier = Modifier
                        .padding(start = 12.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        storeAnotacao.salvarAnotacao(anotacao)
                        Toast.makeText(context, "Anotação salva com sucesso!", Toast.LENGTH_SHORT)
                            .show()
                    }

                },
                backgroundColor = BLACK,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp
                )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_salvar),
                    contentDescription = "Icone Salvar"
                )

            }
        }) {

        Column {
            TextField(
                value = anotacao,
                onValueChange = {
                    anotacao = it
                },
                label = {
                    Text(text = "Digite a sua anotação...")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),

                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = WHITE,
                    cursorColor = BLACK,
                    focusedLabelColor = WHITE,
                    focusedIndicatorColor = WHITE

                )

            )


        }

    }
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    BlocoDeNotasComposeTheme {
        BlocoDeNotasComponentes()
    }
}