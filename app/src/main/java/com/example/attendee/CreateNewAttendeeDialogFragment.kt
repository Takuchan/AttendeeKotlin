package com.example.attendee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.attendee.databinding.FragmentCreateNewAttendeeDialogBinding
import com.example.attendee.databinding.FragmentDashboardBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateNewAttendeeDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCreateNewAttendeeDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateNewAttendeeDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    companion object {
        // set: fragment object, requestCode
        @JvmStatic
        fun newInstance(fragment: Fragment, requestCode: Int): CreateNewAttendeeDialogFragment {
            val dialog = CreateNewAttendeeDialogFragment()
            dialog.setTargetFragment(fragment, requestCode)

            val args = Bundle()
            fragment.arguments = args

            return  dialog
        }
    }
}