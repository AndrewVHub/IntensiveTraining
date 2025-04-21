package ru.andrewvhub.intensivetraining.ui.fragments.home

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.andrewvhub.intensivetraining.R
import ru.andrewvhub.intensivetraining.core.BaseFragment
import ru.andrewvhub.intensivetraining.databinding.FragmentHomeBinding
import ru.andrewvhub.intensivetraining.ui.viewBinding.viewBinding
import ru.andrewvhub.utils.extension.addSystemTopSpace

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewBinding by viewBinding(FragmentHomeBinding::bind)

    override val viewModel by viewModel<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(viewBinding) {
        super.onViewCreated(view, savedInstanceState)

        text.addSystemTopSpace(false)
    }

}