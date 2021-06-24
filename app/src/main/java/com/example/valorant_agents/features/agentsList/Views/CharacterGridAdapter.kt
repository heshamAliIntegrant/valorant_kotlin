package com.example.valorant_agents.features.agentsList.Views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.valorant_agents.R
import com.example.valorant_agents.databinding.ActivityMainBinding
import com.example.valorant_agents.databinding.CharacterItemBinding
import com.example.valorant_agents.features.agentsList.Models.Agent
import com.squareup.picasso.Picasso

class CharacterGridAdapter : ListAdapter<Agent, CharacterGridAdapter.CharacterViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class CharacterViewHolder(val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(agent: Agent) {
            binding.characterName.text = agent.displayName
            binding.characterRole.text = agent.role?.displayName ?: ""
            Picasso.get().load(agent.bustPortrait).into(binding.characterImage)
        }

        companion object {
            fun create(parent: ViewGroup): CharacterViewHolder {
                val binding: CharacterItemBinding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context))
                return CharacterViewHolder(binding)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Agent>() {
        override fun areItemsTheSame(oldItem: Agent, newItem: Agent): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Agent, newItem: Agent): Boolean {
            return oldItem.displayName == newItem.displayName
        }
    }
}