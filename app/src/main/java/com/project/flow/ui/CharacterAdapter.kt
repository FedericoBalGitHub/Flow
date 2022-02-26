package com.project.flow.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.flow.data.remote.model.character
import com.project.flow.databinding.ItemListContentBinding
import com.project.flow.util.loadImage

/**
 * Created by Federico Bal on 22/2/2022.
 */

class CharacterAdapter(private val onClickListener: View.OnClickListener,
                       private val onContextClickListener: View.OnContextClickListener):
    PagingDataAdapter<character, CharacterAdapter.CharacterViewHolder>(CharacterComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        return CharacterViewHolder(
            ItemListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bindCharacter(it,position)
            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }
    }
    inner class CharacterViewHolder(private val binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindCharacter(item: character, pos: Int) = with(binding) {
            ivImage.loadImage(item.image)
            tvName.text = item.species
            tvDescription.text = "Pos: ${pos},N: ${item.name},S: ${item.species}"
        }
    }

    object CharacterComparator : DiffUtil.ItemCallback<character>() {
        override fun areItemsTheSame(oldItem: character, newItem: character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: character, newItem: character): Boolean {
            return oldItem == newItem
        }
    }

}