package com.ishak.imagesdownloadlibtest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Board {
    @SerializedName("id")
    @Expose
    lateinit var id: String

    @SerializedName("user")
    @Expose
    lateinit var user: User

    @SerializedName("urls")
    @Expose
    lateinit var urls: Urls
}