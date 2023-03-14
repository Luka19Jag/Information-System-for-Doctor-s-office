/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.operation.examinationItem;

import ps.domain.StavkaPregleda;
import ps.operation.AbstractGenericOperation;

/**
 *
 * @author Zbook G3
 */
public class EditItem extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null || !(param instanceof StavkaPregleda))
             throw new Exception("Data about examination item aren't correct!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.edit((StavkaPregleda)param);
    }
    
}
