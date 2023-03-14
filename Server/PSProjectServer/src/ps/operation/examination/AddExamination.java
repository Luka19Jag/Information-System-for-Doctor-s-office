/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.operation.examination;

import ps.domain.Pregled;
import ps.domain.StavkaPregleda;
import ps.operation.AbstractGenericOperation;

/**
 *
 * @author Zbook G3
 */
public class AddExamination extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null || !(param instanceof Pregled))
            throw new Exception("Data about examination aren't correct!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.add((Pregled)param);
        for (StavkaPregleda item : ((Pregled)param).getListaStavkePregleda()) {
            repository.add(item);
        }
        //repository.add(((Pregled)param).getTermin());
    }
    
}
