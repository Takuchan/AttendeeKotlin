package com.example.attendee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.attendee.databinding.FragmentAttendeeItemShowBinding
import com.example.attendee.databinding.FragmentProfileBinding
import com.example.attendee.viewmodel.AttendeeViewFactory
import com.example.attendee.viewmodel.AttendeeViewModel


class AttendeeItemShowFragment() : Fragment() {

    private var _binding: FragmentAttendeeItemShowBinding? = null
    private val binding get() = _binding!!

    private val args : AttendeeItemShowFragmentArgs by navArgs()
    private val attendeeViewModel: AttendeeViewModel by viewModels {
        AttendeeViewFactory((requireActivity().applicationContext as AttendeeApplication).attendeeRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAttendeeItemShowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        attendeeViewModel.loadAttendee(args.getAttendee).observe(viewLifecycleOwner, Observer { attendeeEntitiy ->
            binding.textView10.text = attendeeEntitiy.title
            binding.textView11.text = attendeeEntitiy.location
            binding.textView13.text = attendeeEntitiy.publicmemo
            binding.textView14.text = attendeeEntitiy.privatememo
        })

        return root
    }

    companion object {

    }
}