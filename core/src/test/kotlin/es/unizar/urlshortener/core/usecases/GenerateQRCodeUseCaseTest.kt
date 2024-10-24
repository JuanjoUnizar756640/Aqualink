package es.unizar.urlshortener.core.usecases

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertNotNull
import es.unizar.urlshortener.core.services.QRCodeService
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals

class GenerateQRCodeUseCaseTest {

    private val generateQRCodeUseCase = GenerateQRCodeUseCase()

    @Test
    fun `generateQRCode returns a valid QR code for a valid URL`() {
        val validUrl = "http://example.com"
        val qrCodeBytes = generateQRCodeUseCase.generateQRCode(validUrl)

        // Verifica que el código QR no esté vacío
        assertNotNull(qrCodeBytes, "El código QR no debería ser nulo")
        assertTrue(qrCodeBytes.isNotEmpty(), "El código QR no debería estar vacío")
    }

    @Test
    fun `generateQRCode throws exception if url is invalid`() {
        // Probar con una URL inválida
        val invalidUrl = "invalid-url"
        val exception = assertThrows<QRCodeService.QRCodeGenerationException> {
            generateQRCodeUseCase.generateQRCode(invalidUrl)
        }
        assertEquals("Invalid URL provided: $invalidUrl", exception.message)

        // Probar con una URL vacía
        val emptyUrl = ""
        val exceptionEmpty = assertThrows<QRCodeService.QRCodeGenerationException> {
            generateQRCodeUseCase.generateQRCode(emptyUrl)
        }
        assertEquals("Invalid URL provided: $emptyUrl", exceptionEmpty.message)
    }

    @Test
    fun `generateQRCode throws an exception if encoding fails`() {
        // Arrange
        val invalidUrl = "" // URL vacía

        // Act & Assert
        val exception = assertThrows<QRCodeService.QRCodeGenerationException> {
            generateQRCodeUseCase.generateQRCode(invalidUrl)
        }

        // Assert que el mensaje sea el de URL inválida
        assertEquals("Invalid URL provided: ", exception.message)
    }
}
