/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.operation.termin;

import java.util.List;
import ps.domain.Termin;
import ps.operation.AbstractGenericOperation;

/**
 *
 * @author Zbook G3
 */
public class GetAllTermins extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null)
            throw new Exception("Data about termins aren't correct!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.getAllWithCondition(new Termin(), (List<Termin>) param);
        //repository.getAll(new Termin(), (List<Termin>) param);
    }
    
}
