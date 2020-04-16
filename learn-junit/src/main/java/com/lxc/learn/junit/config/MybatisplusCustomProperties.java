package com.lxc.learn.junit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * @author lixianchun
 * @description
 * @date 2020/4/16
 */


@ConfigurationProperties(
        prefix = "mybatisplus"
)
public class MybatisplusCustomProperties {
    private String dialectType;
    private boolean jdbcTypeForNull = false;

    public MybatisplusCustomProperties() {
    }

    public void setDialectType(String dialectType) {
        this.dialectType = dialectType;
    }

    public void setJdbcTypeForNull(boolean jdbcTypeForNull) {
        this.jdbcTypeForNull = jdbcTypeForNull;
    }

    public String getDialectType() {
        return this.dialectType;
    }

    public boolean isJdbcTypeForNull() {
        return this.jdbcTypeForNull;
    }
}
