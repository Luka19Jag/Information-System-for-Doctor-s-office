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
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import ps.communication.Communication;
import ps.constants.Constant;
import ps.domain.Doktor;
import ps.domain.Pacijent;
import ps.domain.Pregled;
import ps.tableModel.TableModelViewAllExaminations;
import ps.view.cordinator.MainCordinator;
import ps.view.form.FrmViewAllExaminations;
import ps.view.util.FormMode;

/**
 *
 * @author Zbook G3
 */
public class ViewAllExaminationsController {

    private FrmViewAllExaminations frmViewAllExaminations;
    List<Pregled> examinations;
    List<Pregled> requiredExaminations;
    Doktor doctor;
    
    public ViewAllExaminationsController(FrmViewAllExaminations frmViewAllExaminations) {
        this.frmViewAllExaminations = frmViewAllExaminations;

        requiredExaminations = new ArrayList<>();

        if (!MainCordinator.getInstance().getMainContoller().registred) {
            doctor = (Doktor) MainCordinator.getInstance().getParam("CURRENT_USER");
        } else {
            doctor = (Doktor) MainCordinator.getInstance().getParam("REGISTERED_USER");
        }

        try {
            Pregled p = new Pregled();
            p.setDoktor(doctor);
            examinations = Communication.getInstance().getAllExaminations(p);
            //examinations.remove(0);
            fillTblViewAllExaminations();
            //if(examinations == null)
               // System.out.println("Greska bre");
            //else
              //  System.out.println(examinations.size());
            //for(Pregled proba:examinations)
              //  System.out.println(proba.toString());
        } catch (Exception e) {
            //ex.printStackTrace();
//            MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
            JOptionPane.showMessageDialog(frmViewAllExaminations,
                    "Server is down!",
                    "Server down",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        addActionListeners();
    }

    public void openForm(FormMode formMode) {
        frmViewAllExaminations.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView();
        frmViewAllExaminations.setVisible(true);
    }

    private void addActionListeners() {
        frmViewAllExaminations.btnDetailsAddAcionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = frmViewAllExaminations.getTblExaminations().getSelectedRow();
                if (selectedRow >= 0) {
                    Pregled examination = ((TableModelViewAllExaminations) frmViewAllExaminations.getTblExaminations().
                            getModel()).getExaminationAt(selectedRow);
                    MainCordinator.getInstance().addParam(Constant.PARAM_EXAMINATION, examination);
                    MainCordinator.getInstance().openExaminationDetailsForm();
                } else {
                    JOptionPane.showMessageDialog(frmViewAllExaminations,
                            "You must select a examination",
                            "Examination details",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frmViewAllExaminations.btnCancelAddAcionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmViewAllExaminations.dispose();
            }
        });

        frmViewAllExaminations.addWindowListener(new WindowAdapter() {
           @Override
            public void windowActivated(WindowEvent e) {
                fillTblViewAllExaminations();
            }

        });

        frmViewAllExaminations.getTxtSearchValue().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                textChanged();
                TableModelViewAllExaminations ptm = (TableModelViewAllExaminations)
                        frmViewAllExaminations.getTblExaminations().getModel();
                ptm.emptyList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                textChanged();
                TableModelViewAllExaminations ptm = (TableModelViewAllExaminations)
                        frmViewAllExaminations.getTblExaminations().getModel();
                ptm.emptyList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                textChanged();
                TableModelViewAllExaminations ptm = (TableModelViewAllExaminations)
                        frmViewAllExaminations.getTblExaminations().getModel();
                ptm.emptyList();
            }

            
        });
    }
    
    private void textChanged() {
        String searchValue = frmViewAllExaminations.getTxtSearchValue().getText();
        
        //List<Pregled> exams = Communication.getInstance().getAllExaminations(searchValue, new List<Pregled>);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        String dateTermin;
        String startTime;
        String totalPrice, totalDuration;
        Pacijent p = new Pacijent();
        boolean find = false;
        TableModelViewAllExaminations ptm = (TableModelViewAllExaminations)
                frmViewAllExaminations.getTblExaminations().getModel();
        for (Pregled examination : examinations) {

            date = examination.getTermin().getDatumTermina();
            dateTermin = sdf.format(date);

            startTime = examination.getTermin().getVremeOd();
            //theEndTime = examination.getTermin().getVremeDo();
            
            
            if (examination.getPacijent().getIme().startsWith(searchValue) ||
                    examination.getPacijent().getPrezime().startsWith(searchValue)
                    || dateTermin.startsWith(searchValue) || startTime.startsWith(searchValue) || 
                    String.valueOf(examination.getUkupnaCena()).startsWith(searchValue) ||
                    String.valueOf(examination.getUkupnoTrajanje()).startsWith(searchValue)
                    ){
                ptm.updateList(examination);
                find = true;
            }
        }
        if (!find) {
            JOptionPane.showMessageDialog(frmViewAllExaminations,
                    "System has not found the examination!",
                    "Examination error", JOptionPane.ERROR_MESSAGE);
        }
            }

    private void prepareView() {
        frmViewAllExaminations.setTitle("View examinations");
        frmViewAllExaminations.getTxtEmptyValue().setVisible(false);
    }
    
    private void fillTblViewAllExaminations() {
        List<Pregled> listOfExaminations;

        try {
            Pregled p = new Pregled();
            p.setDoktor(doctor);
            listOfExaminations = Communication.getInstance().getAllExaminations(p);
            //listOfExaminations.remove(0);
            
            if (listOfExaminations.isEmpty()) {
                frmViewAllExaminations.getPnViewAllExaminations().setVisible(true);
                frmViewAllExaminations.getTblExaminations().setVisible(false);                
                //emptyTable(frmViewAllMedicalExamination);
            } else {
                //examinations = listOfExaminations;
                TableModelViewAllExaminations tableModelViewAllExaminations = new TableModelViewAllExaminations(listOfExaminations);
                frmViewAllExaminations.getTblExaminations().setModel(tableModelViewAllExaminations);
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(frmViewAllExaminations,
                    "Server is down!",
                    "Server down",
                    JOptionPane.ERROR_MESSAGE);
//            MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
//            JOptionPane.showMessageDialog(frmViewPatients, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
            //System.exit(1);
        } 
        
        //TableModelViewAllExaminations model = new TableModelViewAllExaminations(examinations);
        //frmViewAllExaminations.getTblExaminations().setModel(model);
        
    }
    
}
