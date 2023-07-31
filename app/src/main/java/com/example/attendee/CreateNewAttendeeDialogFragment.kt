package com.example.attendee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.attendee.database.AttendeeEntity
import com.example.attendee.databinding.FragmentCreateNewAttendeeDialogBinding
import com.example.attendee.databinding.FragmentDashboardBinding
import com.example.attendee.viewmodel.AttendeeViewFactory
import com.example.attendee.viewmodel.AttendeeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class CreateNewAttendeeDialogFragment : BottomSheetDialogFragment(), ValidationTools {

    private var _binding: FragmentCreateNewAttendeeDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        // Inflate the layout for this fragment
        _binding = FragmentCreateNewAttendeeDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.button6.setOnClickListener{
            if (isEmptyEditText(binding.textinput1)){

            }else{
                val title: String = changeEditTextToString(binding.textinput1)
                val location:String = changeEditTextToString(binding.textinput2)
                val publicMessage:String = changeEditTextToString(binding.textinput3)
                val privateMessage:String= changeEditTextToString(binding.textinput4)
                val now = Date();
                val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                val result = sdf.format(now)
                val profileEntity =
                    AttendeeEntity(id = 0,title = title,location = location, publicmemo = publicMessage,privatememo = privateMessage,date = result)
                attendeeViewModel.insert(profileEntity)
            }

        }

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

    override fun isEmptyEditText(editText: EditText?): Boolean {
        return editText!!.text.toString().isEmpty()
    }

    override fun changeEditTextToString(editText: EditText?): String {
        return editText!!.text.toString().ifEmpty {
            ""
        }
    }
}