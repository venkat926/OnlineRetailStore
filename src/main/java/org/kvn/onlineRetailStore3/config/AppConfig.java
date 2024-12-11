package org.kvn.onlineRetailStore3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.kvn.onlineRetailStore3.constants.Constants.*;

@Configuration
public class AppConfig {

    @Autowired
    private ConnectionProperties connectionProperties;
//    @Value("${app.MYSQL_DB_URL}")
//    String mysqlDbUrl;
//    @Value("${app.USER_NAME}")
//    String userName;

    @Bean
    public Connection connection() {
        try {
            return DriverManager.getConnection(connectionProperties.getMySqlDbUrl(),
                    connectionProperties.getUserName(), PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(DRIVER_NAME);
        dataSourceBuilder.url(connectionProperties.getMySqlDbUrl());
        dataSourceBuilder.username(connectionProperties.getUserName());
        dataSourceBuilder.password(PASSWORD);
        return dataSourceBuilder.build();
    }
}
