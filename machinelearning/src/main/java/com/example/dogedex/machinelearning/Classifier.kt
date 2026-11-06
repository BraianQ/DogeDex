package com.example.dogedex.machinelearning

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.MappedByteBuffer

private const val MAX_RECOGNITION_DOG_RESULTS = 5

class Classifier(
    context: Context,
    modelPath: String = "model.tflite",
    labelsPath: String = "labels.txt"
) {
    private val tfLiteModel: MappedByteBuffer = FileUtil.loadMappedFile(context, modelPath)
    private val labels: List<String> = FileUtil.loadLabels(context, labelsPath)
    private val interpreter: Interpreter = Interpreter(tfLiteModel)

    fun recognizeImage(bitmap: Bitmap): List<ClassifierOutput> {
        val inputDataType = interpreter.getInputTensor(0).dataType()
        var tensorImage = TensorImage(inputDataType)
        tensorImage.load(bitmap)

        val inputShape = interpreter.getInputTensor(0).shape()
        val imageProcessorBuilder = ImageProcessor.Builder()
            .add(ResizeOp(inputShape[1], inputShape[2], ResizeOp.ResizeMethod.BILINEAR))

        if (inputDataType == DataType.FLOAT32) {
            imageProcessorBuilder.add(NormalizeOp(0.0f, 255.0f))
        }

        val imageProcessor = imageProcessorBuilder.build()
        tensorImage = imageProcessor.process(tensorImage)

        val outputTensor = interpreter.getOutputTensor(0)
        val outputBuffer = TensorBuffer.createFixedSize(outputTensor.shape(), outputTensor.dataType())

        interpreter.run(tensorImage.buffer, outputBuffer.buffer.rewind())

        val probabilityBuffer = TensorLabel(labels, outputBuffer)
        val labeledProbability = probabilityBuffer.mapWithFloatValue

        return getTopKProbability(labeledProbability)
    }

    private fun getTopKProbability(labelProb: Map<String, Float>): List<ClassifierOutput> {
        return labelProb.entries
            .map { entry ->
                ClassifierOutput(
                    id = entry.key,
                    confidence = entry.value
                )
            }
            .sortedByDescending { it.confidence }
            .take(MAX_RECOGNITION_DOG_RESULTS)
    }

    fun close() {
        interpreter.close()
    }
}
