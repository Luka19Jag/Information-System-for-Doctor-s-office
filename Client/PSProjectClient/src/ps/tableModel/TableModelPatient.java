/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.tableModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import ps.domain.Pacijent;

/**
 *
 * @author Zbook G3
 */
public class TableModelPatient extends AbstractTableModel{
    
    private List<Pacijent> patients;
    private List<Pacijent> requiredPatients;
    private String[] columnNames = new String[] {"Name", "Lastname", "Email", "Address", "Date of birth"};
    private Class[] columnClasses = new Class[] {String.class, String.class, String.class, String.class, Date.class};

    public TableModelPatient(List<Pacijent> patients) {
        this.patients = patients;
        requiredPatients = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        if(patients == null)
            return 0;
        return patients.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pacijent patient = patients.get(rowIndex);
        switch(columnIndex){
            case 0: 
                return patient.getIme();
            case 1: 
                return patient.getPrezime();
            case 2: 
                return patient.getEmail();
            case 3: 
                return patient.getAdresa();
            case 4: 
                return patient.getDatumRodjenja();
            default:
                return "N/A";
            
        }
    }

    @Override
    public String getColumnName(int column) {
         if(column>columnNames.length)
            return "N/A";
        return columnNames[column];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex>columnClasses.length)
            return Object.class;
        return columnClasses[columnIndex];
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Pacijent patient = patients.get(rowIndex);
        switch(columnIndex) {
            case 0:
                patient.setIme(value.toString());
                break;
            case 1:
                patient.setPrezime(value.toString());
                break;
            case 2:
                patient.setEmail(value.toString());
                break;
            case 3:
                patient.setAdresa(value.toString());
                break;
            case 4:
                patient.setDatumRodjenja((Date)value);
        }
    }    
    
    public void addPatient(Pacijent patient){
        this.patients.add(patient);
        //fireTableDataChanged();
        
        fireTableRowsInserted(patients.size()-1, patients.size()-1);
    }
    
    public Pacijent deletePatient(int row){
        Pacijent deletedPatient = this.patients.remove(row);
        this.fireTableRowsInserted(patients.size()-1, patients.size()-1);
        return deletedPatient;
    }
    
    public Pacijent getPatientAt(int rowIndex){
        return patients.get(rowIndex);
    }

    public void refreshPatients() {
        fireTableDataChanged();
    } 
    
    public void emptyList(){
        requiredPatients = new ArrayList<>();
    }
    
    public void updateList(Pacijent patient){
        if(!requiredPatients.contains(patient))
            requiredPatients.add(patient);
        
        this.patients = requiredPatients;
        fireTableDataChanged();
    }
    
}
