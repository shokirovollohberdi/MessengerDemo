package uz.shokirov.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.shokirov.messenger.databinding.ItemRvBinding
import uz.shokirov.models.User

class RvUserListAdapter(var list: ArrayList<User>,var onClick: OnClick) : RecyclerView.Adapter<RvUserListAdapter.Vh>() {
    inner class Vh(var itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(user: User, position: Int) {
            itemRv.tvDisplayName.text = user.displayName
            itemRv.tvEmail.text = user.email
            Picasso.get().load(user.imageUrl).into(itemRv.profileImage)
            itemRv.root.setOnClickListener {
                onClick.onClick(list[position],position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent?.context), parent, false))
    }


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}interface OnClick{
    fun onClick(user: User,position: Int)
}