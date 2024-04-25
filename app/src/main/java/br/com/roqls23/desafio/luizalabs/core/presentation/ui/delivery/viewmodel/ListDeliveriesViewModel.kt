package br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DeliveryEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.DataState
import br.com.roqls23.desafio.luizalabs.core.domain.usecase.FindAllDeliveriesUseCase
import br.com.roqls23.desafio.luizalabs.core.domain.usecase.RemoveDeliveryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDeliveriesViewModel @Inject constructor(
    application: Application,
    private val findAllDeliveriesUseCase: FindAllDeliveriesUseCase,
    private val removeDeliveryUseCase: RemoveDeliveryUseCase,
): AndroidViewModel(application) {

    private val _deliveries = MutableStateFlow<List<DeliveryEntity>>(emptyList())
    val deliveries: StateFlow<List<DeliveryEntity>> get() = _deliveries.asStateFlow()

    fun findDeliveries() = viewModelScope.launch {
        findAllDeliveriesUseCase().onEach { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    _deliveries.value = dataState.data
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

    fun deleteDelivery(id: Long) = viewModelScope.launch {
        removeDeliveryUseCase(id).onEach { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    findDeliveries()
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