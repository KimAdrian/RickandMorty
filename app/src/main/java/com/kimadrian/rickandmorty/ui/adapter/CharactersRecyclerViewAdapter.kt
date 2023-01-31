package com.kimadrian.rickandmorty.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kimadrian.rickandmorty.R
import com.kimadrian.rickandmorty.data.model.characters.Result

class CharactersRecyclerViewAdapter: PagingDataAdapter<Result, CharacterViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_card_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.characterName.text = character?.name
        holder.characterStatus.text = character?.status
        holder.characterSpecies.text = character?.species
        holder.characterLocation.text = character?.location?.name
        Glide.with(holder.characterImage)
            .load(character?.image)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.characterImage)
       // holder.characterStatusColor.setCardBackgroundColor(CharacterStatusColor.statusColor(character!!.status))

    }

    companion object DiffCallback: DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }
}

class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val characterImage: ImageView = itemView.findViewById(R.id.characterImage)
    val characterName: TextView = itemView.findViewById(R.id.characterName)
    val characterStatus: TextView = itemView.findViewById(R.id.characterStatus)
    val characterSpecies: TextView = itemView.findViewById(R.id.characterSpecies)
    val characterStatusColor: CardView = itemView.findViewById(R.id.statusColor)
    val characterLocation: TextView = itemView.findViewById(R.id.characterLocation)
}
