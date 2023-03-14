/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Zbook G3
 */
public interface GenericEntity extends Serializable{
    
    String getTableName();
    
    void setId(Long id);
    
    String getWhereCondition();
    
    String getComplexWhereCondition();
    
    String getComplexClassName();
    
    String getColumnsForInsertName();
    
    String getSelect();

    String getAtrValue();
    
    String setAtrValue();
    
    GenericEntity getNewRecord(ResultSet rs) throws SQLException;
    
    public void setNewRecord(GenericEntity entity);
    
}
