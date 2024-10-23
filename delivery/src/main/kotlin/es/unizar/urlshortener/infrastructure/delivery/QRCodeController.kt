package es.unizar.urlshortener.infrastructure.delivery

import es.unizar.urlshortener.core.usecases.GenerateQRCodeUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.NoSuchElementException

@RestController
class QRCodeController(private val generateQRCodeUseCase: GenerateQRCodeUseCase) {

    @GetMapping("/qr-code/{shortUrl}")
    fun getQRCode(@PathVariable shortUrl: String): ResponseEntity<ByteArray> {
        if (shortUrl.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

        val urlPattern = "^(http|https)://.*$"
        if (!shortUrl.matches(Regex(urlPattern))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

        if (shortUrl.contains("<") || shortUrl.contains(">")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

        if (shortUrl.length > 2048) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

        return try {
            val qrCode = generateQRCodeUseCase.generateQRCode("http://localhost:8080/$shortUrl")
            ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCode)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error generando el QR code: Short URL not found".toByteArray())
        } catch (e: Exception) {
            // Captura cualquier otra excepción y devuelve un error 500
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error generando el QR code: ${e.message}".toByteArray())
        }
    }
}
