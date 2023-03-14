/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


/**
 *
 * @author Zbook G3
 */
public class Usluga implements GenericEntity{

    private Long id;
    private String naziv;
    private double cena;
    private String opis;

    public Usluga() {
    }

    public Usluga(Long id, String naziv, double cena, String opis) {
        this.id = id;
        this.naziv = naziv;
        this.cena = cena;
        this.opis = opis;
    }
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usluga other = (Usluga) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.naziv;
    }    

    @Override
    public String getTableName() {
        return "usluga";
    }

    @Override
    public String getWhereCondition() {
        return "id= " + id;
    }
    
    @Override
    public String getComplexWhereCondition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getComplexClassName() {
        return "usluga";
    }

    @Override
    public String getColumnsForInsertName() {
        return "naziv, cena, opis";
    }

    @Override
    public String getSelect() {
        return "*";
    }

    @Override
    public String getAtrValue() {
        return "'" + naziv + "'" + ", " + "'" + cena + "'" + ", " + "'" + opis + "'";
    }

    @Override
    public String setAtrValue() {
        return "naziv=" + "'" + naziv + "'" + ", " + "cena=" + "'" + cena + "'" + ", " + "opis=" 
                + "'" + opis + "'";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        return new Usluga(rs.getLong("id"), rs.getString("naziv"), rs.getDouble("cena"),
                rs.getString("opis"));
    }

    @Override
    public void setNewRecord(GenericEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
