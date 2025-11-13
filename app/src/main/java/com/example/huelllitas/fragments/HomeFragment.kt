package com.example.huelllitas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.huelllitas.R
import com.example.huelllitas.adapters.FeaturedProductAdapter
import com.example.huelllitas.adapters.ProductAdapter
import com.example.huelllitas.model.Product
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var recommendedProductsRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    private lateinit var featuredCarousel: ViewPager2
    private lateinit var featuredAdapter: FeaturedProductAdapter
    private lateinit var dotsIndicator: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFeaturedCarousel(view)

        initRecommendedGrid(view)
    }

    private fun initFeaturedCarousel(view: View) {
        featuredCarousel = view.findViewById(R.id.mainCarousel)
        dotsIndicator = view.findViewById(R.id.dotsIndicator)
        val featuredList = createFeaturedProducts()

        featuredAdapter = FeaturedProductAdapter(featuredList)
        featuredCarousel.adapter = featuredAdapter

        TabLayoutMediator(dotsIndicator, featuredCarousel) { tab, position ->

        }.attach()
    }

    private fun initRecommendedGrid(view: View) {
        recommendedProductsRecyclerView = view.findViewById(R.id.recommendedProductsGrid)
        val productList = createSampleData()
        productAdapter = ProductAdapter(productList)

        recommendedProductsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recommendedProductsRecyclerView.adapter = productAdapter
    }

    private fun createFeaturedProducts(): List<Product> {
        return listOf(
            Product(10, "Correa para perro", "$45.00", R.drawable.correa_perro),
            Product(11, "Juguete Interactivo", "$22.00", R.drawable.plato_interactivo),
            Product(12, "Cama Ortopédica", "$75.00", R.drawable.cama_ortopedica)
        )
    }

    private fun createSampleData(): List<Product> {
        return listOf(
            Product(1, "Bolsas desechos para...", "$15.00", R.drawable.bolsas_desechos),
            Product(2, "Snacks Naturales", "$12.00", R.drawable.snacks_naturales),
            Product(3, "Cama Suave", "$25.00", R.drawable.cama_suave),
            Product(4, "Comida Premium", "$20.00", R.drawable.comida_premium),
            Product(5, "Shampoo Hipoalergénico", "$18.00", R.drawable.shampo_hipoalergenico),
            Product(6, "Hueso de Carnaza", "$8.00", R.drawable.hueso_de_carnaza)
        )
    }
}