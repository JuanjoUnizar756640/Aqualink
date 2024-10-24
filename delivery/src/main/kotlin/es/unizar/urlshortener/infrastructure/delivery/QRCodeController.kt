package es.unizar.urlshortener.infrastructure.delivery

import es.unizar.urlshortener.core.services.QRCodeService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/api/qrcode")
class QRCodeController(
    private val qrCodeService: QRCodeService // Usamos la inyección de dependencias aquí
) {
    private val logger = LoggerFactory.getLogger(QRCodeController::class.java)

    @GetMapping("/{shortUrl}")
    fun getQRCode(@PathVariable shortUrl: String): ResponseEntity<ByteArray> {
        return try {
            val qrCode = qrCodeService.generateQRCodeForUrl("http://localhost:8080/$shortUrl")
            val headers = HttpHeaders().apply {
                contentType = MediaType.IMAGE_PNG
            }
            ResponseEntity(qrCode, headers, HttpStatus.OK)
        } catch (e: QRCodeService.QRCodeGenerationException) {
            logger.error("Error generating QR code for URL: $shortUrl", e)
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
