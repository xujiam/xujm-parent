package top.xujm.core.plugins.datasource.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Xujm
 */
@ConfigurationProperties(prefix = "spring.datasource.slave")
public class SlaveProperties {

    private String driverClassName = "";

    private String jdbcUrl = "";

    private String username = "";

    private String password = "";

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
