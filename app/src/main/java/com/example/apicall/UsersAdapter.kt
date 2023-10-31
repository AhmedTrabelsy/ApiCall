package com.example.apicall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class UsersAdapter(private val dataSet: ArrayList<User>, private val listener: OnItemClickListener) : RecyclerView.Adapter<UsersAdapter.ViewHolder>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.usercard, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = dataSet[position]

        // Set the user data to the TextViews in the ViewHolder
        holder.name.text = "Name: ${user.name}"
        holder.username.text = "UserName: ${user.username}"
        holder.email.text = "Email: ${user.email}"
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener  {
        val name: TextView = view.findViewById(R.id.name)
        val username: TextView = view.findViewById(R.id.username)
        val email: TextView = view.findViewById(R.id.email)
        val cardViewUser: CardView = view.findViewById(R.id.cardViewUser)

        init {
            cardViewUser.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    listener.selectItem(position)
                }
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.selectItem(position)
            }
        }
    }


    interface OnItemClickListener {
        fun selectItem(position: Int)
    }


}
