/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.communation;

import java.io.Serializable;

/**
 *
 * @author Zbook G3
 */
public class Request implements Serializable{
    
    private Operation operation;
    private Object argument;

    public Request() {
    }

    public Request(Operation operation, Object argument) {
        this.operation = operation;
        this.argument = argument;
    }

    /**
     * @return the operation
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    /**
     * @return the argument
     */
    public Object getArgument() {
        return argument;
    }

    /**
     * @param argument the argument to set
     */
    public void setArgument(Object argument) {
        this.argument = argument;
    }
    
}
