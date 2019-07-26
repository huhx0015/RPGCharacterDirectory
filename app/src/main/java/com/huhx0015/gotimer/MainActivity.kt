package com.huhx0015.gotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    companion object {
        private const val TOP_TIMER = "TopTimerFragment"
        private const val BOTTOM_TIMER = "BottomTimerFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initFragments(savedInstanceState)
        initViewModel()
    }

    private fun initView() {
        setContentView(R.layout.activity_main)
    }

    private fun initFragments(savedInstanceState: Bundle?) {
        val topTimerFragment: TimerFragment
        val bottomTimerFragment: TimerFragment

        if (savedInstanceState != null) {
            topTimerFragment = supportFragmentManager.getFragment(savedInstanceState, TOP_TIMER) as TimerFragment
            bottomTimerFragment = supportFragmentManager.getFragment(savedInstanceState, BOTTOM_TIMER) as TimerFragment
        } else {
            topTimerFragment = TimerFragment.newInstance(15)
            bottomTimerFragment = TimerFragment.newInstance(15)
        }

        supportFragmentManager.beginTransaction().apply {
            add(R.id.topTimerSide, topTimerFragment, TOP_TIMER)
            add(R.id.bottomTimerSide, bottomTimerFragment, BOTTOM_TIMER)
            commitAllowingStateLoss()
        }
    }

    private fun initViewModel() {
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}