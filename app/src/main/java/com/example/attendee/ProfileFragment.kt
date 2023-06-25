package com.example.attendee

import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.attendee.database.AttendeeDatabase
import com.example.attendee.database.ProfileDao
import com.example.attendee.database.ProfileEntity
import com.example.attendee.databinding.FragmentProfileBinding
import com.example.attendee.ui.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.Executors


class ProfileFragment : Fragment(), ValidationTools {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    var isEmptyDatabase: Boolean = true
    var fullname: String = ""
    var affliation: String = ""
    var assignment: String = ""
    var telnumber: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        loadDB()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.saveProfileButton.setOnClickListener {
            if (!isEmptyEditText(binding.fullnameinput)) {

                fullname = changeEditTextToString(binding.fullnameinput)
                affliation = changeEditTextToString(binding.affiliationinput)
                assignment = changeEditTextToString(binding.assignmentinput)
                telnumber = changeEditTextToString(binding.telnumberinput)
                val executor = Executors.newSingleThreadExecutor()
                executor.execute(Runnable {
                    val database = AttendeeDatabase.getInstance(requireContext())
                    var profileDao: ProfileDao = database.userDao()
                    val sdf = SimpleDateFormat("yyyymmdd")
                    val date = Date()
                    val nowdate = sdf.format(date)
                    val profileEntity =
                        ProfileEntity(1, fullname, affliation, assignment, telnumber, nowdate)
                    if(isEmptyDatabase){
                        profileDao.insertAll(profileEntity)
                        isEmptyDatabase = false
                    }else{
                        profileDao.updateAll(profileEntity)
                    }
                })

            }
        }
        return root
    }

    fun loadDB() {
        val database = AttendeeDatabase.getInstance(requireContext())
        val userDao = database.userDao()

        GlobalScope.launch(Dispatchers.IO) {
            val myProfile = userDao.getMyProfile()
            withContext(Dispatchers.Main) {
                binding.fullnameinput.setText(myProfile?.name ?: "")
                binding.affiliationinput.setText(myProfile?.affiliation ?: "")
                binding.assignmentinput.setText(myProfile?.assignment ?: "")
                binding.telnumberinput.setText(myProfile?.telnumber ?: "")
                binding.finalEditDate.setText("最終更新日" + myProfile?.date ?: "")
            }
            isEmptyDatabase = myProfile == null
            println(isEmptyDatabase)
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