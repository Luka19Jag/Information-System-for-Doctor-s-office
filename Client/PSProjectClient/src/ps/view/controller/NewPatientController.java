/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.view.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import ps.communication.Communication;
import ps.domain.Doktor;
import ps.domain.Pacijent;
import ps.domain.Pregled;
import ps.view.cordinator.MainCordinator;
import ps.view.form.FrmNewPatient;
import ps.view.util.FormMode;
import ps.view.validation.Validate;

/**
 *
 * @author Zbook G3
 */
public class NewPatientController {
    
    private final FrmNewPatient frmNewPatient;

    public NewPatientController(FrmNewPatient frmNewPatient) {
        this.frmNewPatient = frmNewPatient;
        addActionListenersTxt();
        addActionListeners();
    }

    private void addActionListeners() {
        frmNewPatient.btnSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateForm();                   
                
                if(frmNewPatient.getTxtName().getText().equals("Name must have only letters!") ||
                        frmNewPatient.getTxtName().getText().equals("Name shouldn't be empty!") ||
                        frmNewPatient.getTxtLastname().getText().equals("Lastname must have only letters") ||
                        frmNewPatient.getTxtLastname().getText().equals("Lastname shouldn't be empty") ||
                        frmNewPatient.getTxtAddress().getText().equals("Address must have only letters and numbers!") ||
                        frmNewPatient.getTxtAddress().getText().equals("Address shouldn't be empty!") ||
                        frmNewPatient.getTxtEmail().getText().equals("Email shouldn't be empty!") ||
                        frmNewPatient.getTxtEmail().getText().equals("Email must have only letters!")){
                
                    JOptionPane.showMessageDialog(
                        null,
                        "You didn't fill the blanks crrectly!",
                        "Filling the blanks error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                    
                }else{
                    
                    
                    
                    int reply = JOptionPane.showConfirmDialog(
                        frmNewPatient,
                        "Are you sure you want to save the patient?",
                        "Confirm saving the patient",
                        JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        save();
                    }
                }
               
            }

            private void save() {
                try {
                    Pacijent patient = new Pacijent();
                    patient.setIme(frmNewPatient.getTxtName().getText().trim());
                    patient.setPrezime(frmNewPatient.getTxtLastname().getText().trim());
                    patient.setEmail(frmNewPatient.getTxtEmail().getText().trim());
                    patient.setAdresa(frmNewPatient.getTxtAddress().getText().trim());
                    patient.setDatumRodjenja(frmNewPatient.getjCalDateOfBirth().getDate());
                    
                    List<Pacijent> allPatietnts = Communication.getInstance().getAllPatients(new Pacijent());
                    for(Pacijent p : allPatietnts)
                        if(p.getEmail().equals(patient.getEmail())){
                            JOptionPane.showMessageDialog(
                        null,
                        "There can't be two the same email addresses!\n"
                                + "Please, enter other email address",
                        "Email error",
                        JOptionPane.ERROR_MESSAGE);
                        return;
                        }
                            
                    
                    if(patient.getDatumRodjenja().after(new Date())) {
                        JOptionPane.showMessageDialog(
                        null,
                        "The date can't be in the future!",
                        "Date error",
                        JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    //Controller.getInstance().addPatient(patient);
                    Communication.getInstance().addPatient(patient);
                    JOptionPane.showMessageDialog(frmNewPatient,
                            "The patient has been saved",
                            "Saving patient information",
                            JOptionPane.INFORMATION_MESSAGE);
                    frmNewPatient.dispose();
                } catch (Exception ex) {
                    //ex.printStackTrace();
                    //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
                    JOptionPane.showMessageDialog(
                            frmNewPatient,
                            "Server is down!",
                            "Server down",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);//izlaz
                    
                }           
            }
        });

        frmNewPatient.btnEnableChangeAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        frmNewPatient.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                frmNewPatient.dispose();
            }
        });

        frmNewPatient.btnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                    
                    int reply = JOptionPane.showConfirmDialog(
                            frmNewPatient,
                            "Are you sure you want to delete tha patient?",
                            "Confirm deleting the patient",
                            JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        delete();
                    }
                }
            

            private void delete() {
                Pacijent patient = makePatientFromForm();
                boolean isOk = examinationsCheck(patient);
                if(isOk == false){
                    JOptionPane.showMessageDialog(
                            frmNewPatient,
                            "The patient can't be deleted, because the patient is in some examinations!",
                            "Deleting patient error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    Communication.getInstance().deletePatient(patient);
                    JOptionPane.showMessageDialog(frmNewPatient,
                            "Patient has been successfully deleted !\n",
                            "Deleting patient error",
                            JOptionPane.INFORMATION_MESSAGE);
                    frmNewPatient.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            frmNewPatient,
                            "Error deleting patient!\n" + ex.getMessage(),
                            "Delete patient error", JOptionPane.ERROR_MESSAGE);
                    //Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private boolean examinationsCheck(Pacijent patient) {
                try {
                    
                    List<Doktor> doctors = Communication.getInstance().getAllDoctors(new Doktor());
                    int number = doctors.size();
                    for(int i = 0; i < number;i++){
                        
                        Pregled p = new Pregled();
                        p.setDoktor(doctors.get(i));
                        List<Pregled> exams = Communication.getInstance().getAllExaminations(p);
                        if(exams == null || exams.size() == 0)
                            continue;
                        for(Pregled e : exams)
                            if(e.getPacijent().equals(patient))
                                return false;
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
                
            }
        });

        frmNewPatient.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addActionListenersTxt();
                validateForm();
                if(frmNewPatient.getTxtName().getText().equals("Name must have only letters!") ||
                        frmNewPatient.getTxtName().getText().equals("Name shouldn't be empty!") ||
                        frmNewPatient.getTxtLastname().getText().equals("Lastname must have only letters") ||
                        frmNewPatient.getTxtLastname().getText().equals("Lastname shouldn't be empty") ||
                        frmNewPatient.getTxtAddress().getText().equals("Address must have only letters and numbers!") ||
                        frmNewPatient.getTxtAddress().getText().equals("Address shouldn't be empty!") ||
                        frmNewPatient.getTxtEmail().getText().equals("Email shouldn't be empty!") ||
                        frmNewPatient.getTxtEmail().getText().equals("Email must have only letters!")){
                
                    JOptionPane.showMessageDialog(
                        null,
                        "You didn't fill the blanks crrectly!",
                        "Filling the blanks error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                    
                }else {
                    
                    /*if(frmNewPatient.getjCalDateOfBirth().getDate().after(new Date())) {
                        JOptionPane.showMessageDialog(
                        null,
                        "The date can't be in the future!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                        return;
                    }*/
                    
                }
                int reply = JOptionPane.showConfirmDialog(
                        frmNewPatient,
                        "Are you sure you want to save this changes on the patient?",
                        "Confirm editing the patient",
                        JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    edit();
                }
            }

            private void edit() {
                Pacijent patient = makePatientFromForm();

                try {
                    Communication.getInstance().editPatient(patient);
                    JOptionPane.showMessageDialog(
                            frmNewPatient,
                            "The patient has been edited and saved!\n",
                            "Edititing the patient information",
                            JOptionPane.INFORMATION_MESSAGE);
                    frmNewPatient.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            frmNewPatient,
                            "The patient hasn't been edited and saved!",
                            "Editing the patient error",
                            JOptionPane.ERROR_MESSAGE);
                    //Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void openForm(FormMode formMode) {
        frmNewPatient.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView(formMode);
        frmNewPatient.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        setupComponents(formMode);
        frmNewPatient.setLocationRelativeTo(null);
        frmNewPatient.setResizable(false);
    }

    private void validateForm() {
        validateName();
        validateLastname();
        validateAddress();
        validateEmail();
    }

    private void setupComponents(FormMode formMode) {
        new Validate().formModeSetter(frmNewPatient, formMode);
    }

    private Pacijent makePatientFromForm() {
        Pacijent patient = new Pacijent();

        patient.setIme(frmNewPatient.getTxtName().getText().trim());
        patient.setPrezime(frmNewPatient.getTxtLastname().getText().trim());
        patient.setEmail(frmNewPatient.getTxtEmail().getText().trim());
        patient.setAdresa(frmNewPatient.getTxtAddress().getText().trim());
        patient.setDatumRodjenja(frmNewPatient.getjCalDateOfBirth().getDate());
        patient.setId(Long.parseLong(frmNewPatient.getTxtId().getText()));
        return patient;
    }

    private void validateName() {
        new Validate().validate(frmNewPatient, "[^A-Za-z\\s]", "Firstname");
    }

    private void validateLastname() {
        new Validate().validate(frmNewPatient, "[^A-Za-z\\s]", "Lastname");
    }

    private void validateAddress() {
        new Validate().validate(frmNewPatient, "[^A-Za-z0-9/\\s]", "Address");
    }

    private void validateEmail() {
        new Validate().validate(frmNewPatient, "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", "Email");
    }

    private void addActionListenersTxt() {

        frmNewPatient.getTxtName().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Validate().validate(frmNewPatient, "", "NameClick");
            }
        });

        frmNewPatient.getTxtLastname().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Validate().validate(frmNewPatient, "", "LastnameClick");
            }
        });

        frmNewPatient.getTxtEmail().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Validate().validate(frmNewPatient, "", "EmailClick");
            }
        });

        frmNewPatient.getTxtAddress().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Validate().validate(frmNewPatient, "", "AddressClick");
            }
        });
    }
}
