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
import com.example.huelllitas.adapters.FeaturedProductAdapter // <-- IMPORTAR NUEVO ADAPTADOR
import com.example.huelllitas.adapters.ProductAdapter
import com.example.huelllitas.model.Product
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    // --- VISTAS PARA PRODUCTOS RECOMENDADOS ---
    private lateinit var recommendedProductsRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    // --- NUEVAS VISTAS PARA EL CARRUSEL DESTACADO ---
    private lateinit var featuredCarousel: ViewPager2
    private lateinit var featuredAdapter: FeaturedProductAdapter
    private lateinit var dotsIndicator: TabLayout // <-- El indicador de puntos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar y configurar el carrusel destacado
        initFeaturedCarousel(view)

        // Inicializar y configurar la cuadrícula de recomendados
        initRecommendedGrid(view)
    }

    private fun initFeaturedCarousel(view: View) {
        featuredCarousel = view.findViewById(R.id.mainCarousel)
        dotsIndicator = view.findViewById(R.id.dotsIndicator)
        val featuredList = createFeaturedProducts()

        featuredAdapter = FeaturedProductAdapter(featuredList)
        featuredCarousel.adapter = featuredAdapter

        // Esto conecta el ViewPager2 con el indicador de puntos para que se muevan juntos
        TabLayoutMediator(dotsIndicator, featuredCarousel) { tab, position ->
            // No necesitamos hacer nada aquí, el mediador se encarga de todo
        }.attach()
    }

    private fun initRecommendedGrid(view: View) {
        recommendedProductsRecyclerView = view.findViewById(R.id.recommendedProductsGrid)
        val productList = createSampleData()
        productAdapter = ProductAdapter(productList)

        recommendedProductsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recommendedProductsRecyclerView.adapter = productAdapter
    }

    // Datos de ejemplo PARA EL CARRUSEL
    private fun createFeaturedProducts(): List<Product> {
        return listOf(
            Product(10, "Correa para perro", "$45.00", R.drawable.product_image),
            Product(11, "Juguete Interactivo", "$22.00", R.drawable.product_image), // Usa otra imagen
            Product(12, "Cama Ortopédica", "$75.00", R.drawable.product_image)  // Usa otra imagen
        )
    }

    // Datos de ejemplo PARA LA CUADRÍCULA
    private fun createSampleData(): List<Product> {
        return listOf(
            Product(1, "Bolsas desechos para...", "$15.00", R.drawable.product_image),
            Product(2, "Snacks Naturales", "$12.00", R.drawable.product_image),
            Product(3, "Cama Suave", "$25.00", R.drawable.product_image),
            Product(4, "Comida Premium", "$20.00", R.drawable.product_image),
            Product(5, "Shampoo Hipoalergénico", "$18.00", R.drawable.product_image),
            Product(6, "Hueso de Carnaza", "$8.00", R.drawable.product_image)
        )
    }
}
