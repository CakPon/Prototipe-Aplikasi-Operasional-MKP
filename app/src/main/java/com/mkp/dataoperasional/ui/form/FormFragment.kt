package com.mkp.dataoperasional.ui.form

import PostFormRequest
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mkp.dataoperasional.ViewModelFactory
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.databinding.FragmentFormBinding
import com.mkp.dataoperasional.utils.getImageUri
import com.mkp.dataoperasional.utils.reduceFileImage
import com.mkp.dataoperasional.utils.toBase64
import com.mkp.dataoperasional.utils.uriToFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class FormFragment : Fragment() {

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private var currentLocation: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel by viewModels<FormViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        val view = binding.root

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        setupDropdownMenus()

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        binding.sendButton.setOnClickListener {
            if (validateInputText()) {
                handleSubmit()
            } else {
                binding.sendButton.isEnabled = false
            }
        }

        binding.cameraButton.setOnClickListener {
            startCamera()
        }
        binding.nameInput.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            validateInputText()
        })
        binding.userDropdown.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            validateInputText()
        })
        binding.unitDropdown.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            validateInputText()
        })
        binding.jobDropdown.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            validateInputText()
        })
        binding.projectInput.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            validateInputText()
        })
        binding.descInput.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            validateInputText()
        })
        binding.foundDropdown.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            validateInputText()
        })

        return view
    }
    private fun validateInputText(): Boolean {
        val name = binding.nameInput.text.toString()
        val user = binding.userDropdown.text.toString()
        val unit = binding.unitDropdown.text.toString()
        val job = binding.jobDropdown.text.toString()
        val project = binding.projectInput.text.toString()
        val foundItems = binding.foundDropdown.text.toString()
        val description = binding.descInput.text.toString()

        val isNameValid = name.isNotEmpty()
        val isUserValid = user.isNotEmpty()
        val isUnitValid = unit.isNotEmpty()
        val isJobValid = job.isNotEmpty()
        val isProjectValid = project.isNotEmpty()
        val isFoundItems = foundItems.isNotEmpty()
        val isDescriptionValid = description.isNotEmpty()

        val isImageValid = currentImageUri != null

        binding.sendButton.isEnabled = isNameValid && isUserValid && isUnitValid && isJobValid && isProjectValid && isFoundItems && isDescriptionValid && isImageValid
        return isNameValid && isUserValid && isUnitValid && isJobValid && isProjectValid && isFoundItems && isDescriptionValid && isImageValid
    }

    private fun setupDropdownMenus() {
        val userItems = listOf("PLN", "PLN NP", "PLN NPS", "IPP")
        val unitItems = listOf("Alat Berat KPJB", "Overhaul UJP", "PLN DISJATIM", "PLN NP Kantor Pusat", "PLN NPS Kantor Pusat", "PLN UP3 SDA", "PLTA Asahan", "PLTA Cirata", "PLTD Sei Wie", "PLTD Telaga", "PLTG Ternate", "PLTMG Bawean", "PLTMG Flores", "PLTMG Kendari", "PLTMH Sampean Baru", "PLTU Ampana", "PLTU Amurang", "PLTU Anggrek", "PLTU Arun", "PLTU Bangka", "PLTU Banjarsari", "PLTU Belitung", "PLTU Bolok", "PLTU Brantas", "PLTU Cilacap", "PLTU Gresik", "PLTU Halmahera Timur", "PLTU Indramayu", "PLTU Kaltim Teluk", "PLTU Kendari", "PLTU Ketapang", "PLTU Muara Karang", "PLTU Muara Karang Preventive Corrective 4,5,2", "PLTU Muara Karang Preventive dan WWTP", "PLTU Muara Karang Traveling Screen", "PLTU Muara Tawar", "PLTU Muara Tawar CNG", "PLTU Pacitan", "PLTU Paiton", "PLTU Pangkalan Susu", "PLTU Pulang Pisau", "PLTU Rembang", "PLTU Ropa", "PLTU Sambelia", "PLTU Sengkang", "PLTU Suppa", "PLTU Tanjung Awar2", "PLTU Tanjung Jati B", "PLTU Tanjung Jati B BJS - Utility Service", "PLTU Tanjung Jati B KPJB", "PLTU Tanjung Jati B TJBPS", "PLTU Tembilahan", "PLTU Tenayan", "PLTU Tidore", "Pendampingan Tenaga Teknik Bidang Pembangkitan", "Tarakan", "UJP Puma", "ULPLTA Koto Panjang", "ULPLTD Nopi", "ULPLTD Silae", "ULPTG/MG Duri", "UPLPTD Teluk Lembu", "Workshop Bitung", "Workshop Palembang")
        val jobItems = listOf("Proyek", "Non-Proyek")
        val foundItems = listOf("Positive Action", "Unsafe Action", "Unsafe Condition", "Near Miss")

        (binding.userDropdown as MaterialAutoCompleteTextView).setSimpleItems(userItems.toTypedArray())
        (binding.unitDropdown as MaterialAutoCompleteTextView).setSimpleItems(unitItems.toTypedArray())
        (binding.jobDropdown as MaterialAutoCompleteTextView).setSimpleItems(jobItems.toTypedArray())
        (binding.foundDropdown as MaterialAutoCompleteTextView).setSimpleItems(foundItems.toTypedArray())
    }

    private fun handleSubmit() {
        val name = binding.nameInput.text.toString().trim()
        val user = binding.userDropdown.text.toString().trim()
        val unit = binding.unitDropdown.text.toString().trim()
        val job = binding.jobDropdown.text.toString().trim()
        val project = binding.projectInput.text.toString().trim()
        val foundItems = binding.foundDropdown.text.toString().trim()
        val description = binding.descInput.text.toString().trim()

        val lat = currentLocation?.latitude
        val lon = currentLocation?.longitude
        val location = if (lat != null && lon != null) {
            "https://www.google.com/maps/place/$lat,$lon"
        } else {
            showToast("Lokasi tidak tersedia")
            return
        }

        val imageFile = uriToFile(currentImageUri!!, requireContext()).reduceFileImage()
        val base64Image = imageFile.toBase64()

        val request = PostFormRequest(
            action = "postForm",
            nama = name,
            user = user,
            unit = unit,
            jenisPekerjaan = job,
            namaProyek = project,
            jenisTemuan = foundItems,
            deskripsi = description,
            image = base64Image,
            location = location
        )

        viewModel.uploadForm(request).observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> showLoading(true)
                is ResultState.Success -> {
                    showToast("Sukses")
                    clearInputs()
                    showLoading(false)
                }
                is ResultState.Error -> {
                    showToast(result.error)
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun clearInputs() {
        binding.nameInput.text?.clear()
        binding.userDropdown.setText("")
        binding.unitDropdown.setText("")
        binding.jobDropdown.setText("")
        binding.projectInput.setText("")
        binding.foundDropdown.setText("")
        binding.descInput.text?.clear()
        binding.edAddImage.setImageResource(android.R.color.transparent)
        currentImageUri = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                currentLocation = LatLng(location.latitude, location.longitude)
                Log.d("Location", "Lat: ${location.latitude}, Lon: ${location.longitude}")
            } ?: Log.d("Location", "Location is null")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) showImage()
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.edAddImage.setImageURI(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
