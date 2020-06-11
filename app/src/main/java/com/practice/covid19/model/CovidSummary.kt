package com.practice.covid19.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
Author: Rajendhiran Easu
Date: 06-May-20
*/

data class Country(
    @SerializedName("Country")
    @Expose
    var country: String? = null,
    @SerializedName("CountryCode")
    @Expose
    var countryCode: String? = null,
    @SerializedName("Slug")
    @Expose
    var slug: String? = null,
    @SerializedName("NewConfirmed")
    @Expose
    var newConfirmed: Int? = null,
    @SerializedName("TotalConfirmed")
    @Expose
    var totalConfirmed: Int? = null,
    @SerializedName("NewDeaths")
    @Expose
    var newDeaths: Int? = null,
    @SerializedName("TotalDeaths")
    @Expose
    var totalDeaths: Int? = null,
    @SerializedName("NewRecovered")
    @Expose
    var newRecovered: Int? = null,
    @SerializedName("TotalRecovered")
    @Expose
    var totalRecovered: Int? = null,
    @SerializedName("Date")
    @Expose
    var date: String? = null
)

data class Summary(
    @SerializedName("Global")
    @Expose
    var global: Global? = null,
    @SerializedName("Countries")
    @Expose
    var countries: List<Country>? = null,
    @SerializedName("Date")
    @Expose
    var date: String? = null
)

data class Global(
    @SerializedName("NewConfirmed")
    @Expose
    var newConfirmed: Int? = null,
    @SerializedName("TotalConfirmed")
    @Expose
    var totalConfirmed: Int? = null,
    @SerializedName("NewDeaths")
    @Expose
    var newDeaths: Int? = null,
    @SerializedName("TotalDeaths")
    @Expose
    var totalDeaths: Int? = null,
    @SerializedName("NewRecovered")
    @Expose
    var newRecovered: Int? = null,
    @SerializedName("TotalRecovered")
    @Expose
    var totalRecovered: Int? = null
)
