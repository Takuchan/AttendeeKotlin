package com.example.attendee

import android.app.Dialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutionException


class CameraDialogFragment : DialogFragment() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_camera_dialog, container, false)
            cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

            cameraProviderFuture.addListener({
                try {
                    // Used to bind the lifecycle of cameras to the lifecycle owner
                    val cameraProvider = cameraProviderFuture.get()

                    // Preview
                    val previewView = view.findViewById<PreviewView>(R.id.previewView)
                    val preview = Preview.Builder().build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)

                    // Select back camera as a default
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview)
                } catch (e: ExecutionException) {
                    Log.e(TAG, e.localizedMessage, e)
                } catch (e: InterruptedException) {
                    Log.e(TAG, e.localizedMessage, e)
                }
            }, ContextCompat.getMainExecutor(requireContext()))


        return view
    }

}