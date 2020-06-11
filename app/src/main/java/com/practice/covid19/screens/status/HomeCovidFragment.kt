package com.practice.covid19.screens.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.covid19.R
import com.practice.covid19.model.Global
import com.practice.covid19.network.Status
import com.practice.covid19.utils.numberFormat
import kotlinx.android.synthetic.main.home_covid_fragment.*

class HomeCovidFragment : Fragment() {

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
    ): View? {
        return inflater.inflate(R.layout.home_covid_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_covid_status.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rv_covid_status.adapter = statusAdapter

        viewModel.getCovidStatus().observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    progressBar?.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    progressBar?.visibility = View.GONE
                    res.data?.let {
                        updateCards(it.global)
                        it.countries?.let {
                            statusAdapter.setData(it)
                        } ?: kotlin.run {
                            Toast.makeText(activity, "No Data Available", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                Status.ERROR -> {
                    progressBar?.visibility = View.GONE
                    Toast.makeText(
                        activity,
                        "Something went wrong... Please contact admin",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun updateCards(global: Global?) {
        global?.let {
            txt_confirmed_overall?.text = it.totalConfirmed?.numberFormat()
            txt_today_confirmed?.text = it.newConfirmed?.numberFormat()

            txt_death_overall?.text = it.totalDeaths?.numberFormat()
            txt_today_death?.text = it.newDeaths?.numberFormat()

            txt_recovered_overall?.text = it.totalRecovered?.numberFormat()
            txt_today_recovered?.text = it.newRecovered?.numberFormat()
        }
    }
}
