package com.binarfp.airtrip.model


import com.google.gson.annotations.SerializedName

data class BoardingPasses(
    @SerializedName("boarding_pass_pergi")
    val boardingPassPergi: BoardingPassDetail?,
    @SerializedName("Boarding_pass_pulang")
    val boardingPassPulang: BoardingPassDetail?
)