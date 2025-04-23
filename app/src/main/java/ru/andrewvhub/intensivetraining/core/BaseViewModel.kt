package ru.andrewvhub.intensivetraining.core

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.andrewvhub.utils.SingleLiveEvent
import ru.andrewvhub.utils.extension.getMessageFromThrowable
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), KoinComponent {

    protected val resources: Resources by inject()

    private val _mainNavigate = SingleLiveEvent<NavDirections>()
    val mainNavigate: LiveData<NavDirections> = _mainNavigate

    private val _navigateUp = SingleLiveEvent<Unit>()
    val navigateUp: LiveData<Unit> = _navigateUp

    fun Throwable.handleThrowable() = resources.getMessageFromThrowable(this)

    fun mainNavigate(destination: NavDirections) {
        _mainNavigate.value = destination
    }

    fun navigateUp() {
        _navigateUp.value = Unit
    }
}

fun ViewModel.launchSafe(
    body: suspend () -> Unit,
    onError: ((error: Throwable) -> Unit)? = null,
    start: (() -> Unit)? = null,
    final: (() -> Unit)? = null
): Job = viewModelScope.launch(Dispatchers.IO) {
    try {
        start?.invoke()
        body()
    } catch (error: Throwable) {
        Timber.tag("LAUNCH_SAFE").e(error)
        onError?.invoke(error)
    } finally {
        final?.invoke()
    }
}