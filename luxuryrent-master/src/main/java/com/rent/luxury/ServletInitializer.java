package com.rent.luxury;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Clase que configura el inicializador del servlet para la aplicación Luxury Rent.
 * Esta clase extiende SpringBootServletInitializer y se utiliza para la implementación de despliegue en un contenedor de servlets.
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Configura la aplicación Spring Boot para el despliegue en un contenedor de servlets.
     *
     * @param application El objeto SpringApplicationBuilder que configura la aplicación.
     * @return El objeto SpringApplicationBuilder configurado con la clase LuxuryrentApplication.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LuxuryrentApplication.class);
    }

}

