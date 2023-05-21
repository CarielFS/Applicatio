package com.cafesa.applicatio.network

import com.cafesa.applicatio.models.CharacterDetail
import com.cafesa.applicatio.models.CharacterHP
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersApi {

    @GET("characters/{chars}")
    fun getCharacters(
        @Path("chars") chars: String?
    ): Call<ArrayList<CharacterHP>>

    @GET("character/{id}")
    fun getCharDetail(
        @Path("id") id: String?
    ): Call<ArrayList<CharacterDetail>>

}

