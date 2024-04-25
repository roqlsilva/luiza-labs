package br.com.roqls23.desafio.luizalabs.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.MainScreen
import br.com.roqls23.desafio.luizalabs.core.presentation.ui.theme.DesafioLuizaLabsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesafioLuizaLabsTheme {
                MainScreen()
            }
        }
    }
}
