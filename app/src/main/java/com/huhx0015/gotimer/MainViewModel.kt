package com.huhx0015.gotimer

import android.util.Log
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap
import java.util.concurrent.TimeUnit
import io.reactivex.Flowable
import java.util.concurrent.atomic.AtomicLong

class MainViewModel: ViewModel() {

    enum class TimerState {
        NOT_STARTED, RUNNING, PAUSED, FINISHED
    }

    val timerStates = HashMap<String?, TimerState>() // Contains the timer states for each timer.
    var timerValues = HashMap<String?, AtomicLong>() // Contains the time values for each timer.

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Deprecated("Possibly no longer needed?")
    var currentState: LiveData<TimerState> = MutableLiveData()

    fun initTimer(id: String?) {
        timerStates[id] = TimerState.NOT_STARTED
    }

    fun startTimer(id: String?, seconds: Long, timeButton: Button) { //Create and starts timer

        val subscription = Flowable.interval(0, 1, TimeUnit.SECONDS)
            .take(seconds + 1)
            .takeWhile { timerStates[id]?.equals(TimerState.RUNNING)!! }
            .filter { it >= 0 } // Stopping conditions
            .map { timerValues[id]?.getAndDecrement() } // Decrements the timer value.
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { timeButton.text = it.toString() }, // onNext
                { e -> Log.e("TIMER", "Error: " + e.message) }, // onError
                {
                    // TODO: Currently does not work.
                    if (timerValues[id]?.get() == 0L) {
                        Log.d("TIMER", "Done!" )
                        timerStates[id] = TimerState.FINISHED
                        timeButton.text = "FINISHED"
                    }
                } // onComplete
            )

        compositeDisposable.add(subscription)
    }

    fun getState(): TimerState? {
        return currentState.value
    }
}