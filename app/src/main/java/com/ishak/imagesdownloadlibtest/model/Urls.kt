package com.ishak.imagesdownloadlibtest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Urls {
    @SerializedName("raw")
    @Expose
    lateinit var raw: String

    @SerializedName("full")
    @Expose
    lateinit var full: String

    @SerializedName("regular")
    @Expose
    lateinit var regular: String

    @SerializedName("small")
    @Expose
    lateinit var small: String

    @SerializedName("thumb")
    @Expose
    lateinit var thumb: String
}