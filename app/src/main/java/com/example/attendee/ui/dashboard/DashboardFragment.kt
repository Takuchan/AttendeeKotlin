package com.example.attendee.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendee.AttendeeApplication
import com.example.attendee.AttendeeListAdapter
import com.example.attendee.CreateNewAttendeeDialogFragment
import com.example.attendee.R
import com.example.attendee.database.AttendeeEntity
import com.example.attendee.database.ProfileEntity
import com.example.attendee.databinding.FragmentDashboardBinding
import com.example.attendee.viewmodel.AttendeeViewFactory
import com.example.attendee.viewmodel.AttendeeViewModel
import com.example.attendee.viewmodel.ProfileViewModel
import com.example.attendee.viewmodel.ProfileViewModelFactory

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.createNewAttendeeSheet.setOnClickListener{
            val createattendeedialog = CreateNewAttendeeDialogFragment()
            createattendeedialog.show((requireActivity()).supportFragmentManager,"")
            val profileEntity =
                AttendeeEntity(0,"ahokusa","fahokusa","ahokussa","private","public")
            attendeeViewModel.insert(profileEntity)
        }

        binding.recylcerView.adapter = adapter
        binding.recylcerView.layoutManager = LinearLayoutManager(context)
        attendeeViewModel.allAttendee.observe(viewLifecycleOwner,Observer { attendee ->
            attendee.let { adapter.submitList(it) }
        })
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}