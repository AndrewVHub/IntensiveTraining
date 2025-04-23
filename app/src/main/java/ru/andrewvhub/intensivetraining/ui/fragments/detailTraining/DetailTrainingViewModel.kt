package ru.andrewvhub.intensivetraining.ui.fragments.detailTraining

import ru.andrewvhub.intensivetraining.core.BaseViewModel

class DetailTrainingViewModel: BaseViewModel() {

    fun navigateToVideo(id: Int) {
        mainNavigate(
            DetailTrainingFragmentDirections.actionDetailTrainingFragmentToTrainingVideoPlayerFragment(id)
        )
    }
}