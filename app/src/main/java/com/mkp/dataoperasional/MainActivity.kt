package com.mkp.dataoperasional

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        // Set up ViewPager with an adapter
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Check Stock"
                    tab.setIcon(R.drawable.baseline_manage_search_24)
                }
                1 -> {
                    tab.text = "Restock"
                    tab.setIcon(R.drawable.baseline_add_24)
                }
                2 -> {
                    tab.text = "Ambil Stock"
                    tab.setIcon(R.drawable.baseline_remove_24)
                }
                3 -> {
                    tab.text = "Inspeksi Proyek"
                    tab.setIcon(R.drawable.baseline_format_list_bulleted_add_24)
                }
            }
        }.attach()
    }
}