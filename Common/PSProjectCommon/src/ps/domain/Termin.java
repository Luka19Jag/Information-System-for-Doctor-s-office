/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zbook G3
 */
public class Termin implements GenericEntity{
    
    private Date datumTermina;
    private String vremeOd;
    private String vremeDo;

    public Termin() {
    }

    public Termin(Date datumTermina, String vremeOd, String vremeDo) {
        this.datumTermina = datumTermina;
        this.vremeOd = vremeOd;
        this.vremeDo = vremeDo;
    }
    
    public Termin(Date datumTermina, String vremeOd) {
        this.datumTermina = datumTermina;
        this.vremeOd = vremeOd;
    }
    
    public Date getDatumTermina() {
        return datumTermina;
    }

    public void setDatumTermina(Date datumTermina) {
        this.datumTermina = datumTermina;
    }

    public String getVremeOd() {
        return vremeOd;
    }

    public void setVremeOd(String vremeOd) {
        this.vremeOd = vremeOd;
    }

    public String getVremeDo() {
        return vremeDo;
    }

    public void setVremeDo(String vremeDo) {
        this.vremeDo = vremeDo;
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
        final Termin other = (Termin) obj;
        if (!Objects.equals(this.vremeOd, other.vremeOd)) {
            return false;
        }
        if (!Objects.equals(this.datumTermina, other.datumTermina)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Datum termina: " + datumTermina+ ","
                + "\nTrajanje: "+ vremeOd + " - " + vremeDo;
    }
    
    @Override
    public String getTableName() {
        return "termin";
    }

    @Override
    public void setId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getWhereCondition() {
        return "datum = " + "'" + new java.sql.Date(convert(datumTermina).getTime()) + "'" + " AND "  +  "vremeOd = " + "'" + vremeOd + "'";
    }
    
    @Override
    public String getComplexWhereCondition() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "termin.datum >= " + "'" + sdf.format(new Date()) + "'";
    }

    @Override
    public String getAtrValue() {
        return "'" + new java.sql.Date(convert(datumTermina).getTime()) + "'" + ", " + "'" + vremeOd +
                "'" + ", " + "'" + vremeDo + "'";
    }

    @Override
    public String setAtrValue() {
        return "datumTermina=" + "'" + new java.sql.Date(convert(datumTermina).getTime()) + "'" +
                ", " + "vremeOd=" + "'" + vremeOd + "'" + ", " + "vremeDo=" + "'" + vremeDo + "'"; 
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        return new Termin(rs.getDate("datum"), rs.getString("vremeOd"), rs.getString("vremeDo"));
    }
    
    private Date convert(Date datum){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(datum);
        
        try {
            return sdf.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
            Logger.getLogger(Doktor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getComplexClassName() {
        return "termin";
    }

    @Override
    public String getColumnsForInsertName() {
        return "datum, vremeOd, vremeDo";
    }

    @Override
    public String getSelect() {
        return "*";
    }

    @Override
    public void setNewRecord(GenericEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
