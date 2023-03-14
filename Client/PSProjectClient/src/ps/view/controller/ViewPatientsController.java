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
import ps.constants.Constant;
import ps.domain.Pacijent;
import ps.tableModel.TableModelPatient;
import ps.view.cordinator.MainCordinator;
import ps.view.form.FrmViewPatients;
import ps.view.util.FormMode;

/**
 *
 * @author Zbook G3
 */
public class ViewPatientsController {
    
    private final FrmViewPatients frmViewPatients;
    List<Pacijent> requiredPatients;
    List<Pacijent> patients;
    private AddExaminationController addExaminationController = null;

    public ViewPatientsController(FrmViewPatients frmViewPatients) {
        this.frmViewPatients = frmViewPatients;
        requiredPatients = new ArrayList<>();
        
        try {
            Pacijent p = new Pacijent();
            patients = Communication.getInstance().getAllPatients(p);
        } catch (Exception ex) {
            Logger.getLogger(ViewPatientsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmViewPatients,
                    "Server is down!",
                    "Server down",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        addActionListeners();
        
    }
    
    public ViewPatientsController(FrmViewPatients frmViewPatients, AddExaminationController addExaminationController) {
        this.frmViewPatients = frmViewPatients;
        requiredPatients = new ArrayList<>();
        this.addExaminationController = addExaminationController;
        
        try {
            Pacijent p = new Pacijent();
            patients = Communication.getInstance().getAllPatients(p);
        } catch (Exception ex) {
            Logger.getLogger(ViewPatientsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmViewPatients,
                    "Server is down!",
                    "Server down",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        addActionListeners();
        
    }
    
    public void openForm() {
        frmViewPatients.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView();
        frmViewPatients.setVisible(true);
    }
    
    public void openF(FormMode formMode) {
        
        switch (formMode) {
            case FORM_ADD:
                formAdd();
                break;
            case FORM_VIEW:
                formView();
                break;
        }
        frmViewPatients.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView();
        frmViewPatients.setVisible(true);
    }

     private void prepareView() {
        frmViewPatients.setTitle("View patients");
     }

    private void addActionListeners() {
        
        frmViewPatients.btnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewPatients.getTblPatients().getSelectedRow();
                if (row >= 0) {
                    Pacijent patient = ((TableModelPatient) frmViewPatients.getTblPatients().getModel()).getPatientAt(row);
                    MainCordinator.getInstance().addParam(Constant.PARAM_PATIENT, patient);
                    MainCordinator.getInstance().openPatientDetailsPatientForm();
                } else {
                    JOptionPane.showMessageDialog(frmViewPatients,
                            "You must select a patient",
                            "Patient details", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });
        
        frmViewPatients.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExaminationController = null;
                frmViewPatients.dispose();
            }
        });
        
        frmViewPatients.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblPatients();
            }

        });
        
        frmViewPatients.getTxtValue().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                findPatient();
                TableModelPatient tmp = (TableModelPatient)frmViewPatients.getTblPatients().getModel();
                tmp.emptyList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                findPatient();
                TableModelPatient tmp = (TableModelPatient)frmViewPatients.getTblPatients().getModel();
                tmp.emptyList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                findPatient();
                TableModelPatient tmp = (TableModelPatient)frmViewPatients.getTblPatients().getModel();
                tmp.emptyList();
            }
        });     
        
        frmViewPatients.btnChooseAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int row = frmViewPatients.getTblPatients().getSelectedRow();
                if (row >= 0) {
                    Pacijent patient = ((TableModelPatient) frmViewPatients.getTblPatients().getModel()).getPatientAt(row);
                    MainCordinator.getInstance().addParam(Constant.PARAM_PATIENT, patient);
                    addExaminationController.patientMain = patient;
                    addExaminationController.frmAddExamination.getTxtPatient().setText(patient.toString());
                    addExaminationController = null;
                    frmViewPatients.dispose();
                } else {
                    JOptionPane.showMessageDialog(frmViewPatients,
                            "You must select a patient",
                            "Choose patient", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
    }
    
    private void findPatient(){
        String value = frmViewPatients.getTxtValue().getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        String dateOfBirth;
        TableModelPatient ptm = (TableModelPatient)frmViewPatients.getTblPatients().getModel();
        boolean found = false;
        for (Pacijent patient : patients) {
                    
            date = patient.getDatumRodjenja();
            dateOfBirth = sdf.format(date);
                    
            if(patient.getIme().startsWith(value) || patient.getPrezime().startsWith(value)
                        || patient.getEmail().startsWith(value) || patient.getAdresa().startsWith(value) 
                        || dateOfBirth.contains(value)){
                ptm.updateList(patient);
                found = true;
            }
        } 
        if(!found)
            JOptionPane.showMessageDialog(frmViewPatients,
                    "The system has not found the patients!",
                    "Patient information",
                    JOptionPane.INFORMATION_MESSAGE);
    }

    private void fillTblPatients() {
         List<Pacijent> patients;
         Pacijent p = new Pacijent();
         try {
             patients = Communication.getInstance().getAllPatients(p);
             TableModelPatient tmp = new TableModelPatient(patients);
             frmViewPatients.getTblPatients().setModel(tmp);
         } catch (Exception ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(frmViewPatients,
                    "Server is down!",
                    "Server down",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
//            MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
//            JOptionPane.showMessageDialog(frmViewPatients, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
        }
    }

    private void formAdd() {
        //frmViewPatients.getBtnDetails().setVisible(false);
        frmViewPatients.getBtnChoose().setVisible(true);
    }

    private void formView() {
        //frmViewPatients.getBtnDetails().setVisible(true);
        frmViewPatients.getBtnChoose().setVisible(false);
    }
    
}
