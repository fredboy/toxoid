package ru.fredboy.toxoid.clean.domain.usecase.qr

import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.multi.qrcode.QRCodeMultiReader
import java.nio.ByteBuffer
import javax.inject.Inject

class GetQrImageAnalyzerUseCase @Inject constructor() {

    fun execute(onQrDecoded: (String) -> Unit): ImageAnalysis.Analyzer {
        return object : ImageAnalysis.Analyzer {

            override fun analyze(image: ImageProxy) {
                if (image.format != ImageFormat.YUV_420_888 &&
                    image.format != ImageFormat.YUV_422_888 &&
                    image.format != ImageFormat.YUV_444_888
                ) {
                    return
                }

                try {
                    val byteBuffer: ByteBuffer = image.planes[0].buffer
                    val imageData = ByteArray(byteBuffer.capacity())
                    byteBuffer.get(imageData)

                    val source = PlanarYUVLuminanceSource(
                        /* yuvData = */ imageData,
                        /* dataWidth = */ image.width,
                        /* dataHeight = */ image.height,
                        /* left = */ 0,
                        /* top = */ 0,
                        /* width = */ image.width,
                        /* height = */ image.height,
                        /* reverseHorizontal = */ false
                    )

                    val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
                    val result = QRCodeMultiReader().decode(binaryBitmap)
                    onQrDecoded(result.text)
                } catch (_: NotFoundException) {

                } catch (_: ChecksumException) {

                } catch (_: FormatException) {

                } finally {
                    image.close()
                }
            }

        }
    }

}