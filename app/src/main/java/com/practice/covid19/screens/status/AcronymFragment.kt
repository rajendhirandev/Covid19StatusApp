package com.practice.covid19.screens.status

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.covid19.databinding.AcronymFragmentBinding
import com.practice.covid19.model.Acronym
import com.practice.covid19.network.Resource
import com.practice.covid19.network.Status
import com.practice.covid19.utils.launchAndCollectIn

class AcronymFragment : Fragment() {

    lateinit var binding: AcronymFragmentBinding
    private val viewModel: HomeCovidViewModel by viewModels()
   /* private val statusAdapter: HomeCovidStatusAdapter by lazy {
        HomeCovidStatusAdapter()
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AcronymFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvCovidStatus.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
               /* adapter = statusAdapter*/
            }

            logo.setOnClickListener {
                viewModel.getMeanings("BBC")
            }
        }

        viewModel.meaningStateFlow.launchAndCollectIn(viewLifecycleOwner) {
            updateView(it)
        }
    }

    private fun updateView(res: Resource<List<Acronym>>) {
        when (res.status) {
            Status.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                res.data?.get(0)?.let {
                    it.lfs?.let {
                        it.forEach {
                            Log.d("ACRY", "updateView: ${it.lf}")
                        }
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

    companion object {
        fun newInstance() = AcronymFragment()
    }
}
