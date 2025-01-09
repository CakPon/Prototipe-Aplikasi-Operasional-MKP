package com.mkp.dataoperasional.ui.check

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.mkp.dataoperasional.data.response.Category
import com.mkp.dataoperasional.data.response.WearPackItem
import com.mkp.dataoperasional.databinding.ListCategoryBinding
import com.mkp.dataoperasional.databinding.ListGroupBinding
import com.mkp.dataoperasional.ui.check.detail.DetailGetDataActivity

class GetDataExpandableListAdapter(
    private val categories: List<Category>,
    private val context: Context
) : BaseExpandableListAdapter() {

    private val colorMapping = mapOf(
        "BIRU OM" to Color.BLUE,
        "BIRU OH" to Color.BLUE,
        "BIRU PLN" to Color.parseColor("#00BCD7"),
        "KAOS OH" to Color.parseColor("#00479C"),
        "MERAH OM" to Color.RED,
        "MERAH OH" to Color.RED,
        "ORANGE" to Color.parseColor("#FFA500"),
        "HIJAU" to Color.parseColor("#00BA4E"),
        "ABU-ABU" to Color.GRAY,
        "DONGKER OM" to Color.parseColor("#00479C"),
        "HITAM" to Color.BLACK,
        "PUTIH" to Color.WHITE,
        "HIJAU IC" to Color.parseColor("#006400"),
        "KUNING" to Color.YELLOW,
        "BIRU" to Color.BLUE,
        "MERAH" to Color.RED,
        "DONGKER" to Color.parseColor("#00479C"),
        "KREM" to Color.parseColor("#fce5cd")

    )

    override fun getGroupCount(): Int = categories.size

    override fun getChildrenCount(groupPosition: Int): Int =
        categories[groupPosition].items.size

    override fun getGroup(groupPosition: Int): Any = categories[groupPosition].category

    override fun getChild(groupPosition: Int, childPosition: Int): Any =
        categories[groupPosition].items[childPosition]

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long =
        childPosition.toLong()

    override fun hasStableIds(): Boolean = true

    override fun getGroupView(
        groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?
    ): View {
        val binding: ListCategoryBinding
        val view: View

        if (convertView == null) {
            binding = ListCategoryBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            view = binding.root
        } else {
            binding = ListCategoryBinding.bind(convertView)
            view = convertView
        }

        binding.textItem.text = getGroup(groupPosition) as String
        return view
    }

    override fun getChildView(
        groupPosition: Int, childPosition: Int, isLastChild: Boolean,
        convertView: View?, parent: ViewGroup?
    ): View {
        val binding: ListGroupBinding
        val view: View

        if (convertView == null) {
            binding = ListGroupBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            view = binding.root
        } else {
            binding = ListGroupBinding.bind(convertView)
            view = convertView
        }

        val item = getChild(groupPosition, childPosition) as WearPackItem
        val colorName = item.color
        binding.groupTitle.text = colorName

        val blackBackgroundColors = listOf("HITAM", "KAOS OH", "DONGKER OM", "HIJAU IC", "BIRU OM", "BIRU OH", "BIRU", "MERAH OM", "MERAH OH", "MERAH", "DONGKER")

        if (colorName in blackBackgroundColors) {
            binding.groupTitle.setBackgroundColor(colorMapping[colorName] ?: Color.WHITE)
            binding.groupTitle.setTextColor(Color.WHITE)
        } else {
            val backgroundColor = colorMapping[colorName] ?: Color.WHITE
            binding.groupTitle.setBackgroundColor(backgroundColor)
            binding.groupTitle.setTextColor(Color.BLACK)
        }

        view.setOnClickListener {
            val intent = Intent(context, DetailGetDataActivity::class.java).apply {
                putExtra("CATEGORY", getGroup(groupPosition) as String)
                putExtra("COLOR", item.color)
            }
            context.startActivity(intent)
        }

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
}
