package com.cafesa.applicatio.models

import com.google.gson.annotations.SerializedName

data class CharacterHP(
    @SerializedName("id")
    var id: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("actor")
    var actor: String?
)
