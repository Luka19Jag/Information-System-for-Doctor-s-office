/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.operation.termin;

import ps.domain.Termin;
import ps.operation.AbstractGenericOperation;

/**
 *
 * @author Zbook G3
 */
public class AddTermin extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null || !(param instanceof Termin))
            throw new Exception("Data about termin aren't correct!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.add((Termin)param);
    }
    
}
