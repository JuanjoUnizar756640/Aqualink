package es.unizar.urlshortener.infrastructure.delivery

import es.unizar.urlshortener.core.usecases.GenerateQRCodeUseCase
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Controller responsible for handling QR code generation requests.
 */
@RestController
@RequestMapping("/api/qrcode")
class QRCodeController(
    private val generateQRCodeUseCase: GenerateQRCodeUseCase
) {

    /**
     * Generates a QR code for the given shortened URL.
     * @param shortUrl The shortened URL for which to generate the QR code.
     * @return The QR code image as a byte array.
     */
    @GetMapping("/{shortUrl}")
    fun getQRCode(@PathVariable shortUrl: String): ResponseEntity<ByteArray> {
        // Call the use case to generate the QR code
        val qrCode = generateQRCodeUseCase.generateQRCode("http://short.url/$shortUrl")

        // Return the QR code image as a PNG
        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_PNG
        return ResponseEntity(qrCode, headers, HttpStatus.OK)
    }
}