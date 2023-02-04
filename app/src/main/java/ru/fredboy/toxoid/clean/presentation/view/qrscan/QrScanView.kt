package ru.fredboy.toxoid.clean.presentation.view.qrscan

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

typealias ImageAnalyzer = ImageAnalysis.Analyzer

interface QrScanView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun bindCameraPreview(
        preview: Preview,
        cameraSelector: CameraSelector,
        imageAnalyzer: ImageAnalyzer
    )

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPermissionDeniedView()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setToxIdAndClose(toxId: String)

}