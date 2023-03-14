/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.communation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author Zbook G3
 */
public class Receiver {
    
    private Socket socket;
    
    public Receiver(Socket socket) {
        this.socket = socket;
    }
    
    public Object receive() throws Exception {
        try{
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return in.readObject();
        }catch(IOException | ClassNotFoundException e){
            //e.printStackTrace();
            throw new Exception("Receiving object error!\n"+e.getMessage());            
        }
        
    }
    
}
