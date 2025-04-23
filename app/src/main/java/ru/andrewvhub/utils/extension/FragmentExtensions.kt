package ru.andrewvhub.utils.extension

import android.graphics.Color
import android.view.View.inflate
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.andrewvhub.intensivetraining.R
import ru.andrewvhub.intensivetraining.databinding.ErrorMassageViewBinding

fun Fragment.showSnackBar(message: String, title: String = getString(R.string.common_error), ) {
    val snackView = inflate(requireContext(), R.layout.error_massage_view, null)
    val binding = ErrorMassageViewBinding.bind(snackView)
    val snackBar = Snackbar.make(requireView(), "", Snackbar.LENGTH_LONG)
    snackBar.apply {
        (view as ViewGroup).addView(binding.root)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        binding.apply {
            titleError.text = title
            descriptionError.text = message
        }
        show()
    }
}
