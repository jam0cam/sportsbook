package com.example.sportsbook.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sportsbook.databinding.FragmentBetsBinding
import com.example.sportsbook.extensions.applyBundle
import org.joda.time.LocalDate

class BetsFragment : Fragment() {
    var binding: FragmentBetsBinding? = null

    companion object {
        private const val KEY = "logs"

        fun newInstance(date: LocalDate): BetsFragment {
            return BetsFragment().applyBundle {
                putSerializable(KEY, date)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBetsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}