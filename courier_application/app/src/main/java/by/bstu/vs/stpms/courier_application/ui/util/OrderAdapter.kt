package by.bstu.vs.stpms.courier_application.ui.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.bstu.vs.stpms.courier_application.databinding.AvailableOrderLayoutBinding
import by.bstu.vs.stpms.courier_application.model.database.entity.Order

class OrderAdapter(private val context: Context) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onVariantClick(order: Order?)
    }

    var availableOrders: List<Order>? = null

    var onClickListener: OnClickListener? = null

    fun setArticles(orders: List<Order>?) {
        this.availableOrders = orders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: AvailableOrderLayoutBinding = AvailableOrderLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order: Order = availableOrders!![position]
        holder.binding?.order = order
        if (onClickListener != null) {
            holder.itemView.setOnClickListener {
                onClickListener?.onVariantClick(
                        order
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return if (availableOrders == null) 0 else availableOrders!!.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var binding: AvailableOrderLayoutBinding?

        init {
            binding = DataBindingUtil.bind(v)
        }
    }
}
