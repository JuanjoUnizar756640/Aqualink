package es.unizar.urlshortener.infrastructure.delivery

import es.unizar.urlshortener.core.services.QRCodeService
import es.unizar.urlshortener.core.usecases.GenerateQRCodeUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun generateQRCodeUseCase(): GenerateQRCodeUseCase {
        return GenerateQRCodeUseCase()
    }

    @Bean
    fun qrCodeService(generateQRCodeUseCase: GenerateQRCodeUseCase): QRCodeService {
        return QRCodeService(generateQRCodeUseCase)
    }
}
