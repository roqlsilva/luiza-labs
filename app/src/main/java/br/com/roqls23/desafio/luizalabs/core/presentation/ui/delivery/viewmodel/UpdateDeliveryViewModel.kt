package br.com.roqls23.desafio.luizalabs.core.presentation.ui.delivery.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.roqls23.desafio.luizalabs.core.domain.dto.CreateDeliveryForm
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DeliveryEntity
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DistrictEntity
import br.com.roqls23.desafio.luizalabs.core.domain.entity.StateEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.DataState
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.UiState
import br.com.roqls23.desafio.luizalabs.core.domain.usecase.FindDeliveryByIdUseCase
import br.com.roqls23.desafio.luizalabs.core.domain.usecase.FindDistrictsByUfUseCase
import br.com.roqls23.desafio.luizalabs.core.domain.usecase.FindStatesUseCase
import br.com.roqls23.desafio.luizalabs.core.domain.usecase.UpdateDeliveryUseCase
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
class UpdateDeliveryViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val findDeliveryByIdUseCase: FindDeliveryByIdUseCase,
    private val findStatesUseCase: FindStatesUseCase,
    private val findDistrictsByUfUseCase: FindDistrictsByUfUseCase,
    private val updateDeliveryUseCase: UpdateDeliveryUseCase,
): AndroidViewModel(application) {

    val deliveryId = checkNotNull(savedStateHandle.get<Long>("id"))

    sealed interface UpdateDeliveryUiState : UiState {
        data object UpdatedSuccessfully : UpdateDeliveryUiState

        data class LoadDelivery(
            val entity: DeliveryEntity,
        ) : UpdateDeliveryUiState

        data class LoadStates(
            val states: List<StateEntity>
        ) : UpdateDeliveryUiState

        data class LoadDistricts(
            val districts: List<DistrictEntity>
        ) : UpdateDeliveryUiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    private val _districts = MutableStateFlow<List<DistrictEntity>>(emptyList())
    val districts: StateFlow<List<DistrictEntity>> get() = _districts.asStateFlow()

    private val _states = MutableStateFlow<List<StateEntity>>(emptyList())
    val states: StateFlow<List<StateEntity>> get() = _states.asStateFlow()

    fun loadDelivery() = viewModelScope.launch {
        findDeliveryByIdUseCase(deliveryId).onEach { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    _uiState.value = UpdateDeliveryUiState.LoadDelivery(entity = dataState.data)
                }

                else -> Unit
            }
        }.launchIn(viewModelScope)
    }

    fun findStates() = viewModelScope.launch {
        findStatesUseCase().onEach { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    _states.value = dataState.data
                    _uiState.value = UpdateDeliveryUiState.LoadStates(states = dataState.data)
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
                    _uiState.value = UpdateDeliveryUiState.LoadDistricts(districts = dataState.data)
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

    fun updateDelivery(
        data: CreateDeliveryForm
    ) = viewModelScope.launch {
        updateDeliveryUseCase(data).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    Log.d("RESULT_REQUEST", "Loading...")
                }

                is DataState.Success -> {
                    _uiState.value = UpdateDeliveryUiState.UpdatedSuccessfully
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