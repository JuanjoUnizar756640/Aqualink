package es.unizar.urlshortener.core.usecases

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.nio.charset.StandardCharsets
import java.util.*
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream
import java.awt.image.BufferedImage
import java.io.IOException

/**
 * This use case generates a QR code for the provided URL.
 */
class GenerateQRCodeUseCase {

    // Constants
    private companion object {
        private const val QR_CODE_SIZE = 250
        private const val BLACK_COLOR = 0x000000
        private const val WHITE_COLOR = 0xFFFFFF
    }

    /**
     * Generates a QR code from the given URL.
     * @param url The URL to encode in the QR code.
     * @return The QR code as a byte array.
     */
    fun generateQRCode(url: String): ByteArray {
        try {
            println();
            val qrCodeWriter = QRCodeWriter()
            val hintMap = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java).apply {
                this[EncodeHintType.CHARACTER_SET] = StandardCharsets.UTF_8.name()
            }

            val bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE, hintMap)
            val image = BufferedImage(QR_CODE_SIZE, QR_CODE_SIZE, BufferedImage.TYPE_INT_RGB)

            for (x in 0 until QR_CODE_SIZE) {
                for (y in 0 until QR_CODE_SIZE) {
                    image.setRGB(x, y, if (bitMatrix.get(x, y)) BLACK_COLOR else WHITE_COLOR)
                }
            }

            val outputStream = ByteArrayOutputStream()
            ImageIO.write(image, "png", outputStream)
            return outputStream.toByteArray()
        } catch (e: WriterException) {
            throw IllegalArgumentException("Error generating QR code: ${e.message}", e)
        } 
        catch (e: IOException) {
            throw RuntimeException("I/O error generating QR code: ${e.message}", e)
        }
    }
}
