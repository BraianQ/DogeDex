package com.example.dogedex.camera.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class DogImageAnalyzer(
    private val onFinishAnalysis: (Double) -> Unit = {}
) : ImageAnalysis.Analyzer {

    override fun analyze(imageProxy: ImageProxy) {
        val rotationDegrees = imageProxy.imageInfo.rotationDegrees
        onFinishAnalysis(rotationDegrees.toDouble())

        imageProxy.close()
    }
}
