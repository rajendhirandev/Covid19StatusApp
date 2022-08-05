package com.practice.acronym.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Meanings (

    @SerializedName("lf")
    @Expose
    var lf: String? = null,
    @SerializedName("freq")
    @Expose
    var freq: String? = null,
    @SerializedName("since")
    @Expose
    var since: String? = null
)

data class Acronym(

    @SerializedName("lfs")
    @Expose
    var lfs: List<Meanings>? = null,
    @SerializedName("sf")
    @Expose
    var date: String? = null
)