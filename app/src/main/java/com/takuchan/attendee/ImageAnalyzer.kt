package com.takuchan.attendee

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class ImageAnalyzer(
    private val scanner : BarcodeScanner,
    private val callback: (List<Barcode>) -> Unit
): ImageAnalysis.Analyzer {
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val image = imageProxy.image
        if(image != null){
            val image = InputImage.fromMediaImage(image,imageProxy.imageInfo.rotationDegrees)
            Log.d("imageinfo",image.height.toString())
            scanner.process(image)
                .addOnSuccessListener{barcodes ->
                    callback(barcodes)
                    for (barcode in barcodes) {
                        val bounds = barcode.boundingBox
                        val corners = barcode.cornerPoints

                        val rawValue = barcode.rawValue
                        Log.d("rawValue",rawValue.toString())

                        val valueType = barcode.valueType
                        // See API reference for complete list of supported types
                        when (valueType) {
                            Barcode.TYPE_WIFI -> {
                                val ssid = barcode.wifi!!.ssid
                                val password = barcode.wifi!!.password
                                val type = barcode.wifi!!.encryptionType
                            }
                            Barcode.TYPE_URL -> {
                                val title = barcode.url!!.title
                                val url = barcode.url!!.url
                            }
                        }
                    }
                }
                .addOnCompleteListener{imageProxy.close()}
        }else{
            imageProxy.close()
        }
    }
}