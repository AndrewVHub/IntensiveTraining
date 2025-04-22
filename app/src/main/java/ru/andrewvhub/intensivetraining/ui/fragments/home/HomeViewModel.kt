package ru.andrewvhub.intensivetraining.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import ru.andrewvhub.intensivetraining.R
import ru.andrewvhub.intensivetraining.core.BaseViewModel
import ru.andrewvhub.intensivetraining.core.launchSafe
import ru.andrewvhub.intensivetraining.data.models.Training
import ru.andrewvhub.intensivetraining.domain.useCase.GetTrainingsUseCase
import ru.andrewvhub.intensivetraining.ui.items.TrainingItem
import ru.andrewvhub.utils.SingleLiveEvent


const val DEFAULT_CATEGORY = "Все"

class HomeViewModel(
    private val getTrainingsUseCase: GetTrainingsUseCase
):BaseViewModel() {

    private val _allTrainingsFlow = MutableStateFlow<List<Training>>(emptyList())
    private val _searchTextFlow = MutableStateFlow("")

    private val _selectedTypeFlow = MutableStateFlow(DEFAULT_CATEGORY)
    val selectedTypeFlow = _selectedTypeFlow.asStateFlow()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private fun filterByTitle(training: Training, searchText: String): Boolean {
        return training.title?.contains(searchText, ignoreCase = true) ?: false
    }

    private fun filterByType(training: Training, selectedType: String?): Boolean {
        return selectedType == DEFAULT_CATEGORY || resources.getString(training.type.nameResId) == selectedType
    }

    val filteredTrainings: Flow<List<TrainingItem>> = combine(
        _allTrainingsFlow,
        _searchTextFlow,
        _selectedTypeFlow
    ) { trainings, searchText, selectedType ->
        trainings
            .filter { filterByTitle(it, searchText) && filterByType(it, selectedType) }
            .map { it.toItem() }
    }

    init {
        loadTrainings()
    }

    fun loadTrainings() {
        launchSafe(
            start = { _isLoading.postValue(true) },
            body = {
                val trainings = getTrainingsUseCase.invoke(Unit)
                _allTrainingsFlow.value = trainings

            },
            onError = { _errorMessage.postValue(it.handleThrowable()) },
            final = { _isLoading.postValue(false) }
        )
    }

    fun onSearch(text: String) {
        _searchTextFlow.value = text
    }

    fun onSelectType(text: String) {
        _selectedTypeFlow.value = text
    }

    fun resetFilters() {
        _selectedTypeFlow.value = DEFAULT_CATEGORY
    }

    private fun Training.toItem() = TrainingItem (
        id = id.toString(),
        title = title.orEmpty(),
        description = description.orEmpty(),
        type = resources.getString(type.nameResId),
        time = resources.getString(R.string.common_training_min, duration),
        image = image,
        onClick = {

        }
    )

    fun navigateToSelectFilter() {
        mainNavigate(
            HomeFragmentDirections.actionHomeFragmentToSelectFilterBottomSheetFragment(
                _selectedTypeFlow.value
            )
        )
    }
}