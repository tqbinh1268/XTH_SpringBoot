/*
 * Copyright 2024 Oracle and/or its affiliates
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

/**
 * MySQL JDBC Connector<br>
 * This is generated code. The {@link JdbcConnector#connect()} method is implemented to connect to the
 * MySQL Database using the appropriate JDBC Driver.
 *
 * <p><u>DRIVER LIBRARIES</u></p>
 * To run this class you will need to integrate the MySQL JDBC Driver libraries in your project<br>
 * e.g. by using Maven Project Object Model (POM) <a href="https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.33/mysql-connector-java-8.0.33.pom">mysql-connector-java-8.0.33.pom</a><br>
 * Additional features may require other libraries to be added to the runtime. Please read the MySQL JDBC documentation for additional details.
 *
 * <p><u>JDBC URL</u></p>
 * The connection is using a JDBC URL of type "Custom"<br>
 * URL pattern: "jdbc:mysql://&lt;HOST&gt;:&lt;PORT&gt;/&lt;DATABASE&gt;"<br>
 *
 *
 * <p><u>AUTHENTICATION</u></p>
 * The connection uses "User / Password" authentication
 * User and Password are passed as properties to the driver
 * <ul>
 *   <li>Property "user": the name of the user </li>
 *   <li>Property "password": the password for the account</li>
 * </ul>
 * The connection uses "User / Password" authentication
 */
public class JdbcConnector {
    private static final String PROP_USER = "root";
    private static final String PROP_PASSWORD = "123456";
    private static final String JDBC_DB_PASSWORD = "123456";

    /**
     * Creates a jdbc connection to the MYSQL database
     *
     * @return a new {@link Connection}
     * @throws Exception if something goes wrong
     */
    public Connection connect() throws Exception {
        Properties properties = new Properties();

        // JDBC URL
        // MYSQL jdbc url: jdbc:mysql://<HOST>:<PORT>/<DATABASE>
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/mysql";

        // AUTHENTICATION
        // user-name and password authentication
        String userName = "root";
        String password = System.getProperty(JDBC_DB_PASSWORD);
        properties.put(PROP_USER, userName);
        properties.put(PROP_PASSWORD, password);

        // PROPERTIES
        properties.put("allowPublicKeyRetrieval", "true");
        properties.put("serverTimezone", "UTC");
        properties.put("useSSL", "false");

        // DRIVER
        // MYSQL driver class "com.mysql.cj.jdbc.Driver"
        Class<? extends Driver> driverClass = com.mysql.cj.jdbc.Driver.class;
        Driver driver = driverClass.getConstructor().newInstance();

        // CONNECTION
        return driver.connect(jdbcUrl, properties);
    }

    public static void main(String[] args) {
        // init secret
        readSecret();

        JdbcConnector connector = new JdbcConnector();
        try (Connection connection = connector.connect()) {
            connection.isValid(10);
            System.out.println("INFO: Successfully connected and validated");

        } catch (Exception e) {
            System.out.println("ERROR: Failed to connect. Cause:  " + e.getMessage());
        }
    }

    /**
     * Load secret from system environment
     */
    private static void readSecret() {
        String secret = System.getenv(JDBC_DB_PASSWORD);
        if (secret == null) {
            String errorMessage = "${error_message1}" +
                    "${error_message2}";
            throw new AssertionError(errorMessage);
        }
        System.setProperty(JDBC_DB_PASSWORD, secret);
    }
}