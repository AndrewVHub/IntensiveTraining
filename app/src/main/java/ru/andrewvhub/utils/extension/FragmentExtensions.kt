package ru.andrewvhub.utils.extension

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.getColor(@ColorRes colorResId: Int) =
    ContextCompat.getColor(requireContext(), colorResId)
