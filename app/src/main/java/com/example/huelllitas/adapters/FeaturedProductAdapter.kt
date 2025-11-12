package com.example.huelllitas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.huelllitas.R
import com.example.huelllitas.model.Product

// Adaptador para el ViewPager2 del carrusel principal
class FeaturedProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<FeaturedProductAdapter.FeaturedViewHolder>() {

    class FeaturedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.featuredProductImage)
        val productName: TextView = itemView.findViewById(R.id.featuredProductName)
        // Puedes añadir aquí el RatingBar si quieres modificarlo dinámicamente
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        // Usamos el nuevo layout que creamos
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_featured_product, parent, false)
        return FeaturedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.name
        holder.productImage.setImageResource(product.imageResId)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
