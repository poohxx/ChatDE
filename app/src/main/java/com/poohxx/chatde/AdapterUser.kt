package com.poohxx.chatde

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poohxx.chatde.databinding.UserListItemBinding

class AdapterUser: ListAdapter<User, AdapterUser.ItemHolder>(ItemComparator()) {
    class ItemHolder(private val binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(user: User) = with(binding){
        tvMessage.text = user.message
        tvUserName.text = user.name
    }
        companion object{
            fun create(parent: ViewGroup): ItemHolder{
                return ItemHolder(UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }
    class ItemComparator : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}