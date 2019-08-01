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

    val timerStates = HashMap<String?, State>() // Contains the timer states for each timer.
    var timerValues = HashMap<String?, AtomicLong>() // Contains the time values for each timer.

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var controlState: LiveData<State> = MutableLiveData()

    fun initTimer(id: String?) {
        timerStates[id] = State.NOT_STARTED
    }

    fun startTimer(id: String?, seconds: Long, timeButton: Button) { //Create and starts timer

        val subscription = Flowable.interval(0, 1, TimeUnit.SECONDS)
            .take(seconds + 1)
            .takeWhile { timerStates[id]?.equals(State.RUNNING)!! }
            .filter { it >= 0 } // Stopping conditions
            .map { timerValues[id]?.getAndDecrement() } // Decrements the timer value.
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { timeButton.text = it.toString() }, // onNext
                { e -> Log.e("TIMER", "Error: " + e.message) }, // onError
                {
                    if (timerValues[id]?.get()!! <= 0) {
                        Log.d("TIMER", "Done!" )
                        timerStates[id] = State.FINISHED
                        timeButton.text = "FINISHED"
                    }
                } // onComplete
            )

        compositeDisposable.add(subscription)
    }

    fun getControl(): State? {
        return controlState.value
    }
}