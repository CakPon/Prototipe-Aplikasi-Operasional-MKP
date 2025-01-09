package com.mkp.dataoperasional.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class WearPackResponse(
	@field:SerializedName("categories")
	val categories: List<Category>
)

data class Category(
	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("items")
	val items: List<WearPackItem>
)

data class WearPackItem(
	@field:SerializedName("color")
	val color: String,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)
@Parcelize
data class ItemsItem(
	@field:SerializedName("size")
	val size: String,

	@field:SerializedName("stock")
	var stock: Int
) : Parcelable
