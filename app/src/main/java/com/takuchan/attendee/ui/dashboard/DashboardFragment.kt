package com.takuchan.attendee.ui.dashboard

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takuchan.attendee.AttendeeApplication
import com.takuchan.attendee.AttendeeListAdapter
import com.takuchan.attendee.CreateNewAttendeeDialogFragment
import com.takuchan.attendee.R
import com.takuchan.attendee.database.AttendeeEntity
import com.takuchan.attendee.database.ProfileEntity
import com.takuchan.attendee.databinding.FragmentDashboardBinding
import com.takuchan.attendee.viewmodel.AttendeeViewFactory
import com.takuchan.attendee.viewmodel.AttendeeViewModel
import com.takuchan.attendee.viewmodel.ProfileViewModel
import com.takuchan.attendee.viewmodel.ProfileViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = AttendeeListAdapter()
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d("tablayout", tab.position.toString())
                if (tab.position == 0){
                    val fragment = CreatedAttendeeFragment()
                    val transaction = childFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    transaction.replace(R.id.fragmentContainerView,fragment)
                    transaction.commit()
//                    findNavController().navigate(DashboardFragmentDirections.actionNavigationDashboardToCreatedAttendeeFragment())
                }else if(tab.position == 1){
                    val fragment = JoinedAttendeeFragment()
                    val transaction = childFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    transaction.replace(R.id.fragmentContainerView,fragment)
                    transaction.commit()
//                    findNavController().navigate(DashboardFragmentDirections.actionNavigationDashboardToJoinedAttendeeFragment())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                Log.d("tablayout", tab?.position.toString())

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
//                Log.d("tablayout", tab?.position.toString())

            }
        })

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}