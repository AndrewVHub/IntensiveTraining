package ru.andrewvhub.intensivetraining.ui.fragments.trainingVideoPlayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.andrewvhub.intensivetraining.core.BaseViewModel
import ru.andrewvhub.intensivetraining.core.launchSafe
import ru.andrewvhub.intensivetraining.domain.useCase.GetTrainingVideoByIdUseCase
import ru.andrewvhub.utils.SingleLiveEvent

class TrainingVideoPlayerViewModel(
    private val getTrainingVideoByIdUseCase: GetTrainingVideoByIdUseCase,
    private val idVideo: Int
) : BaseViewModel() {

    private val _urlVideo = MutableLiveData<String>()
    val urlVideo: LiveData<String> = _urlVideo

    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        loadVideoUrl()
    }

    private fun loadVideoUrl() {
        launchSafe(
            body = {
                val videoUrl = getTrainingVideoByIdUseCase.invoke(GetTrainingVideoByIdUseCase.Params(idVideo)).url
                _urlVideo.postValue(videoUrl)
            },
            onError = {
                _errorMessage.postValue(it.handleThrowable())
            }
        )
    }
}