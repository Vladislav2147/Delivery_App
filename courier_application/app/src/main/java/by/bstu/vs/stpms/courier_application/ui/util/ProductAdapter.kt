package by.bstu.vs.stpms.courier_application.ui.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.bstu.vs.stpms.courier_application.databinding.ProductLayoutBinding
import by.bstu.vs.stpms.courier_application.model.database.entity.Ordered

class ProductAdapter(private val context: Context) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onVariantClick(ordered: Ordered?)
    }

    var ordereds: List<Ordered>? = null

    var onClickListener: OnClickListener? = null

    fun setProducts(products: List<Ordered>?) {
        this.ordereds = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ProductLayoutBinding = ProductLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: Ordered = ordereds!![position]
        holder.binding?.ordered = product
        if (onClickListener != null) {
            holder.itemView.setOnClickListener {
                onClickListener?.onVariantClick(
                    product
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return if (ordereds == null) 0 else ordereds!!.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var binding: ProductLayoutBinding?

        init {
            binding = DataBindingUtil.bind(v)
        }
    }
}