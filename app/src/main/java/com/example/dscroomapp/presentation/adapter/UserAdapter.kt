package com.example.dscroomapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dscroomapp.R
import com.example.dscroomapp.entities.UserItem

class UserAdapter: RecyclerView.Adapter<UserViewHolder>() {
    private val userItem = ArrayList<UserItem>()

    fun setItems(new_items: ArrayList<UserItem>){
        this.userItem.clear()
        this.userItem.addAll(new_items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(layout)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(userItem[position])

    override fun getItemCount() = userItem.size
}

class UserViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    private val name: TextView = item.findViewById(R.id.name)
    private val id: TextView = item.findViewById(R.id.user_id)
    private val age: TextView = item.findViewById(R.id.user_age)
    private val userInitials: TextView = item.findViewById(R.id.user_initials)

    fun bind(userItem: UserItem){
        name.text = userItem.name
        id.text = "userId: ${userItem.id}"
        age.text = "userAge: ${userItem.age}"
        userInitials.text = userItem.name.take(1) + userItem.name.takeLast(3)
    }

}
