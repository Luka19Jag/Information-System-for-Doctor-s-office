/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.operation.examinationItem;

import java.util.List;
import ps.domain.StavkaPregleda;
import ps.operation.AbstractGenericOperation;

/**
 *
 * @author Zbook G3
 */
public class GetAllItems extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null)
             throw new Exception("Data about examination items aren't correct!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        List<StavkaPregleda> items = (List<StavkaPregleda>) param;
        repository.getAllWithCondition(items.get(0), items); 
        //repository.getAll(items.get(0), items);// na klijentu proveravam za koji pregled je stavka
    }
    
}
