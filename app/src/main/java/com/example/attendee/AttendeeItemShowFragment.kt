package com.example.attendee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.attendee.databinding.FragmentAttendeeItemShowBinding
import com.example.attendee.databinding.FragmentProfileBinding


class AttendeeItemShowFragment() : Fragment() {
    private val args: AttendeeItemShowFragment by navArgs()


    private var _binding: FragmentAttendeeItemShowBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAttendeeItemShowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    companion object {

    }
}