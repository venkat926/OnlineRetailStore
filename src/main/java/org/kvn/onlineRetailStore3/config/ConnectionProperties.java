package org.kvn.onlineRetailStore3.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class ConnectionProperties {
    String mySqlDbUrl;
    String userName;

    public String getMySqlDbUrl() { return mySqlDbUrl; }
    public void setMySqlDbUrl(String MYSQL_DB_URL) { this.mySqlDbUrl = MYSQL_DB_URL; }

    public String getUserName() { return userName; }
    public void setUserName(String USER_NAME) { this.userName = USER_NAME; }
}
