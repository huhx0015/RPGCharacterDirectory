package com.huhx0015.gotimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TimerFragment : Fragment() {

    companion object {
        private const val KEY_START_TIME = "start_time"

        fun newInstance(startTime: Long): TimerFragment {
            val bundle = Bundle()
            bundle.putLong(KEY_START_TIME, startTime)

            val timerFragment = TimerFragment()
            timerFragment.arguments = bundle
            return timerFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }
}
