package com.takuchan.attendee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.takuchan.attendee.database.ProfileEntity
import com.takuchan.attendee.databinding.FragmentProfileBinding
import com.takuchan.attendee.viewmodel.ProfileViewModel
import com.takuchan.attendee.viewmodel.ProfileViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date


class ProfileFragment : Fragment(), ValidationTools {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    var isEmptyDatabase: Boolean = false
    var fullname: String = ""
    var affliation: String = ""
    var assignment: String = ""
    var telnumber: String = ""

    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory((requireActivity().application as AttendeeApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root



        binding.saveProfileButton.setOnClickListener {
            if (!isEmptyEditText(binding.fullnameinput)) {

                fullname = changeEditTextToString(binding.fullnameinput)
                affliation = changeEditTextToString(binding.affiliationinput)
                assignment = changeEditTextToString(binding.assignmentinput)
                telnumber = changeEditTextToString(binding.telnumberinput)
                val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                val date = Date()
                val nowdate = sdf.format(date)
                val profileEntity =
                    ProfileEntity(1, fullname, affliation, assignment, telnumber, nowdate)
                if (isEmptyDatabase){
                    profileViewModel.insert(profileEntity)
                    isEmptyDatabase = false
                }else{
                    profileViewModel.updateMyProfile(profileEntity)
                }
            }
        }

        profileViewModel.myProfile.observe(viewLifecycleOwner, Observer {
            if (it == null) isEmptyDatabase = true
            binding.fullnameinput.setText(it?.name ?: "")
            binding.affiliationinput.setText(it?.affiliation ?: "")
            binding.assignmentinput.setText(it?.assignment ?: "")
            binding.telnumberinput.setText(it?.telnumber ?: "")
            binding.finalEditDate.setText("最終更新日" + it?.date ?: "")
        })
        return root
    }


//


    override fun isEmptyEditText(editText: EditText?): Boolean {
        return editText!!.text.toString().isEmpty()
    }

    override fun changeEditTextToString(editText: EditText?): String {
        return editText!!.text.toString().ifEmpty {
            ""
        }
    }


}