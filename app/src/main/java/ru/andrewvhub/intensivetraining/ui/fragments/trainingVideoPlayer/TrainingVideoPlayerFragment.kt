package ru.andrewvhub.intensivetraining.ui.fragments.trainingVideoPlayer

import android.os.Bundle
import android.view.View
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.andrewvhub.intensivetraining.R
import ru.andrewvhub.intensivetraining.core.BaseFragment
import ru.andrewvhub.intensivetraining.databinding.FragmentTrainingVideoPlayerBinding
import ru.andrewvhub.intensivetraining.ui.viewBinding.viewBinding
import ru.andrewvhub.utils.extension.addSystemBottomSpace
import ru.andrewvhub.utils.extension.addSystemTopSpace
import ru.andrewvhub.utils.extension.nonNullObserve
import ru.andrewvhub.utils.extension.setOnThrottleClickListener
import ru.andrewvhub.utils.extension.showSnackBar

class TrainingVideoPlayerFragment : BaseFragment(R.layout.fragment_training_video_player) {

    private val viewBinding by viewBinding(FragmentTrainingVideoPlayerBinding::bind)
    private val args: TrainingVideoPlayerFragmentArgs by navArgs()
    override val viewModel by viewModel<TrainingVideoPlayerViewModel> { parametersOf(args.idVideo) }

    private val exoPlayer by lazy {
        ExoPlayer.Builder(requireContext()).build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(viewBinding) {
        super.onViewCreated(view, savedInstanceState)

        closeVideo.apply {
            addSystemTopSpace(false)
            setOnThrottleClickListener {
                viewModel.navigateUp()
            }
        }

        playerView.apply {
            addSystemBottomSpace(true)
            player = exoPlayer
        }

        viewModel.apply {
            errorMessage.nonNullObserve(viewLifecycleOwner) { showSnackBar(it) }
            urlVideo.nonNullObserve(viewLifecycleOwner,::handleVideo)
        }
    }

    @OptIn(UnstableApi::class)
    private fun handleVideo(url: String) {
        exoPlayer.apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            play()
        }
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding.playerView.player = null
        exoPlayer.release()
    }
}