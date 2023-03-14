/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.tableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import ps.domain.Termin;

/**
 *
 * @author Zbook G3
 */
public class TableModelScheduledTermins extends AbstractTableModel{
    
    private List<Termin> termins;
    private List<Termin> requiredTermins = new ArrayList<>();
    
    String[] columnNames = new String[]{"Date of termin", "Start time", "The end time"};
    Class[] classNames = new Class[]{String.class, String.class, String.class};

    public TableModelScheduledTermins(List<Termin> termins) {
        this.termins = termins;
    }

    @Override
    public int getRowCount() {
        return termins.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Termin termin = termins.get(rowIndex);
        
        switch(columnIndex){
            case 0: return sdf.format(termin.getDatumTermina());
            case 1: return termin.getVremeOd();
            case 2: return termin.getVremeDo();
            default: return "N/A";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classNames[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public Termin getMedicalExaminationAt(int rowIndex){
        return termins.get(rowIndex);
    }
    
    public void emptyList(){
        requiredTermins = new ArrayList<>();
    }
    
    public void updateList(Termin termin){
        
        if(!requiredTermins.contains(termin)){
            
            requiredTermins.add(termin);
        }
        
        this.termins = requiredTermins;
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
}
