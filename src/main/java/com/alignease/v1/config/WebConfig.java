package com.alignease.v1.config;

import com.alignease.v1.service.impl.AuthServiceImpl;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String productUploadDir;

    @Value("${file.upload-dir.services}")
    private String serviceUploadDir;

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(productUploadDir));
            Files.createDirectories(Paths.get(serviceUploadDir));
            logger.info("Created upload directories: {} and {}", productUploadDir, serviceUploadDir);
        } catch (IOException e) {
            logger.error("Failed to create upload directories", e);
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/product-images/**")
                .addResourceLocations("file:" + productUploadDir + "/")
                .setCachePeriod(3600)
                .resourceChain(true);

        registry.addResourceHandler("/service-images/**")
                .addResourceLocations("file:" + serviceUploadDir + "/")
                .setCachePeriod(3600)
                .resourceChain(true);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}