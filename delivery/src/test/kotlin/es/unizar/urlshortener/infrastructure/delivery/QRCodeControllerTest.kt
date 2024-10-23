package es.unizar.urlshortener.infrastructure.delivery

import es.unizar.urlshortener.core.*
import es.unizar.urlshortener.core.usecases.GenerateQRCodeUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class QRCodeControllerTest {

    private val generateQRCodeUseCase: GenerateQRCodeUseCase = mock(GenerateQRCodeUseCase::class.java)
    private val qrCodeController = QRCodeController(generateQRCodeUseCase)

    @Test
    fun `should return 400 if shortUrl is blank`() {
        val response = qrCodeController.getQRCode("")

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun `should return 400 if shortUrl contains unsafe characters`() {
        val unsafeUrl = "example.com/<>"
        val response = qrCodeController.getQRCode(unsafeUrl)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun `should return 400 if shortUrl is too long`() {
        val longUrl = "http://localhost:8080/${"a".repeat(2048)}" // Ejemplo de URL muy larga
        val response = qrCodeController.getQRCode(longUrl)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }
}
