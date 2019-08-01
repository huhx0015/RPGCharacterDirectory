package com.huhx0015.gotimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_timer.*
import java.util.concurrent.atomic.AtomicLong

class TimerFragment(private var listener: TimerFragmentListener) : Fragment() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    private var fragmentId: String? = null

    companion object {
        private const val KEY_START_TIME = "start_time"
        private const val KEY_ID = "id"

        fun newInstance(listener: TimerFragmentListener, id: String, startTime: Long): TimerFragment {
            val bundle = Bundle()
            bundle.putString(KEY_ID, id)
            bundle.putLong(KEY_START_TIME, startTime)

            val timerFragment = TimerFragment(listener)
            timerFragment.arguments = bundle
            return timerFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        arguments?.let {
            fragmentId = it.getString(KEY_ID)
            val startTime = it.getLong(KEY_START_TIME)

            viewModel.timerValues[fragmentId] = AtomicLong(startTime)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    private fun initView() {
        val startTime = viewModel.timerValues[fragmentId]?.get()
        timerButton.text = startTime.toString()

        timerButton.setOnClickListener {

            // Check if the timer is already running.
            if (!viewModel.timerStates[fragmentId]?.equals(State.RUNNING)!!) {
                viewModel.startTimer(fragmentId, viewModel.timerValues[fragmentId]?.get()!!, timerButton) // Starts timer
                listener.onTimerButtonClicked(fragmentId!!)
            }
        }
    }

    private fun initViewModel() {
        viewModelFactory = ViewModelFactory()

        // Have to reference same activity to use same ViewModel instance.
        viewModel = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }!!
    }

    interface TimerFragmentListener {
        fun onTimerButtonClicked(id: String)
    }
}