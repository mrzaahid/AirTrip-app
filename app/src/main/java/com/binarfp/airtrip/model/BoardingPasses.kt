package com.binarfp.airtrip.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BoardingPasses(
    @SerializedName("boarding_pass_pergi")
    val boardingPassPergi: BoardingPassDetail?,
    @SerializedName("boarding_pass_pulang")
    val boardingPassPulang: BoardingPassDetail?
):Serializable