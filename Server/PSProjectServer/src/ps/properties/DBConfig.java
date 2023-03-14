/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Zbook G3
 */
public class DBConfig {
    
    public void config(String url, String username, String password){
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("dbconfig.properties");

            // set the properties value
            //Login form
            prop.setProperty("url", url);
            prop.setProperty("username", username);
            prop.setProperty("password", password);

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {

        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {

                }
            }

        }
    }
    
}
