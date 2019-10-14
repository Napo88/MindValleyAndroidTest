package com.ishak.imagesdownloadlibtest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("id")
    @Expose
    lateinit var id: String

    @SerializedName("name")
    @Expose
    lateinit var name: String

    @SerializedName("username")
    @Expose
    lateinit var username: String

    @SerializedName("profile_image")
    @Expose
    lateinit var profileImage: ProfileImage
}