/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.tableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import ps.domain.Pacijent;
import ps.domain.Pregled;
import ps.domain.Termin;

/**
 *
 * @author Zbook G3
 */
public class TableModelViewAllExaminations extends AbstractTableModel{
    
    private List<Pregled> examinations;
    private List<Pregled> requiredExaminations = new ArrayList<>();
    
    String[] columnNames = new String[]{"Total price", "Total duration", "Patient", "Date", "Start time"};
    Class[] classNames = new Class[]{Double.class, Double.class, Pacijent.class, String.class, String.class};

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    
    public TableModelViewAllExaminations(List<Pregled> examinations) {
        this.examinations = examinations;
    }
    
    @Override
    public int getRowCount() {
        return examinations.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pregled examination = examinations.get(rowIndex);
        
        
        
        switch(columnIndex){
            case 0: return examination.getUkupnaCena();
            case 1: return examination.getUkupnoTrajanje();
            case 2: return examination.getPacijent();
            case 3: Termin t = examination.getTermin();
                    Date date = t.getDatumTermina();
                return sdf.format(date);
            case 4: return examination.getTermin().getVremeOd();
            default: return "N/A";
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classNames[columnIndex];
    } 
    
    public Pregled getExaminationAt(int rowIndex){
        return examinations.get(rowIndex);
    }
    
    public void emptyList(){
        requiredExaminations = new ArrayList<>();
    }
    
    public void updateList(Pregled examination){
        
        if(!requiredExaminations.contains(examination)){
            
            requiredExaminations.add(examination);
        }
        
        this.examinations = requiredExaminations;
        fireTableDataChanged();
    }
    
}
