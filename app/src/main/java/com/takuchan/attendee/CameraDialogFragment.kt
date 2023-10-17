package com.takuchan.attendee

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraDialogFragment : DialogFragment() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private val scanner : BarcodeScanner = BarcodeScanning.getClient()
    private val analyzer: ImageAnalyzer = ImageAnalyzer(scanner, ::onDetectCode)
    private val workerExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    @SuppressLint("UnsafeOptInUsageError")
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

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    val analysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(workerExecutor,analyzer)
                        }
                    cameraProvider.unbindAll()
                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(requireActivity(), cameraSelector, analysis,preview)
                } catch (e: ExecutionException) {
                    Log.e(TAG, e.localizedMessage, e)
                } catch (e: InterruptedException) {
                    Log.e(TAG, e.localizedMessage, e)
                }
            }, ContextCompat.getMainExecutor(requireContext()))


        return view
    }
    private fun onDetectCode(codes:List<Barcode>){
        codes.forEach{
            Log.d("tatagg",it.rawValue.toString())
        }
    }

}