package es.unizar.urlshortener.infrastructure.delivery

import es.unizar.urlshortener.core.qrcode.QRCodeService
import es.unizar.urlshortener.core.qrcode.QRCodeServiceImpl // Asegúrate de tener esta implementación
import es.unizar.urlshortener.core.usecases.GenerateQRCodeUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    
    @Bean
    fun qrCodeService(): QRCodeService {
        return QRCodeServiceImpl() // Asegúrate de tener esta implementación
    }

    @Bean
    fun generateQRCodeUseCase(qrCodeService: QRCodeService): GenerateQRCodeUseCase {
        return GenerateQRCodeUseCase(qrCodeService)
    }
}