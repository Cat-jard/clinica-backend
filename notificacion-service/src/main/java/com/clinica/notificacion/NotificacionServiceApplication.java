package com.clinica.notificacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
@EnableConfigurationProperties(com.clinica.notificacion.config.NotificationProperties.class)
public class NotificacionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificacionServiceApplication.class, args);
    }
}
