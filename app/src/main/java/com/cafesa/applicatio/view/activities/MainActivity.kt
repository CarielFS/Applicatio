package com.cafesa.applicatio.view.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.cafesa.applicatio.R
import com.cafesa.applicatio.databinding.ActivityMainBinding
import com.cafesa.applicatio.databinding.PopUpLayoutBinding
import com.cafesa.applicatio.network.CharactersApi
import com.cafesa.applicatio.network.RetrofitService
import com.cafesa.applicatio.models.CharacterHP
import com.cafesa.applicatio.view.adapters.CharactersAdapter
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var characterList = "students"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if(bundle!=null){
            characterList = bundle.getString("characters","students")
        } else {
            characterList = "students"
        }

        with(binding){
            tvCurrent.text = characterList.replaceFirstChar {
                it.titlecase()
            }
            btnStudents.text = getString(R.string.students_btn)
            btnStaff.text = getString(R.string.staff_btn)
        }

        val call = RetrofitService.getRetrofit()
            .create(CharactersApi::class.java)
            .getCharacters(characterList)

        call.enqueue(object: retrofit2.Callback<ArrayList<CharacterHP>> {
            override fun onResponse(
                call: Call<ArrayList<CharacterHP>>,
                response: Response<ArrayList<CharacterHP>>
            ) {
                with(binding){
                    givSnitch.isVisible = false
                    rvCharacters.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvCharacters.adapter = CharactersAdapter(this@MainActivity, response.body()!!)
                    { selectedCharacter: CharacterHP ->
                        characterClick(selectedCharacter)
                    }
                }


            }

            override fun onFailure(call: Call<ArrayList<CharacterHP>>, t: Throwable) {
                binding.givSnitch.isVisible = false
                Toast.makeText(this@MainActivity, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun characterClick(characterHP: CharacterHP){
        val bundle = Bundle()
        val intent = Intent(this,CharacterProfile::class.java)

        bundle.putString("id",characterHP.id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun clickStudents(view: View){
        if(characterList!="students"){
            val intent = Intent(this,MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("characters","students")
            intent.putExtras(bundle)
            finish()
            startActivity(intent)
        }else{
            Toast.makeText(this@MainActivity, getString(R.string.curr_student), Toast.LENGTH_SHORT).show()
        }
    }
    fun clickStaff(view: View){
        if(characterList!="staff") {
            val intent = Intent(this, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("characters", "staff")
            intent.putExtras(bundle)
            finish()
            startActivity(intent)
        }else{
            Toast.makeText(this@MainActivity, getString(R.string.curr_staff), Toast.LENGTH_SHORT).show()
        }
    }
    fun showInfo(view: View){

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.pop_up_layout)

        val close: Button = dialog.findViewById(R.id.btnClose)
        close.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }

}

