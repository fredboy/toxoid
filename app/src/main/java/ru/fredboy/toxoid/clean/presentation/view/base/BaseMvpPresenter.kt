package ru.fredboy.toxoid.clean.presentation.view.base

import android.util.Log
import androidx.annotation.CallSuper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope

abstract class BaseMvpPresenter<V : MvpView> : MvpPresenter<V>() {

    private val jobs: MutableSet<Job> = LinkedHashSet()

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        jobs.onEach(Job::cancel)
    }

    private fun logError(throwable: Throwable) {
        Log.e(this::class.java.simpleName, "Flow error.", throwable)
    }

    fun suspend(
        coroutine: suspend () -> Unit,
        onError: (Throwable) -> Unit = ::logError,
        doFinally: () -> Unit = {}
    ) {
        presenterScope.launch {
            try {
                coroutine()
            } catch (t: Throwable) {
                onError(t)
            } finally {
                doFinally()
            }
        }.also(jobs::add)
    }

    fun <T : Any> Flow<T>.schedule(
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit = ::logError,
        doFinally: () -> Unit = {},
    ) {
        presenterScope.launch {
            try {
                this@schedule.flowOn(Dispatchers.IO).collect(onSuccess)
            } catch (t: Throwable) {
                onError(t)
            } finally {
                doFinally()
            }
        }.also(jobs::add)
    }

}