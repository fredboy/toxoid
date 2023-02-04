package ru.fredboy.toxoid.clean.presentation.view.qrscan

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpPresenter
import ru.fredboy.toxoid.utils.exhaustive
import ru.fredboy.toxoid.utils.validateToxId
import splitties.permissions.PermissionRequestResult
import javax.inject.Inject

class QrScanPresenter @Inject constructor(
    private val useCases: QrScanUseCases,
) : BaseMvpPresenter<QrScanView>() {

    fun onCameraPermissionRequestResult(result: PermissionRequestResult) {
        when (result) {
            is PermissionRequestResult.Granted -> onCameraPermissionGranted()
            is PermissionRequestResult.Denied -> onCameraPermissionDenied()
        }.exhaustive()
    }

    private fun onCameraPermissionGranted() {
        val preview = Preview.Builder().build()

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val imageAnalyzer = useCases.getImageAnalyzer(::onQrCodeResult)

        viewState.bindCameraPreview(preview, cameraSelector, imageAnalyzer)
    }

    private fun onQrCodeResult(result: String) {
        val toxId = if (result.startsWith("tox:")) result.substring(4) else result

        if (!validateToxId(toxId)) {
            return
        }

        viewState.setToxIdAndClose(toxId)
    }

    private fun onCameraPermissionDenied() {
        viewState.showPermissionDeniedView()
    }

}