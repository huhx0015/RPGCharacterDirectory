package com.huhx0015.gotimer

import android.app.Application
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun startTimer(seconds: Long, timeRemaining: TextView) {
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
                    timeRemaining.text = it.toString()
                    Log.d("TEST", "onNext: $it")
                }, // onNext
                onError = {
                    Log.e("TEST", "onError: " + it.message)
                }, // onError
                onComplete = {
                    timeRemaining.text = "FINISHED!"
                    Log.d("TEST", "onCompleted ")
                } // onCompleted
            )

        compositeDisposable.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}