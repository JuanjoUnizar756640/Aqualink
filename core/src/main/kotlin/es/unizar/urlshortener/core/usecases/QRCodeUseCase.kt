package es.unizar.urlshortener.core.usecases

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.nio.charset.StandardCharsets
import java.util.*
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream
import java.awt.image.BufferedImage

/**
 * This use case generates a QR code for the provided URL.
 */
class GenerateQRCodeUseCase {

    /**
     * Generates a QR code from the given URL.
     * @param url The URL to encode in the QR code.
     * @return The QR code as a byte array.
     */
    fun generateQRCode(url: String): ByteArray {
        val qrCodeWriter = QRCodeWriter()
        val hintMap = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        hintMap[EncodeHintType.CHARACTER_SET] = StandardCharsets.UTF_8.name()

        val bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 250, 250, hintMap)
        val image = BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB)

        for (x in 0 until 250) {
            for (y in 0 until 250) {
                image.setRGB(x, y, if (bitMatrix.get(x, y)) 0x000000 else 0xFFFFFF)
            }
        }

        val outputStream = ByteArrayOutputStream()
        ImageIO.write(image, "png", outputStream)
        return outputStream.toByteArray()
    }
}