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

    // @Test
    // fun `should return QR code when valid shortUrl is provided`() {
    //     val shortUrl = "example"
    //     // val expectedQRCode = ByteArray(0) // Simula un QR code vacío
    //     val expectedQRCode = "fakeQRCode".toByteArray() // Simula un QR code más realista

    //     `when`(generateQRCodeUseCase.generateQRCode("http://localhost:8080/$shortUrl"))
    //         .thenReturn(expectedQRCode)

    //     val response: ResponseEntity<ByteArray> = qrCodeController.getQRCode(shortUrl)

    //     assertEquals(HttpStatus.OK, response.statusCode)
    //     assertEquals(expectedQRCode, response.body)
    //     assertEquals("image/png", response.headers.contentType?.toString())
    // }

    // @Test
    // fun `should return 404 if shortUrl does not exist`() {
    //     val shortUrl = "nonexistent"

    //     `when`(generateQRCodeUseCase.generateQRCode("http://localhost:8080/$shortUrl"))
    //         .thenThrow(NoSuchElementException("Short URL not found"))

    //     val response: ResponseEntity<ByteArray> = qrCodeController.getQRCode(shortUrl)

    //     assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    //     assertEquals("Error generando el QR code: Short URL not found", String(response.body ?: ByteArray(0)))
    // }

    // @Test
    // fun `should return 500 if an exception occurs`() {
    //     val shortUrl = "example"

    //     // Configuración del comportamiento simulado: cuando se llama a generateQRCode, se lanza una RuntimeException
    //     `when`(generateQRCodeUseCase.generateQRCode("http://localhost:8080/$shortUrl"))
    //         .thenThrow(RuntimeException("Error generating QR code"))

    //     // Llamada al método que estamos probando
    //     val response: ResponseEntity<ByteArray> = qrCodeController.getQRCode(shortUrl)

    //     // Imprimir el mensaje de error para depuración
    //     println(String(response.body ?: ByteArray(0))) // Para ver el mensaje de error

    //     // Verificaciones de aserción
    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    //     assertEquals("Error generando el QR code: Error generating QR code", 
    //         String(response.body ?: ByteArray(0)))
    // }

    @Test
    fun `should return 400 if shortUrl is malformed`() {
        val malformedUrl = "htp://invalid-url"
        val response = qrCodeController.getQRCode(malformedUrl)

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
