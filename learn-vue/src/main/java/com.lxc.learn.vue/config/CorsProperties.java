//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lxc.learn.vue.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(
        prefix = "cors"
)
public class CorsProperties {
    private boolean enabled;
    private String[] allowedOrigins;
    private String[] allowedMethods;
    private String[] allowedHeaders;
    private String[] exposedHeaders;
    private Boolean allowCredentials;
    private String mappings;
    private Long maxAge;

    public CorsProperties() {
    }

    public String[] getAllowedOrigins() {
        return this.allowedOrigins;
    }

    public void setAllowedOrigins(String[] allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public String[] getAllowedMethods() {
        return this.allowedMethods;
    }

    public void setAllowedMethods(String[] allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public String[] getAllowedHeaders() {
        return this.allowedHeaders;
    }

    public void setAllowedHeaders(String[] allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public String[] getExposedHeaders() {
        return this.exposedHeaders;
    }

    public void setExposedHeaders(String[] exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
    }

    public Boolean getAllowCredentials() {
        return this.allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public Long getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    public String getMappings() {
        return this.mappings;
    }

    public void setMappings(String mappings) {
        this.mappings = mappings;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
