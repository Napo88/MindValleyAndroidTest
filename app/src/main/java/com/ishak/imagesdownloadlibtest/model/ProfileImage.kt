package com.ishak.imagesdownloadlibtest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileImage {
    @SerializedName("small")
    @Expose
    lateinit var small: String

    @SerializedName("medium")
    @Expose
    lateinit var medium: String

    @SerializedName("large")
    @Expose
    lateinit var large:String
}