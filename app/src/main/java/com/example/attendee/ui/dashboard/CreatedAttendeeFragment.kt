package com.example.attendee.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendee.AttendeeApplication
import com.example.attendee.AttendeeListAdapter
import com.example.attendee.CreateNewAttendeeDialogFragment
import com.example.attendee.R
import com.example.attendee.databinding.FragmentCreatedAttendeeBinding
import com.example.attendee.databinding.FragmentDashboardBinding
import com.example.attendee.viewmodel.AttendeeViewFactory
import com.example.attendee.viewmodel.AttendeeViewModel


class CreatedAttendeeFragment : Fragment() {
    private var _binding: FragmentCreatedAttendeeBinding? = null

    private val binding get() = _binding!!

    private val attendeeViewModel: AttendeeViewModel by viewModels {
        AttendeeViewFactory((requireActivity().applicationContext as AttendeeApplication).attendeeRepository)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = AttendeeListAdapter()
        _binding = FragmentCreatedAttendeeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        binding.createNewAttendeeSheet3.setOnClickListener{
            val createattendeedialog = CreateNewAttendeeDialogFragment()
            createattendeedialog.show((requireActivity()).supportFragmentManager,"")
        }

        binding.recylcerView.adapter = adapter
        binding.recylcerView.layoutManager = LinearLayoutManager(context)
        attendeeViewModel.allAttendee.observe(viewLifecycleOwner, Observer { attendee ->
            attendee.let { adapter.submitList(it) }
        })

        return root
    }
}