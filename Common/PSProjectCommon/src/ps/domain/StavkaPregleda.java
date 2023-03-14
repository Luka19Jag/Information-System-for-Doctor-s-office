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
public class StavkaPregleda implements GenericEntity{
    
    private Pregled pregled;
    private int rbr;
    private double cena;
    private double trajanje;
    private String napomena;
    private Usluga usluga;
    private Status status; 

    public StavkaPregleda() {
    }

    public StavkaPregleda(Pregled pregled, int rbr, double cena, double trajanje, String napomena,Usluga usluga) {
        this.pregled = pregled;
        this.rbr = rbr;
        this.cena = cena;
        this.trajanje = trajanje;
        this.napomena = napomena;
        this.usluga = usluga;
    }
    
    public Pregled getPregled() {
        return pregled;
    }

    public void setPregled(Pregled pregled) {
        this.pregled = pregled;
    }

    public int getRbr() {
        return rbr;
    }

    public void setRbr(int rbr) {
        this.rbr = rbr;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(double trajanje) {
        this.trajanje = trajanje;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Usluga getUsluga() {
        return usluga;
    }

    public void setUsluga(Usluga usluga) {
        this.usluga = usluga;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        final StavkaPregleda other = (StavkaPregleda) obj;
        if (Double.doubleToLongBits(this.cena) != Double.doubleToLongBits(other.cena)) {
            return false;
        }
        if (Double.doubleToLongBits(this.trajanje) != Double.doubleToLongBits(other.trajanje)) {
            return false;
        }
        if (!Objects.equals(this.napomena, other.napomena)) {
            return false;
        }
        if (!Objects.equals(this.pregled, other.pregled)) {
            return false;
        }
        if (!Objects.equals(this.rbr, other.rbr)) {
            return false;
        }
        if (!Objects.equals(this.usluga, other.usluga)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.usluga.toString();
    }

    @Override
    public String getTableName() {
        return "stavkaPregleda";
    }

    @Override
    public void setId(Long id) {
        pregled.setId(id);
    }

    @Override
    public String getWhereCondition() {
        return "pregledId = " + pregled.getId() + " AND rbr = " + rbr;
    }
    
    @Override
    public String getComplexWhereCondition() {
        return "stavkaPregleda.pregledId = " + pregled.getId();// ili samo ovako pregledId ??
    }

    @Override
    public String getComplexClassName() {
        return "stavkaPregleda\n" + 
                            "JOIN usluga\n" +
                            "ON stavkaPregleda.uslugaId = usluga.id\n";
    }

    @Override
    public String getColumnsForInsertName() {
        return "pregledId, rbr, cena, trajanje, napomena, uslugaId";
    }

    @Override
    public String getSelect() {
        return "stavkaPregleda.pregledId AS 'stavkaPregleda_id', \n" +
                            "stavkaPregleda.rbr AS 'stavkaPregleda_rbr',\n" +
                            "stavkaPregleda.cena AS 'stavkaPregleda_cena',\n" +
                            "stavkaPregleda.trajanje AS 'stavkaPregleda_trajanje'," +
                            "stavkaPregleda.napomena AS 'stavkaPregleda_napomena',\n" +
                            "stavkaPregleda.uslugaId AS 'stavkaPregleda_uslugaId',\n" +
                            "usluga.id AS 'usluga_id',\n" +
                            "usluga.naziv AS 'usluga_naziv',\n" +
                            "usluga.cena AS 'usluga_cena',\n" +
                            "usluga.opis AS 'usluga_opis'\n";
    }

    @Override
    public String getAtrValue() {
        return pregled.getId() + ", " + rbr + ", " + cena
                + ", " + trajanje + ", " + "'" + napomena + "'" + ", " + usluga.getId();
    }

    @Override
    public String setAtrValue() {
        return "pregledId=" + pregled.getId() + ", " + "rbr=" + rbr + ", " + "'" 
                + ", " + "cena=" + cena + ", " + "trajanje=" + trajanje
                + ", " + "napomena=" + "'" + napomena + "'" + ", " + "uslugaId=" + usluga.getId();
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        //double cena = rs.getDouble("cena");
        //long pregledId = rs.getLong("pregledId");
        
        return new StavkaPregleda(new Pregled(rs.getLong("stavkaPregleda_id")), rs.getInt("stavkaPregleda_rbr")
                , rs.getDouble("stavkaPregleda_cena"), rs.getDouble("stavkaPregleda_trajanje"), rs.getString("stavkaPregleda_napomena"), 
                new Usluga(rs.getLong("usluga_id"),
                        rs.getString("usluga_naziv"), rs.getDouble("usluga_cena"),
                        rs.getString("usluga_opis")));
    }

    @Override
    public void setNewRecord(GenericEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
