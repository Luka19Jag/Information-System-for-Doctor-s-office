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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zbook G3
 */
public class Pregled implements GenericEntity{
    
    private Long id;
    private double ukupnaCena;
    private double ukupnoTrajanje;
    private Doktor doktor;
    private Pacijent pacijent;
    private Termin termin;
    private List<StavkaPregleda> listaStavkePregleda;

    public Pregled() {
        this.ukupnaCena = 0;
        this.listaStavkePregleda = new ArrayList<>();
    }

    public Pregled(Long id){
        this.id = id;
    }
    
    public Pregled(Long id, double ukupnaCena, double ukupnoTrajanje, Doktor doktor, Pacijent pacijent, Termin termin) {
        this.id = id;
        this.ukupnaCena = ukupnaCena;
        this.ukupnoTrajanje = ukupnoTrajanje;
        this.doktor = doktor;
        this.pacijent = pacijent;
        this.termin = termin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public double getUkupnoTrajanje() {
        return ukupnoTrajanje;
    }

    public void setUkupnoTrajanje(double ukupnoTrajanje) {
        this.ukupnoTrajanje = ukupnoTrajanje;
    }

    public Doktor getDoktor() {
        return doktor;
    }

    public void setDoktor(Doktor doktor) {
        this.doktor = doktor;
    }

    public Pacijent getPacijent() {
        return pacijent;
    }

    public void setPacijent(Pacijent pacijent) {
        this.pacijent = pacijent;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public List<StavkaPregleda> getListaStavkePregleda() {
        return listaStavkePregleda;
    }

    public void setListaStavkePregleda(List<StavkaPregleda> listaStavkePregleda) {
        this.listaStavkePregleda = listaStavkePregleda;
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
        final Pregled other = (Pregled) obj;
        if (Double.doubleToLongBits(this.ukupnaCena) != Double.doubleToLongBits(other.ukupnaCena)) {
            return false;
        }
        if (Double.doubleToLongBits(this.ukupnoTrajanje) != Double.doubleToLongBits(other.ukupnoTrajanje)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.doktor, other.doktor)) {
            return false;
        }
        if (!Objects.equals(this.pacijent, other.pacijent)) {
            return false;
        }
        if (!Objects.equals(this.termin, other.termin)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Pregled je izvrsio: " + this.doktor.getIme() + " " + this.doktor.getPrezime() + "," +
                "\nPregledan je: " + this.pacijent.getIme() + this.pacijent.getPrezime() + "," + 
                "\nUkupno trajanje: " + this.ukupnoTrajanje + "," +
                "\nUkupna cena: " + this.ukupnaCena;
        
    }
    
    @Override
    public String getTableName() {
        return "pregled";
    }

    @Override
    public String getWhereCondition() {
        return "id = " + id;
    }
    
    @Override
    public String getComplexWhereCondition() {
        return "doktor_id = " + doktor.getId();
    }

    @Override
    public String getAtrValue() {
        return ukupnaCena + ", " + ukupnoTrajanje + ", " + doktor.getId() + ", " +
                pacijent.getId() + ", " + "'" + new java.sql.Date(termin.getDatumTermina().getTime()) +
                "'" + ", " + "'" + termin.getVremeOd()+ "'";
    }

    @Override
    public String setAtrValue() {
        return "id=" + id + ", " + "ukupnaCena=" + ukupnaCena + ", " + "ukupnoTrajanje=" + ukupnoTrajanje 
                + ", " + "doktorId=" + doktor.getId() + ", " + "pacijentId=" + pacijent.getId()
                + ", " + "terminDatum=" + "'" + new java.sql.Date(termin.getDatumTermina().getTime()) 
                + "'" +", " + "terminVremeOd=" + "'" +termin.getVremeOd() + "'";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        return new Pregled(rs.getLong("pregled_id"), rs.getDouble("pregled_ukupnaCena"),
                rs.getDouble("pregled_ukupnoTrajanje"),
                new Doktor(rs.getLong("doktor_id"), rs.getString("doktor_ime"), 
                        rs.getString("doktor_prezime"), rs.getString("doktor_email"),
                        rs.getString("doktor_password"), rs.getDate("doktor_datumRodjenja"),
                        rs.getString("doktor_adresa")),
                new Pacijent(rs.getLong("pacijent_id"), rs.getString("pacijent_ime"), 
                        rs.getString("pacijent_prezime"), rs.getString("pacijent_email"),
                        rs.getDate("pacijent_datumRodjenja"), rs.getString("pacijent_adresa")), 
                new Termin(rs.getDate("termin_datum"), rs.getString("termin_vremeOd"),
                        rs.getString("termin_vremeDo")));
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
        return "pregled\n" +
                            "JOIN doktor\n" +
                            "ON pregled.doktorId = doktor.id\n" +
                            "JOIN pacijent\n" +
                            "ON pregled.pacijentId = pacijent.id\n" +
                            "JOIN termin\n" +
                            "ON pregled.terminDatum = termin.datum\n" +
                            "AND pregled.terminVremeOd = termin.vremeOd";
    }

    @Override
    public String getColumnsForInsertName() {
        return "ukupnaCena, ukupnoTrajanje, doktorId, pacijentId, terminDatum, terminVremeOd";
    }

    @Override
    public String getSelect() {
        return "pregled.id AS 'pregled_id', \n" +
                            "pregled.ukupnaCena AS 'pregled_ukupnaCena',\n" +
                            "pregled.ukupnoTrajanje AS 'pregled_ukupnoTrajanje',\n" +
                            "doktor.id AS 'doktor_id'," +
                            "doktor.ime AS 'doktor_ime',\n" +
                            "doktor.prezime AS 'doktor_prezime',\n" +
                            "doktor.email AS 'doktor_email',\n" +
                            "doktor.password AS 'doktor_password',\n" +
                            "doktor.datumRodjenja AS 'doktor_datumRodjenja',\n" +
                            "doktor.adresa AS 'doktor_adresa',\n" +
                            "pacijent.id AS 'pacijent_id',\n" +
                            "pacijent.ime AS 'pacijent_ime',\n" +
                            "pacijent.prezime AS 'pacijent_prezime',\n" +
                            "pacijent.email AS 'pacijent_email',\n" +
                            "pacijent.adresa AS 'pacijent_adresa',\n" +
                            "pacijent.datumRodjenja AS 'pacijent_datumRodjenja',\n" +
                            "termin.datum AS 'termin_datum',\n" +
                            "termin.vremeOd AS 'termin_vremeOd',\n" +
                            "termin.vremeDo AS 'termin_vremeDo'\n";
    }

    @Override
    public void setNewRecord(GenericEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
    
}
