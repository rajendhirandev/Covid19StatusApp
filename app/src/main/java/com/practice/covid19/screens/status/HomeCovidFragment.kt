package com.practice.covid19.screens.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.covid19.databinding.HomeCovidFragmentBinding
import com.practice.covid19.model.Global
import com.practice.covid19.model.Summary
import com.practice.covid19.network.Resource
import com.practice.covid19.network.Status
import com.practice.covid19.utils.numberFormat
import kotlinx.coroutines.flow.collectLatest

class HomeCovidFragment : Fragment() {

    lateinit var binding: HomeCovidFragmentBinding

    companion object {
        fun newInstance() = HomeCovidFragment()
    }

    private val viewModel: HomeCovidViewModel by viewModels()
    private val statusAdapter: HomeCovidStatusAdapter by lazy {
        HomeCovidStatusAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeCovidFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            rvCovidStatus.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                adapter = statusAdapter
            }

            viewModel.getCovidStatusStateFlowOn()

            logo.setOnClickListener {
                viewModel.getCovidStatusStateFlowOn()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.summaryData.collectLatest {
                updateView(it)
            }
        }

        viewModel.summaryLiveData.observe(viewLifecycleOwner) {
            updateView(it)
        }
    }

    private fun updateView(res: Resource<Summary>) {
        when (res.status) {
            Status.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                res.data?.let {
                    updateCards(it.global)
                    it.countries?.let {
                        statusAdapter.setData(it)
                    } ?: kotlin.run {
                        Toast.makeText(activity, "No Data Available " + res.msg, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
            Status.ERROR -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    activity,
                    "Something went wrong... Please contact admin " + res.msg,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun updateCards(global: Global?) {
        global?.let {
            binding.apply {
                txtConfirmedOverall.text = it.totalConfirmed?.numberFormat()
                txtTodayConfirmed.text = it.newConfirmed?.numberFormat()

                txtDeathOverall.text = it.totalDeaths?.numberFormat()
                txtTodayDeath.text = it.newDeaths?.numberFormat()

                txtRecoveredOverall.text = it.totalRecovered?.numberFormat()
                txtTodayRecovered.text = it.newRecovered?.numberFormat()
            }
        }
    }
}
