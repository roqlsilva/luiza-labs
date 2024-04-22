package br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.DataState
import br.com.roqls23.desafio.luizalabs.core.domain.usecase.FindDistrictsByUfUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDeliveriesViewModel @Inject constructor(
    application: Application,
    private val findDistrictsByUfUseCase: FindDistrictsByUfUseCase
): AndroidViewModel(application) {

    fun findDistricts() = viewModelScope.launch {
        findDistrictsByUfUseCase("AM").onEach { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    Log.d("RESULT_REQUEST", "districts: ${dataState.data}")
                }

                is DataState.Error -> {
                    dataState.throwable.printStackTrace()
                }

                is DataState.Loading -> {
                    Log.d("RESULT_REQUEST", "Loading...")
                }
            }
        }.catch { e ->
            e.printStackTrace()
        }.launchIn(viewModelScope)
    }
}