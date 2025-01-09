package com.mkp.dataoperasional

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mkp.dataoperasional.ui.decrease.DecreaseFragment
import com.mkp.dataoperasional.ui.check.CheckStockFragment
import com.mkp.dataoperasional.ui.form.FormFragment
import com.mkp.dataoperasional.ui.restock.RestockFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CheckStockFragment()
            1 -> RestockFragment()
            2 -> DecreaseFragment()
            3 -> FormFragment()
            else -> CheckStockFragment()
        }
    }
}
