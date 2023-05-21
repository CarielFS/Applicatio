package com.cafesa.applicatio.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cafesa.applicatio.R
import com.cafesa.applicatio.databinding.CharacterElemBinding
import com.cafesa.applicatio.models.CharacterHP

class CharactersAdapter (
    private var context: Context,
    private var charactersHP: ArrayList<CharacterHP>,
    private val clickListener: (CharacterHP) -> Unit)
    :RecyclerView.Adapter<CharactersAdapter.ViewHolder>(){

    class ViewHolder(view: CharacterElemBinding): RecyclerView.ViewHolder(view.root){
        val ivThumbnail = view.ivThumbnail
        val tvName = view.tvName
        val tvActor = view.tvActor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CharacterElemBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = charactersHP.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvName.text = charactersHP[position].name
        if(charactersHP[position].name?.length!! > 18){
            holder.tvName.textSize = 36F
        }

        if (charactersHP[position].actor == ""){
            holder.tvActor.setText(R.string.no_data)
        }else{
            holder.tvActor.text = charactersHP[position].actor
        }


        if (charactersHP[position].image == "") {
            holder.ivThumbnail.setImageResource(R.drawable.default_profile)
        }else{
            Glide.with(context)
                .load(charactersHP[position].image)
                .into(holder.ivThumbnail)
        }



        holder.itemView.setOnClickListener {
            //Para programar los eventos click al elemento completo del ViewHolder
            clickListener(charactersHP[position])
        }

    }

}

