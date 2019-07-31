package com.huhx0015.gotimer

import android.util.Log
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.HashMap
import java.util.concurrent.TimeUnit

class MainViewModel: ViewModel() {

    enum class TimerState {
        NOT_STARTED, TOP_TIMER_RUNNING, BOTTOM_TIMER_RUNNING, PAUSED, FINISHED
    }

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    val timerValues = HashMap<String?, Long>()
    val timerStates = HashMap<String?, TimerState>()

    var currentState: LiveData<TimerState> = MutableLiveData()


    fun initTimer(id: String?) {
        timerStates[id] = TimerState.NOT_STARTED

        Log.d("TIMER", "initTimer invoked.")
    }


    fun startTimer(id: String?, seconds: Long, timeButton: Button) {

        val subscription = Observable.interval(0, 1, TimeUnit.SECONDS)
            .take(seconds + 1)
            .map {
                seconds - it - 1
            }
            .filter {
                it >= 0
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {

                    // Updates the timer value.
                    timerValues[id] = it

                    // Updates the button text.
                    timeButton.text = it.toString()

//                    // Check if app is started or not
//                    if (timerStates.get(id).equals()) {
//
//                    }

                    Log.d("TEST", "onNext: $it")
                }, // onNext
                onError = {
                    Log.e("TEST", "onError: " + it.message)
                }, // onError
                onComplete = {
                    timeButton.text = "FINISHED!"
                    Log.d("TEST", "onCompleted ")
                } // onCompleted
            )

        compositeDisposable.add(subscription)
    }

    fun stopTimer() {
        compositeDisposable.dispose()
    }

    fun getState(): TimerState? {
        return currentState.value
    }
}