package com.example.attendee.ui.home

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.attendee.databinding.FragmentHomeBinding
import java.util.concurrent.ExecutorService

import android.Manifest
import android.content.ContentValues
import android.graphics.Camera
import android.os.Build

import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.attendee.AttendeeApplication
import com.example.attendee.CameraDialogFragment
import com.example.attendee.R
import com.example.attendee.viewmodel.ProfileViewModel
import com.example.attendee.viewmodel.ProfileViewModelFactory
import java.util.concurrent.Executors

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var cameraExecutor: ExecutorService

    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory((requireActivity().applicationContext as AttendeeApplication).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(requireActivity())[ProfileViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.button.setOnClickListener{
//            root.findNavController().navigate(R.id.profileFragment2)
            findNavController().navigate(R.id.action_navigation_home_to_profileFragment2)

        }
        binding.button3.setOnClickListener{
            if (allPermissionsGranted()) {
                // DialogFragmentのインスタンスを作成
                val newFragment = CameraDialogFragment()
                newFragment.show(requireActivity().supportFragmentManager, "game")
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
            }

            cameraExecutor = Executors.newSingleThreadExecutor()
        }
        binding.button2.setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        profileViewModel.myProfile.observe(viewLifecycleOwner, Observer {
            binding.textView.text = it?.name ?: "ようこそ!Attendeeへ"
            binding.textView2.text =
                (it?.affiliation ?: "プロフィールを設定してください") + (" / "+it?.assignment) ?: ""
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}