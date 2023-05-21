package com.cafesa.applicatio.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.cafesa.applicatio.R
import com.cafesa.applicatio.databinding.ActivityCharacterProfileBinding
import com.cafesa.applicatio.databinding.CharacterElemBinding
import com.cafesa.applicatio.models.CharacterDetail
import com.cafesa.applicatio.network.CharactersApi
import com.cafesa.applicatio.network.RetrofitService
import retrofit2.Call
import retrofit2.Response

class CharacterProfile : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val noData = getString(R.string.no_data)

        if(bundle!=null){
            val id = bundle.getString("id","")
            if(id != ""){
                val call = RetrofitService.getRetrofit()
                    .create(CharactersApi::class.java)
                    .getCharDetail(id)

                call.enqueue(object: retrofit2.Callback<ArrayList< CharacterDetail>>{
                    override fun onResponse(
                        call: Call<ArrayList< CharacterDetail>>,
                        response: Response<ArrayList< CharacterDetail>>
                    ) {
                        with(binding){
                            givSnitch.isVisible = false
                            tvName.text = response.body()!![0].name

                            if (response.body()!![0]?.actor != "")
                                tvActor.text = response.body()!![0]?.actor
                            else
                                tvActor.text = noData

                            if (response.body()!![0]?.house != "") {
                                tvHouse.text = getString(R.string.house, response.body()!![0]?.house)
                                val houses = mapOf(
                                    "Gryffindor" to R.drawable.gryffindor,
                                    "Hufflepuff" to R.drawable.hufflepuff,
                                    "Ravenclaw" to R.drawable.ravenclaw,
                                    "Slytherin" to R.drawable.slytherin
                                )
                                binding.root.setBackgroundResource(houses[response.body()!![0]?.house]!!)
                            }else
                                tvHouse.text = getString(R.string.house,noData)

                            if (response.body()!![0]?.ancestry != "")
                                tvAncestry.text = getString(R.string.ancestry,response.body()!![0]?.ancestry)
                            else
                                tvAncestry.text = getString(R.string.ancestry,noData)

                            if (response.body()!![0]?.species != "")
                                tvSpecies.text = getString(R.string.species,response.body()!![0]?.species)
                            else
                                tvSpecies.text = getString(R.string.species,noData)

                            if (response.body()!![0]?.wand?.core != "")
                                tvWand.text = getString(R.string.wand,response.body()!![0]?.wand?.core)
                            else
                                tvWand.text = getString(R.string.no_wand)

                            if (response.body()!![0]?.alive != null)
                                if (response.body()!![0]?.alive == true)
                                    tvAlive.text = getString(R.string.is_alive)
                                else
                                    tvAlive.text = getString(R.string.is_dead)
                            else
                                tvHouse.text = noData

                            if (response.body()!![0]?.wizard != null)
                                if (response.body()!![0]?.wizard == true)
                                    tvWizard.text = getString(R.string.is_wizard)
                                else
                                    tvWizard.text = getString(R.string.no_wizard)
                            else
                                tvWizard.text = noData

                            if (response.body()!![0]?.image != "")
                                Glide.with(this@CharacterProfile)
                                    .load(response.body()!![0]?.image)
                                    .into(ivThumbnail)
                            else
                                ivThumbnail.setImageResource(R.drawable.default_profile)

                        }
                    }

                    override fun onFailure(call: Call<ArrayList< CharacterDetail>>, t: Throwable) {
                        t.printStackTrace()
                        binding.givSnitch.isVisible = false
                        Toast.makeText(this@CharacterProfile, getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }

                })

            }else{
                binding.givSnitch.isVisible = false
                Toast.makeText(this@CharacterProfile, getString(R.string.err_character), Toast.LENGTH_SHORT).show()
            }

        } else {
            binding.givSnitch.isVisible = false
            Toast.makeText(this@CharacterProfile, getString(R.string.err_character), Toast.LENGTH_SHORT).show()
        }

    }
}