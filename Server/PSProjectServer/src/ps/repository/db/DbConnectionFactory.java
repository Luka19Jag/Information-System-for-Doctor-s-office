/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.repository.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import ps.properties.Property;

/**
 *
 * @author Zbook G3
 */
public class DbConnectionFactory implements Property{

    private static DbConnectionFactory instance;
    private Connection connection;
    private String url, username, password;
            

    private DbConnectionFactory() {
    }

    public static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }
    
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            readConfigProperties();
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        }
        return connection;
    }
    
    @Override
    public void readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("dbconfig.properties");
            prop.load(input);
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
