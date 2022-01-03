package uz.shokirov.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.shokirov.messenger.databinding.ItemFromBinding
import uz.shokirov.messenger.databinding.ItemToBinding
import uz.shokirov.models.Group
import uz.shokirov.models.Message

class GroupAdapter(var list: List<Group>, val uid: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class FromVh(var itemRv: ItemFromBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(group: Group) {
            itemRv.tvText.text = group.text
            itemRv.tvDate.text = group.date
            itemRv.tvUserName.text = group.own
        }
    }

    inner class ToVh(var itemRv: ItemToBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(group: Group) {
            itemRv.tvText.text = group.text
            itemRv.tvDate.text = group.date
            itemRv.tvUserName.text = group.own
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return FromVh(
                ItemFromBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return ToVh(ItemToBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            val fromVh = holder as FromVh
            fromVh.onBind(list[position])
        } else {
            val toVh = holder as ToVh
            toVh.onBind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        if (list[position].fromUid == uid) {
            return 1
        } else {
            return 2
        }
        return super.getItemViewType(position)

    }
}