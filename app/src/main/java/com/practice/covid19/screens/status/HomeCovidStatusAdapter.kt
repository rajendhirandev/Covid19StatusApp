package com.practice.covid19.screens.status

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practice.covid19.R
import com.practice.covid19.model.Country
import com.practice.covid19.screens.status.HomeCovidStatusAdapter.StatusVH
import com.practice.covid19.utils.numberFormat
import kotlinx.android.synthetic.main.covid_status_item_view.view.*

/*
Author: Rajendhiran Easu
Date: 09-May-20
*/
class HomeCovidStatusAdapter : RecyclerView.Adapter<StatusVH>() {

    private var countries: List<Country> = ArrayList()

    inner class StatusVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun updateView(countryInfo: Country?) {
            countryInfo?.let { country ->
                itemView.txt_title_countries.text = country.country
                itemView.txt_title_confirmed.text = country.totalConfirmed?.numberFormat()
                itemView.txt_title_confirmed_today.text = country.newConfirmed?.numberFormat()

                itemView.txt_title_recovered.text = country.totalRecovered?.numberFormat()
                itemView.txt_title_recovered_today.text = country.newRecovered?.numberFormat()

                itemView.txt_title_death.text = country.totalDeaths?.numberFormat()
                itemView.txt_title_death_today.text = country.newDeaths?.numberFormat()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.covid_status_item_view, parent, false)
        return StatusVH(view)
    }

    override fun onBindViewHolder(holder: StatusVH, position: Int) {
        holder.updateView(countries.get(position))
    }

    fun setData(data: List<Country>) {
        countries = data.filter { it.totalConfirmed != 0 }.sortedByDescending { it.totalConfirmed }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}
