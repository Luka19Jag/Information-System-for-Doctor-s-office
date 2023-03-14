/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.operation.doctor;

import ps.domain.Doktor;
import ps.operation.AbstractGenericOperation;

/**
 *
 * @author Zbook G3
 */
public class AddDoctor extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null || !(param instanceof Doktor))
            throw new Exception("Data about doctor aren't correct!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.add((Doktor)param);
    }
    
}
