/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.operation.patient;

import java.util.List;
import ps.domain.Pacijent;
import ps.operation.AbstractGenericOperation;

/**
 *
 * @author Zbook G3
 */
public class GetAllPatients extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null)
            throw new Exception("Data about patients aren't correct!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.getAll(new Pacijent(), (List<Pacijent>) param);
    }
    
}
