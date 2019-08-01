package com.huhx0015.gotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.view_controls.*

class MainActivity : AppCompatActivity(), TimerFragment.TimerFragmentListener {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var topTimerFragment: TimerFragment
    private lateinit var bottomTimerFragment: TimerFragment

    companion object {
        private const val TAG = "MainActivity"
        private const val TOP_TIMER_ID = "TopTimerFragment"
        private const val BOTTOM_TIMER_ID = "BottomTimerFragment"
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
        compositeDisposable.dispose()
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



    override fun onTimerButtonClicked(id: String?) {

        when (id) {
            TOP_TIMER_ID -> {
                Log.d("TIMER", "TOP TIMER BUTTON TAPPED")

                if (!viewModel.timerStates[TOP_TIMER_ID]?.equals(State.RUNNING)!!) {
                    viewModel.timerStates[TOP_TIMER_ID] = State.RUNNING // Sets the running state.
                    viewModel.timerStates[BOTTOM_TIMER_ID] = State.PAUSED // Sets the running state.
                }


            }
            BOTTOM_TIMER_ID -> {
                Log.d("TIMER", "BOTTOM TIMER BUTTON TAPPED")

                if (!viewModel.timerStates[BOTTOM_TIMER_ID]?.equals(State.RUNNING)!!) {
                    viewModel.timerStates[BOTTOM_TIMER_ID] = State.RUNNING // Sets the running state.
                    viewModel.timerStates[TOP_TIMER_ID] = State.PAUSED // Sets the running state.
                }
            }
        }
    }
}