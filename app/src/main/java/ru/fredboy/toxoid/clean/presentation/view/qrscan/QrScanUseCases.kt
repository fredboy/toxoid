package ru.fredboy.toxoid.clean.presentation.view.qrscan

import androidx.camera.core.ImageAnalysis
import ru.fredboy.toxoid.clean.domain.usecase.qr.GetQrImageAnalyzerUseCase
import javax.inject.Inject

class QrScanUseCases @Inject constructor(
    private val getQrImageAnalyzerUseCase: GetQrImageAnalyzerUseCase,
) {

    fun getImageAnalyzer(onQrDecoded: (String) -> Unit): ImageAnalysis.Analyzer {
        return getQrImageAnalyzerUseCase.execute(onQrDecoded)
    }

}