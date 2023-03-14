/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.operation;

import java.sql.SQLException;
import ps.repository.Repository;
import ps.repository.db.DbRepository;
import ps.repository.db.impl.RepositoryDBGeneric;

/**
 *
 * @author Zbook G3
 */
public abstract class AbstractGenericOperation {
    
    protected final Repository repository;

    public AbstractGenericOperation() {
        this.repository = new RepositoryDBGeneric();
    }
    
    public final void execute(Object param) throws Exception{
        try{
            preconditions(param);
            startTransaction();
            executeOperation(param);
            commitTransaction();
        } catch(Exception ex){
            ex.printStackTrace();
            rollbackTransaction();
            throw ex;
        }
        finally{
            disconnect();
        }
    }

    protected abstract void preconditions(Object param) throws Exception;

    public void startTransaction() throws SQLException{
        ((DbRepository)repository).connect();
    }

    protected abstract void executeOperation(Object param) throws Exception;

    private void commitTransaction() throws SQLException {
        ((DbRepository)repository).commit();
    }

    private void rollbackTransaction() throws SQLException {
        ((DbRepository)repository).rollback();
    }

    private void disconnect() throws SQLException {
        ((DbRepository)repository).disconnect();
    }
    
}
