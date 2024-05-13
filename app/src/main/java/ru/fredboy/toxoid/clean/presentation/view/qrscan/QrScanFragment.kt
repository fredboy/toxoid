package ru.fredboy.toxoid.clean.presentation.view.qrscan

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatDialogFragment
import moxy.ktx.moxyPresenter
import ru.fredboy.toxoid.databinding.FragmentQrScanBinding
import ru.fredboy.toxoid.utils.gone
import ru.fredboy.toxoid.utils.setNavigationResult
import ru.fredboy.toxoid.utils.visible
import splitties.permissions.requestPermission
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Provider


@AndroidEntryPoint
class QrScanFragment : MvpAppCompatDialogFragment(), QrScanView {

    private var _binding: FragmentQrScanBinding? = null
    private val binding: FragmentQrScanBinding
        get() = requireNotNull(_binding)

    @Inject
    lateinit var presenterProvider: Provider<QrScanPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrScanBinding.inflate(inflater)

        with(binding) {
            qrScanSettingsButton.setOnClickListener {
                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts(
                    /* scheme = */ "package",
                    /* ssp = */ requireContext().packageName,
                    /* fragment = */ null
                )
                intent.data = uri
                startActivity(intent)
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        requestCameraPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestCameraPermission() {
        lifecycleScope.launchWhenResumed {
            val result = requestPermission(
                fragmentManager = childFragmentManager,
                lifecycle = lifecycle,
                permission = Manifest.permission.CAMERA
            )

            presenter.onCameraPermissionRequestResult(result)
        }
    }

    override fun bindCameraPreview(
        preview: Preview,
        cameraSelector: CameraSelector,
        imageAnalyzer: ImageAnalysis.Analyzer
    ) {
        binding.qrScanPermissionDeniedView.gone()

        preview.setSurfaceProvider(binding.qrScanCameraPreview.surfaceProvider)

        val cameraProvider = ProcessCameraProvider.getInstance(requireContext()).get()
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .apply { setAnalyzer(Executors.newSingleThreadExecutor(), imageAnalyzer) }

        cameraProvider.bindToLifecycle(
            /* lifecycleOwner = */ this as LifecycleOwner,
            /* cameraSelector = */ cameraSelector,
            /* ...useCases = */ imageAnalysis, preview
        )
    }

    override fun showPermissionDeniedView() {
        binding.qrScanPermissionDeniedView.visible()
    }

    override fun setToxIdAndClose(toxId: String) {
        requireActivity().runOnUiThread {
            setNavigationResult(SCAN_RESULT_KEY, toxId)
        }
    }


    companion object {
        const val SCAN_RESULT_KEY = "qr_scan_result_key"
    }

}