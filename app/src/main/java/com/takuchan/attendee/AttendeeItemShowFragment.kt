package com.takuchan.attendee

import android.net.Uri
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
import com.takuchan.attendee.databinding.FragmentAttendeeItemShowBinding
import com.takuchan.attendee.databinding.FragmentProfileBinding
import com.takuchan.attendee.models.QrcodeModel
import com.takuchan.attendee.viewmodel.AttendeeViewFactory
import com.takuchan.attendee.viewmodel.AttendeeViewModel
import com.google.mlkit.vision.barcode.common.Barcode.Address
import com.journeyapps.barcodescanner.BarcodeEncoder


class AttendeeItemShowFragment() : Fragment() {

    private var _binding: FragmentAttendeeItemShowBinding? = null
    private val binding get() = _binding!!

    private lateinit var qrCodeAddress : Uri
    private lateinit var sheetDetail: QrcodeModel

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
            val qrcodeModel = QrcodeModel()
            //TODO: モデルを定義するのだが、UIDがない。
        })
        val barcodeEncoder = BarcodeEncoder()
        val sheetQrCode = barcodeEncoder.encodeBitmap()
        binding.generateqrImageView.setImageBitmap()

        return root
    }

    companion object {

    }
}