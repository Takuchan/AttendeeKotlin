package com.example.attendee

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutionException


class CameraDialogFragment : DialogFragment() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

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


                    // Select back camera as a default
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()

                    val options = BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                            Barcode.FORMAT_QR_CODE,
                            Barcode.FORMAT_AZTEC)
                        .enableAllPotentialBarcodes()
                        .build()

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview)

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext())) { imageProxy ->
                        val mediaImage = imageProxy.image
                        if (mediaImage != null) {
                            val image = InputImage.fromMediaImage(
                                mediaImage,
                                imageProxy.imageInfo.rotationDegrees
                            )

                            // Now you can use 'image' for barcode scanning or other ML Kit Vision tasks
                            // For example, you can pass 'image' to your barcode scanner
                            val scanner = BarcodeScanning.getClient(options)
                            val result = scanner.process(image)
                                .addOnSuccessListener { barcodes ->
                                    // Handle barcode scanning results here
                                    for (barcode in barcodes) {
                                        // Process each detected barcode
                                    }
                                }
                                .addOnFailureListener { e ->
                                    // Handle any errors here
                                    Log.e(TAG, "Barcode scanning failed: ${e.message}", e)
                                }
                        }

                        imageProxy.close() // Close the image proxy after processing
                    }
                } catch (e: ExecutionException) {
                    Log.e(TAG, e.localizedMessage, e)
                } catch (e: InterruptedException) {
                    Log.e(TAG, e.localizedMessage, e)
                }
            }, ContextCompat.getMainExecutor(requireContext()))


        return view
    }

}