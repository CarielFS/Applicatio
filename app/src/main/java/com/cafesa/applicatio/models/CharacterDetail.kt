package com.cafesa.applicatio.models

import com.google.gson.annotations.SerializedName

data class CharacterDetail(
    @SerializedName("image")
    var image: String?,
    @SerializedName("house")
    var house: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("actor")
    var actor: String?,
    @SerializedName("species")
    var species: String?,
    @SerializedName("ancestry")
    var ancestry: String?,
    @SerializedName("alive")
    var alive: Boolean?,
    @SerializedName("wizard")
    var wizard: Boolean?,
    @SerializedName("wand")
    var wand: Wand?
)

data class Wand(
    @SerializedName("core")
    var core: String?
)