package br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.roqls23.desafio.luizalabs.core.domain.dto.CreateDeliveryForm
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DistrictEntity
import br.com.roqls23.desafio.luizalabs.core.domain.entity.StateEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.DataState
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.UiState
import br.com.roqls23.desafio.luizalabs.core.domain.usecase.CreateDeliveryUseCase
import br.com.roqls23.desafio.luizalabs.core.domain.usecase.FindDistrictsByUfUseCase
import br.com.roqls23.desafio.luizalabs.core.domain.usecase.FindStatesUseCase
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
class CreateDeliveryViewModel @Inject constructor(
    application: Application,
    private val createDeliveryUseCase: CreateDeliveryUseCase,
    private val findStatesUseCase: FindStatesUseCase,
    private val findDistrictsByUfUseCase: FindDistrictsByUfUseCase
): AndroidViewModel(application) {

    sealed interface CreateDeliveryUiState : UiState {
        object CreatedSuccessfully : CreateDeliveryUiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    private val _districts = MutableStateFlow<List<DistrictEntity>>(emptyList())
    val districts: StateFlow<List<DistrictEntity>> get() = _districts.asStateFlow()

    private val _states = MutableStateFlow<List<StateEntity>>(emptyList())
    val states: StateFlow<List<StateEntity>> get() = _states.asStateFlow()

    fun findStates() = viewModelScope.launch {
        findStatesUseCase().onEach { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    _states.value = dataState.data
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

    fun findDistricts(uf: String) = viewModelScope.launch {
        _districts.value = emptyList()
        findDistrictsByUfUseCase(uf).onEach { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    _districts.value = dataState.data
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

    fun createDelivery(
        data: CreateDeliveryForm
    ) = viewModelScope.launch {
        createDeliveryUseCase(data).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    Log.d("RESULT_REQUEST", "Loading...")
                }

                is DataState.Success -> {
                    _uiState.value = CreateDeliveryUiState.CreatedSuccessfully
                }

                is DataState.Error -> {
                    dataState.throwable.printStackTrace()
                }
            }
        }.catch { e ->
            e.printStackTrace()
        }.launchIn(viewModelScope)
    }
}