/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.communation;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Zbook G3
 */
public class Sender {
    
    private Socket socket;

    public Sender(Socket socket) {
        this.socket = socket;
    }
    
    public void send(Object object) throws Exception {
        try{
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.writeObject(object);
            out.flush();
        }catch(IOException e){
            //e.printStackTrace();
            throw new Exception("Sending object error!\n"+e.getMessage());
        }
    }
    
}
