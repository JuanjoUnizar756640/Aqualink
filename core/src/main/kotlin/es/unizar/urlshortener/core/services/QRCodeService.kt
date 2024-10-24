package es.unizar.urlshortener.core.services

import es.unizar.urlshortener.core.usecases.GenerateQRCodeUseCase
import es.unizar.urlshortener.core.QRCodeGenerationException
import com.google.zxing.WriterException // Importar la excepción correcta

class QRCodeService(
    private val generateQRCodeUseCase: GenerateQRCodeUseCase
) {
    fun generateQRCodeForUrl(shortUrl: String): ByteArray {
        return try {
            generateQRCodeUseCase.generateQRCode(shortUrl)
        } catch (e: IllegalArgumentException) {
            throw QRCodeGenerationException("Invalid argument provided for URL: $shortUrl", e)
        } catch (e: WriterException) {
            throw QRCodeGenerationException("Error while generating QR code for URL: $shortUrl", e)
        }
    }

    class QRCodeGenerationException(message: String, cause: Throwable) : RuntimeException(message, cause)
}
