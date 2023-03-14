/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.operation.patient;

import java.util.Date;
import ps.domain.Pacijent;
import ps.operation.AbstractGenericOperation;

/**
 *
 * @author Zbook G3
 */
public class EditPatient extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null || !(param instanceof Pacijent))
            throw new Exception("Data about patient aren't correct!");
        if(((Pacijent)param).getDatumRodjenja().after(new Date()))
            throw new Exception("Date can't be in the future");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.edit((Pacijent)param);
    }
    
}
