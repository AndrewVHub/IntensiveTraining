package ru.andrewvhub.intensivetraining.ui.fragments.detailTraining

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.andrewvhub.intensivetraining.R
import ru.andrewvhub.intensivetraining.core.BaseFragment
import ru.andrewvhub.intensivetraining.databinding.FragmentDetailTrainingBinding
import ru.andrewvhub.intensivetraining.ui.viewBinding.viewBinding
import ru.andrewvhub.utils.extension.addSystemBottomSpace
import ru.andrewvhub.utils.extension.load
import ru.andrewvhub.utils.extension.setOnThrottleClickListener

class DetailTrainingFragment : BaseFragment(R.layout.fragment_detail_training) {

    private val viewBinding by viewBinding(FragmentDetailTrainingBinding::bind)
    override val viewModel by viewModel<DetailTrainingViewModel>()
    private val args: DetailTrainingFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(viewBinding) {
        super.onViewCreated(view, savedInstanceState)

        trainingDescription.addSystemBottomSpace(true)
        args.trainingDetail.apply {
            trainingImage.load(image)
            trainingDescription.text = description
            trainingTitle.text = title
            timeValue.text = getString(R.string.common_training_min, duration)
            typeValue.text = getString(type.nameResId)
        }

        navigateToVideo.setOnThrottleClickListener {
            viewModel.navigateToVideo()
        }
    }
}