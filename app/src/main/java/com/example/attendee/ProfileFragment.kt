package com.example.attendee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.attendee.database.AttendeeDatabase
import com.example.attendee.database.ProfileDao
import com.example.attendee.database.ProfileEntity
import com.example.attendee.databinding.FragmentProfileBinding
import com.example.attendee.ui.home.HomeViewModel
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
    var affliation:String =""
    var assignment:String =""
    var telnumber:String =""

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
            if(!isEmptyEditText(binding.fullnameinput)){

                fullname = changeEditTextToString(binding.fullnameinput)
                affliation = changeEditTextToString(binding.affiliationinput)
                assignment = changeEditTextToString(binding.assignmentinput)
                telnumber = changeEditTextToString(binding.telnumberinput)
                    val executor = Executors.newSingleThreadExecutor()
                    executor.execute(Runnable {
                        val db = Room.databaseBuilder(
                            requireContext(),
                            AttendeeDatabase::class.java, "ATTENDEE"
                        ).build()
                        var profileDao:ProfileDao = db.userDao()

                        if(!isEmptyDatabase){
                            val date:Date = Date()
                            val sdf = SimpleDateFormat("yyyymmdd")
                            val nowdate = sdf.format(date)
                            val profileEntity : ProfileEntity = ProfileEntity(0,fullname,affliation,assignment,telnumber,nowdate)
                            profileDao.insertAll(profileEntity)
                        }
                    })

            }
        }
        return root
    }

    fun loadDB(){
        GlobalScope.launch(Dispatchers.Main) {
            // まっさらに
            // UIスレッドでは実行できないためコルーチン
            val list = withContext(Dispatchers.IO) {
                // データベース用意
                val database = AttendeeDatabase.getInstance(this@ProfileFragment.requireContext())
                val dao = database.userDao()
                dao.getAll()
            }
            if (list.isEmpty()){
                isEmptyDatabase = true
            }else{
                binding.fullnameinput.setText(list[0].name)
                binding.affiliationinput.setText(list[0].affiliation)
                binding.assignmentinput.setText(list[0].assignment)
                binding.telnumberinput.setText(list[0].telnumber)
                isEmptyDatabase = false
            }
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