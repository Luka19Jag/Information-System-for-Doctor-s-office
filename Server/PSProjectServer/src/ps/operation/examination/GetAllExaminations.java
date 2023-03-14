/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.operation.examination;

import java.util.List;
import ps.domain.Pregled;
import ps.operation.AbstractGenericOperation;

/**
 *
 * @author Zbook G3
 */
public class GetAllExaminations extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null)
            throw new Exception("Data about examinations aren't correct!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        List<Pregled> examinations = (List<Pregled>) param;
        repository.getAllWithCondition(examinations.get(0), examinations); // TO CHECK
        //repository.getAll(examinations.get(0), examinations);
        
    }
    
}
