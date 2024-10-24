package es.unizar.urlshortener.infrastructure.delivery

import es.unizar.urlshortener.core.services.QRCodeService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class QRCodeControllerTest {

    private val qrCodeService = mock<QRCodeService>() // Mock del servicio
    private val qrCodeController = QRCodeController(qrCodeService)

    @Test
    fun `getQRCode returns QR code image for valid short URL`() {
        // Arrange
        val shortUrl = "validShortUrl"
        val expectedQRCode = ByteArray(1) // Simulando un QR Code generado
        whenever(qrCodeService.generateQRCodeForUrl("http://localhost:8080/$shortUrl"))
            .thenReturn(expectedQRCode)

        // Act
        val response: ResponseEntity<ByteArray> = qrCodeController.getQRCode(shortUrl)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedQRCode, response.body)
        assertEquals("image/png", response.headers.contentType?.toString())
    }

    @Test
    fun `getQRCode returns INTERNAL_SERVER_ERROR when QR code generation fails`() {
        // Arrange
        val shortUrl = "invalidShortUrl"
        whenever(qrCodeService.generateQRCodeForUrl("http://localhost:8080/$shortUrl"))
        .thenThrow(QRCodeService.QRCodeGenerationException("QR code generation failed", RuntimeException()))

        // Act
        val response: ResponseEntity<ByteArray> = qrCodeController.getQRCode(shortUrl)

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    }
}
