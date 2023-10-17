package com.takuchan.attendee.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.takuchan.attendee.AttendeeApplication
import com.takuchan.attendee.AttendeeListAdapter
import com.takuchan.attendee.CreateNewAttendeeDialogFragment
import com.takuchan.attendee.R
import com.takuchan.attendee.databinding.FragmentCreatedAttendeeBinding
import com.takuchan.attendee.databinding.FragmentDashboardBinding
import com.takuchan.attendee.viewmodel.AttendeeViewFactory
import com.takuchan.attendee.viewmodel.AttendeeViewModel


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