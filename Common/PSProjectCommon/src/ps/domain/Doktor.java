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
public class Doktor implements GenericEntity{
    
    private Long id;
    private String ime;
    private String prezime;
    private String email;
    private String password;
    private Date datumRodjenja;
    private String adresa;
    
    private String status = "Logged out";
    private String check_in_Time ="";
    private String check_out_Time="";
    private String ipAddress="";

    public Doktor() {
    }

    public Doktor(Long id, String ime, String prezime, String email, String password, Date datumRodjenja, String adresa) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.password = password;
        this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
    }
    
    public Doktor(String ime, String prezime, String email, String password, Date datumRodjenja, String adresa) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.password = password;
        this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
    }

    public Doktor(Long id){
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public String getCheck_in_Time() {
        return check_in_Time;
    }

    public String getCheck_out_Time() {
        return check_out_Time;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCheck_in_Time(String check_in_Time) {
        this.check_in_Time = check_in_Time;
    }

    public void setCheck_out_Time(String check_out_Time) {
        this.check_out_Time = check_out_Time;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public Long getId() {
        return id;
    }

        public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
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
        final Doktor other = (Doktor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
    
    @Override
    public String getTableName() {
        return "doktor";
    }

    @Override
    public String getWhereCondition() {
        return "id = " + id;
    }

    @Override
    public String getComplexWhereCondition() {
        return "email ='" + email + "' AND password='" + password + "'";
    }
    
    @Override
    public String getAtrValue() {
        return "'" + ime + "'" + ", " + "'" + prezime + "'" + ", " + "'" + email + "'" + ", " 
                + "'" + password + "'" + ", " + "'" + new java.sql.Date(convert(datumRodjenja).getTime()) +
                "'" + ", " + "'" + adresa + "'";
    }

    @Override
    public String setAtrValue() {
        return "ime=" + "'" + ime + "'" + ", " + "prezime=" + "'" + prezime + "'" + ", " + "email=" 
                + "'" + email + "'" + ", " + "password=" + "'" + password + "'" + ", " + "adresa=" +
                "'" + adresa + "'"+ ", " + "datumRodjenja=" + "'" +
                new java.sql.Date(convert(datumRodjenja).getTime()) + "'";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        return new Doktor(rs.getLong("id"), rs.getString("ime"), rs.getString("prezime")
                , rs.getString("email"), rs.getString("password"), rs.getDate("datumRodjenja"),
                rs.getString("adresa"));
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
        return "doktor";
    }

    @Override
    public String getColumnsForInsertName() {
        return "ime, prezime, email, password, datumRodjenja, adresa";
    }

    @Override
    public String getSelect() {
        return "*";
    }

    @Override
    public void setNewRecord(GenericEntity entity) {
        Doktor tmp = (Doktor)entity;
        this.id = tmp.getId();
        this.ime = tmp.getIme();
        this.prezime = tmp.getPrezime();
        this.email = tmp.getEmail();
        this.adresa = tmp.getAdresa();
        this.datumRodjenja = tmp.getDatumRodjenja();
    }
}
