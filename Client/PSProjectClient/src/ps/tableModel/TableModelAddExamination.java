/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.tableModel;

import javax.swing.table.AbstractTableModel;
import ps.domain.Pregled;
import ps.domain.StavkaPregleda;
import ps.domain.Usluga;

/**
 *
 * @author Zbook G3
 */
public class TableModelAddExamination extends AbstractTableModel{

    private final Pregled examination;
    private final String[] columnNames = new String[] {"Serial number", "Price",
            "Duration", "Note", "Name of service"};

    public TableModelAddExamination(Pregled examination) {
        this.examination = examination;
    }
    
    public Pregled getExamination(){
        return examination;
    }
    
    @Override
    public int getRowCount() {
        if(examination != null)
            return examination.getListaStavkePregleda().size();
        return 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaPregleda item = examination.getListaStavkePregleda().get(rowIndex);
        switch(columnIndex){
            case 0: return item.getRbr();
            case 1: return item.getCena();
            case 2: return item.getTrajanje();
            case 3: return item.getNapomena();
            case 4: return item.getUsluga().getNaziv();
            default: return "N/A";
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public String getItemDescription(int rowIndex, int columnIndex){
        switch(columnIndex){
            case 0: return String.valueOf(examination.getListaStavkePregleda().get(rowIndex).getRbr());
            case 1: return String.valueOf(examination.getListaStavkePregleda().get(rowIndex).getCena());
            case 2: return String.valueOf(examination.getListaStavkePregleda().get(rowIndex).getTrajanje());
            case 3: return examination.getListaStavkePregleda().get(rowIndex).getNapomena();
            case 4: return examination.getListaStavkePregleda().get(rowIndex).getUsluga().getNaziv(); 
            default: return "N/A";
        }
        
    }
    
    public void addExaminationItem(Usluga service, double price, double duration, String note){
        StavkaPregleda item = new StavkaPregleda();
        item.setPregled(examination);
        item.setRbr(examination.getListaStavkePregleda().size() + 1);
        item.setUsluga(service);
        item.setCena(price);
        item.setTrajanje(duration);
        item.setNapomena(note);
        examination.getListaStavkePregleda().add(item);
        examination.setUkupnaCena(examination.getUkupnaCena() + item.getCena());
        examination.setUkupnoTrajanje(examination.getUkupnoTrajanje() + item.getTrajanje());
        fireTableRowsInserted(examination.getListaStavkePregleda().size() - 1, examination.getListaStavkePregleda().size() - 1);
    }
    
    public void removeExaminationItem(int rowIndex) {
        StavkaPregleda item = examination.getListaStavkePregleda().get(rowIndex);
        examination.getListaStavkePregleda().remove(rowIndex);
        examination.setUkupnaCena(examination.getUkupnaCena() - item.getCena());
        examination.setUkupnoTrajanje(examination.getUkupnoTrajanje() - item.getTrajanje());
        setOrderNumbers();
        fireTableRowsInserted(examination.getListaStavkePregleda().size() - 1,
                examination.getListaStavkePregleda().size() - 1);
    }
    
    private void setOrderNumbers() {
        int orderNo = 0;
        for (StavkaPregleda stavka : examination.getListaStavkePregleda()) {
            stavka.setRbr(++orderNo);
        }
    }
    
}
