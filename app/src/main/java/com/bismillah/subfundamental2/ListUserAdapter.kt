package com.bismillah.subfundamental2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.subfundamental2.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.CardViewViewHolder>(){
    private val user = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(user : User){
            with(itemView){
                Glide.with(binding.cardView.context)
                    .load(user.photo)
                    .apply(RequestOptions())
                    .into(binding.imgPhoto)
                binding.tvUsername.text = user.username
                binding.cardView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user.username.toString())
                }
            }
        }
    }

    fun setData(items: ArrayList<User>) {
        user.clear()
        user.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(user[position])
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = user.size



    interface OnItemClickCallback {
        fun onItemClicked(username: String)
    }
}


