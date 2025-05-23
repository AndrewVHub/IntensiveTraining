package ru.andrewvhub.intensivetraining.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.andrewvhub.intensivetraining.ui.MainNavigator
import ru.andrewvhub.utils.extension.navigateSafe
import ru.andrewvhub.utils.extension.nonNullObserve

abstract class BaseFragment(private val layoutResId: Int) : Fragment() {

    protected open val viewModel: BaseViewModel? = null

    val mainNavigator
        get() = (activity as? MainNavigator)?.getNavController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutResId, container, false).also {
        viewModel?.mainNavigate?.nonNullObserve(viewLifecycleOwner) {
            mainNavigator?.navigateSafe(it)
        }
        viewModel?.navigateUp?.nonNullObserve(viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }
}