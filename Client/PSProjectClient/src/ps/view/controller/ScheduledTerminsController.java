/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import ps.communication.Communication;
import ps.domain.Doktor;
import ps.domain.Pregled;
import ps.domain.Termin;
import ps.tableModel.TableModelScheduledTermins;
import ps.view.cordinator.MainCordinator;
import ps.view.form.FrmScheduledTermins;

/**
 *
 * @author Zbook G3
 */
public class ScheduledTerminsController {
    
    private final FrmScheduledTermins frmScheduledTermins;
    List<Termin> terminsMain;
    List<Termin> requiredTermins;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   // List<Termin> allTermins = new ArrayList<>();
    
    
    public ScheduledTerminsController(FrmScheduledTermins frmScheduledTermins) {
        this.frmScheduledTermins = frmScheduledTermins;
        terminsMain = new ArrayList<>();
        requiredTermins = new ArrayList<>();
        
        try {
            //Termin t = new Termin();
            //termins = Communication.getInstance().getAllTermins(t);
            Doktor d;
            if (!MainCordinator.getInstance().getMainContoller().registred){
                 d = (Doktor) MainCordinator.getInstance().getParam("CURRENT_USER");
            }
            else d = (Doktor) MainCordinator.getInstance().getParam("REGISTERED_USER");
            if(d != null)   { 
                
                Pregled p = new Pregled();
                p.setDoktor(d);
                List<Pregled> exams = Communication.getInstance().getAllExaminations(p);
                
                for(Pregled e : exams){
                    terminsMain.add(e.getTermin());
                }
                
                //allTermins = Communication.getInstance().getAllTermins(new Termin());
                
                //for(Termin t : allTermins)
                    //for(Termin t2 : terminsMain)
                        //if(t.equals(t2))
                           // allTermins.remove(t);
                
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(this.frmScheduledTermins,
                    "Server is down!",
                    "Server down",
                    JOptionPane.ERROR_MESSAGE);
            //System.exit(1);
//            MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
//            JOptionPane.showMessageDialog(frmViewPatients, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
        }

        prepareView();
        addActionListener();
    }

    public void openForm() {
        frmScheduledTermins.setVisible(true);        
    }
    
    private void prepareView() {
        frmScheduledTermins.setLocationRelativeTo(null);
        frmScheduledTermins.setTitle("Scheduled termins");
        frmScheduledTermins.setResizable(false);
    }

    private void addActionListener() {
        frmScheduledTermins.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmScheduledTermins.dispose();
            }
        });

        frmScheduledTermins.getTxtSearchValue().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                textChanged();
                TableModelScheduledTermins tmst = (TableModelScheduledTermins)
                        frmScheduledTermins.getTblSheduledTermins().getModel();
                tmst.emptyList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                textChanged();
                TableModelScheduledTermins tmst = (TableModelScheduledTermins)
                        frmScheduledTermins.getTblSheduledTermins().getModel();
                tmst.emptyList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                textChanged();
                TableModelScheduledTermins tmst = (TableModelScheduledTermins)
                        frmScheduledTermins.getTblSheduledTermins().getModel();
                tmst.emptyList();
            }

        });

        frmScheduledTermins.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblScheduledTermins();
                //fillTblSheduledTerminsFromOtherDoctors();
            }

            private void fillTblScheduledTermins() {
                TableModelScheduledTermins tmst = new TableModelScheduledTermins(terminsMain);
                frmScheduledTermins.getTblSheduledTermins().setModel(tmst);
            }

            //private void fillTblSheduledTerminsFromOtherDoctors() {
                //TableModelScheduledTermins model = new TableModelScheduledTermins(allTermins);
                //frmScheduledTermins.getTblSheduledTerminsFromOtherDoctors().setModel(model);
            //}
        });
    }
    
    private void textChanged() {
        String searchValue = frmScheduledTermins.getTxtSearchValue().getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        String dateOfTermin;
        String startTime, theEndTime;
        boolean find = false;
        TableModelScheduledTermins tmst = (TableModelScheduledTermins)
                frmScheduledTermins.getTblSheduledTermins().getModel();
        for (Termin termin : terminsMain) {

            date = termin.getDatumTermina();
            dateOfTermin = sdf.format(date);

            startTime = termin.getVremeOd();
            theEndTime = termin.getVremeDo();

            if (dateOfTermin.startsWith(searchValue) || startTime.startsWith(searchValue) ||
                    theEndTime.startsWith(searchValue)) {
                tmst.updateList(termin);
                find = true;
            }
        }
        if (!find) {
            JOptionPane.showMessageDialog(frmScheduledTermins,
                    "The system has not found the termin!",
                    "Termin information",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }   
    
    
}
