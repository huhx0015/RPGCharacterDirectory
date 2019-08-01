package com.huhx0015.gotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.view_controls.*

class MainActivity : AppCompatActivity(), TimerFragment.TimerFragmentListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var topTimerFragment: TimerFragment
    private lateinit var bottomTimerFragment: TimerFragment

    companion object {
        private const val TAG = "MainActivity"
        const val TOP_TIMER_ID = "TopTimerFragment"
        const val BOTTOM_TIMER_ID = "BottomTimerFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initControls()
        initViewModel()
        initFragments(savedInstanceState)
        initObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    private fun initView() {
        setContentView(R.layout.activity_main)
    }

    private fun initControls() {
        ib_pause.setOnClickListener {
            // TODO: Handle control state here.
        }
    }

    private fun initViewModel() {
        viewModelFactory = ViewModelFactory()
        //viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java) // TODO: Update ViewModelFactory
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        // Sets each timer state to NOT_STARTED
        viewModel.initTimer(TOP_TIMER_ID)
        viewModel.initTimer(BOTTOM_TIMER_ID)
    }

    private fun initFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            topTimerFragment = supportFragmentManager.getFragment(savedInstanceState, TOP_TIMER_ID) as TimerFragment
            bottomTimerFragment = supportFragmentManager.getFragment(savedInstanceState, BOTTOM_TIMER_ID) as TimerFragment
        } else {
            topTimerFragment = TimerFragment.newInstance(this, TOP_TIMER_ID,15)
            bottomTimerFragment = TimerFragment.newInstance(this, BOTTOM_TIMER_ID, 15)
        }

        supportFragmentManager.beginTransaction().apply {
            add(R.id.topTimerSide, topTimerFragment, TOP_TIMER_ID)
            add(R.id.bottomTimerSide, bottomTimerFragment, BOTTOM_TIMER_ID)
            commitAllowingStateLoss()
        }
    }

    private fun initObserver() {
//        viewModel.currentState.observe(this, Observer {
//            it?.let {
//                when (it) {
//                    MainViewModel.State.NOT_STARTED -> Log.d(TAG, "Not Started")
//                    MainViewModel.State.TOP_TIMER_RUNNING -> Log.d(TAG, "Top Timer Running")
//                    MainViewModel.State.BOTTOM_TIMER_RUNNING -> Log.d(TAG, "Bottom Timer Running")
//                    MainViewModel.State.PAUSED -> Log.d(TAG, "Paused")
//                    MainViewModel.State.FINISHED -> Log.d(TAG, "Finished")
//                }
//            }
//        })
    }

    override fun onTimerButtonClicked(id: String) {
        viewModel.switchTimerState(id)
    }
}