import java.io.File

data class PostRequest(
	val action: String,
	val category: String,
	val targetStrings: List<String>,
	val newStockArray: List<Int>
)

data class PostOneRowRequest(
	val action: String,
	val targetCategory: String,
	val newStock: Int
)

data class PostFormRequest(
	val action: String,
	val nama: String,
	val user: String,
	val unit: String,
	val jenisPekerjaan: String,
	val namaProyek: String,
	val jenisTemuan: String,
	val deskripsi: String,
	val image: String,
	val location: String
)

data class PostResponse(
	val status: String,
	val message: String
)
