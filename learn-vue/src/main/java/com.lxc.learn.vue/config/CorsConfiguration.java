//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lxc.learn.vue.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@ConditionalOnProperty(
        prefix = "cors",
        name = {"enabled"},
        havingValue = "true"
)
@Configuration
@EnableConfigurationProperties({CorsProperties.class})
public class CorsConfiguration extends WebMvcConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(CorsConfiguration.class);
    @Autowired
    private CorsProperties corsProperties;
    private String[] DEFAULT_ORIGINS = new String[]{"*"};
    private String[] DEFAULT_ALLOWED_HEADERS = new String[]{"*"};
    private String[] DEFAULT_METHODS = new String[0];
    private boolean DEFAULT_ALLOW_CREDENTIALS = true;
    private long DEFAULT_MAX_AGE = 1800L;

    public CorsConfiguration() {
    }

    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedOrigins = this.corsProperties.getAllowedOrigins();
        String[] allowedHeaders = this.corsProperties.getAllowedHeaders();
        String[] exposedHeaders = this.corsProperties.getExposedHeaders();
        Boolean allowCredentials = this.corsProperties.getAllowCredentials();
        Long maxAge = this.corsProperties.getMaxAge();
        logger.info("registry = [" + allowedOrigins + "]");
        String mappings = this.corsProperties.getMappings();
        if (allowedHeaders == null || allowedHeaders.length == 0) {
            allowedHeaders = this.DEFAULT_ALLOWED_HEADERS;
        }

        if (allowedOrigins == null || allowedOrigins.length == 0) {
            allowedOrigins = this.DEFAULT_ORIGINS;
        }

        if (exposedHeaders == null || exposedHeaders.length == 0) {
            exposedHeaders = this.DEFAULT_METHODS;
        }

        if (maxAge == null || maxAge == 0L) {
            maxAge = this.DEFAULT_MAX_AGE;
        }

        if (allowCredentials == null) {
            allowCredentials = this.DEFAULT_ALLOW_CREDENTIALS;
        }

        if (mappings == null || mappings.trim() == "") {
            mappings = "/**";
        }

        logger.info("mappings is " + mappings);
        registry.addMapping(mappings).allowedOrigins(allowedOrigins).allowedHeaders(allowedHeaders).exposedHeaders(exposedHeaders).allowCredentials(allowCredentials).maxAge(maxAge);
    }
}
