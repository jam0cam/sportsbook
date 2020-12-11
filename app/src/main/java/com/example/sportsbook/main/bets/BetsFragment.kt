package com.example.sportsbook.main.bets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsbook.MyApplication
import com.example.sportsbook.dagger.ViewModelFactory
import com.example.sportsbook.databinding.FragmentBetsBinding
import com.example.sportsbook.extensions.applyBundle
import com.example.sportsbook.extensions.showIfOrGone
import org.joda.time.LocalDate
import javax.inject.Inject

class BetsFragment : Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: BetsViewModel by viewModels { viewModelFactory }

    var _binding: FragmentBetsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val DATE = "date"
        private const val TAG = "BetsFragment"

        fun newInstance(date: LocalDate): BetsFragment {
            return BetsFragment().applyBundle {
                putSerializable(DATE, date)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApplication.instance.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(requireArguments().getSerializable(DATE) as LocalDate)

        with(viewModel) {
            bets.observe(viewLifecycleOwner, ::bind)
        }
    }

    private fun bind(bets: List<BetsListItem>) {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = BetsRecyclerAdapter(bets)
        }

        binding.emptyView.showIfOrGone { bets.isEmpty() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}