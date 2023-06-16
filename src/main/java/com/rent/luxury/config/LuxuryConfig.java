package com.rent.luxury.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de la aplicación Luxury.
 */
@Configuration
@ComponentScan(basePackages = { "com.rent.luxury" })
public class LuxuryConfig implements WebMvcConfigurer {

    /**
     * Agrega controladores para rutas específicas.
     *
     * @param registry El registro de controladores.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
    }
    
    /**
     * Configura la negociación de contenido.
     *
     * @param configurer El configurador de la negociación de contenido.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .favorParameter(true) // Preferir el tipo de contenido basado en un parámetro
            .parameterName("mediaType") // Nombre del parámetro utilizado para la negociación
            .ignoreAcceptHeader(false) // No ignorar el encabezado "Accept" de la solicitud
            .defaultContentType(MediaType.APPLICATION_JSON) // Tipo de contenido predeterminado: JSON
            .mediaType("xml", MediaType.APPLICATION_XML) // Tipo de contenido para "xml": XML
            .mediaType("json", MediaType.APPLICATION_JSON); // Tipo de contenido para "json": JSON
    }

    /**
     * Configura la gestión de recursos estáticos.
     *
     * @param registry El registro de gestión de recursos.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
            .addResourceLocations("classpath:static/img/");

        registry.addResourceHandler("/images/**")
            .addResourceLocations("classpath:static/images/");

        registry.addResourceHandler("/css/**")
            .addResourceLocations("classpath:static/css/");

        registry.addResourceHandler("/js/**")
            .addResourceLocations("classpath:static/js/");

        registry.addResourceHandler("/static/**")
            .addResourceLocations("classpath:static/");

        registry.addResourceHandler("/resources/**")
            .addResourceLocations("classpath:static/");

        registry.addResourceHandler("/assets/**")
            .addResourceLocations("classpath:static/assets/");
    }
}
