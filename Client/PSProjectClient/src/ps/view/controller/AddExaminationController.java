/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import ps.communication.Communication;
import ps.domain.Doktor;
import ps.domain.Pacijent;
import ps.domain.Pregled;
import ps.domain.StavkaPregleda;
import ps.domain.Termin;
import ps.domain.Usluga;
import ps.tableModel.TableModelAddExamination;
import ps.view.cordinator.MainCordinator;
import ps.view.form.FrmAddExamination;
import ps.view.form.FrmViewAllExaminations;
import ps.view.util.FormMode;

/**
 *
 * @author Zbook G3
 */
public class AddExaminationController {

    FrmAddExamination frmAddExamination;
    TableModelAddExamination modelMain;
    boolean validatedTermin = false;
    Termin terminMain;
    boolean saved = false;
    boolean edited = false;
    Pacijent patientMain = null;
    
    public AddExaminationController(FrmAddExamination frmAddExamination) {
        this.frmAddExamination = frmAddExamination;
        addActionListeners();
    }    

    public void openForm(FormMode formMode) {
        fillForm();
        setupComponents(formMode);
        prepareView();

        switch (formMode) {
            case FORM_ADD:
                fillTblExamination();
                break;
            case FORM_EDIT:
                prepareExaminationOnForm();
                break;
            case FORM_VIEW:
                prepareExaminationOnForm();
                break;
        }
        
        frmAddExamination.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        frmAddExamination.setVisible(true);

        
    }

    private void addActionListeners() {        

        frmAddExamination.cbServiceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fillWithCb();
            }

            private void fillWithCb() {
                Usluga service = (Usluga) frmAddExamination.getCbService().getSelectedItem();
                if(frmAddExamination.getCbService().getSelectedIndex() != -1)
                    frmAddExamination.getTxtPrice().setText(service.getCena() + "");
            }
        });
        
        frmAddExamination.btnChoosePatientAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) { 
                MainCordinator.getInstance().openFrmViewPatients(FormMode.FORM_ADD, AddExaminationController.this);
            }
        });
        
        //Dodavanje stavke pregleda
        frmAddExamination.btnAddItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(frmAddExamination.getCbService().getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "You have to chose service!",
                            "Service error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addItem();
            }

            private void addItem() {
                try {                    
                    String note = frmAddExamination.getTxtNote().getText().trim();
                    double price = Double.parseDouble(frmAddExamination.getTxtPrice().getText().trim());
                    double duration = Double.parseDouble(frmAddExamination.getTxtDuration().getText().trim());
                    Usluga service = (Usluga) frmAddExamination.getCbService().getSelectedItem();
                    if (note.length() <= 50) {
                        System.out.println(frmAddExamination.getTblItems().getModel());
                        TableModelAddExamination model1 = (TableModelAddExamination) 
                                frmAddExamination.getTblItems().getModel();
                        model1.addExaminationItem(service, price, duration, note);
                        double priceFromModel = model1.getExamination().getUkupnaCena();
                        double durationFromModel = model1.getExamination().getUkupnoTrajanje();
                        frmAddExamination.getTxtTotalPrice().setText(String.valueOf(priceFromModel));
                        frmAddExamination.getTxtTotalDuration().setText(String.valueOf(durationFromModel));
                        clearViewItem();
                    } else {
                        JOptionPane.showMessageDialog(frmAddExamination,
                                "Note is too long. Maximum is 50 characters. The item is not added.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "Invalid examination data!",
                            "Examination error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Brisanje stavke pregleda
        frmAddExamination.btnDeleteItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }

            private void deleteItem() {
                int rowIndex = frmAddExamination.getTblItems().getSelectedRow();
                TableModelAddExamination model = (TableModelAddExamination) 
                        frmAddExamination.getTblItems().getModel();
                if (rowIndex >= 0) {
                    model.removeExaminationItem(rowIndex);
                    double priceFromModel = model.getExamination().getUkupnaCena();
                    double durationFromModel = model.getExamination().getUkupnoTrajanje();
                    frmAddExamination.getTxtTotalPrice().setText(String.valueOf(priceFromModel));
                    frmAddExamination.getTxtTotalDuration().setText(String.valueOf(durationFromModel));
                } else {
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "Examination item is not selected!",
                            "Examination item error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Sacuvaj pregled
        frmAddExamination.btnSaveAllAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                int reply = JOptionPane.showConfirmDialog(frmAddExamination,
                        "Are you sure you want to save the eximanation?",
                        "Confirm saving examination",
                        JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    saveExamination();
                    if(saved == true) {
                        setupComponents(FormMode.FORM_VIEW);
                        saved = false;
                    }
                }
            }

            private void saveExamination() {
                try {
                    
                    /*if(frmAddExamination.getCbPatient().getSelectedIndex() == -1){
                        JOptionPane.showMessageDialog(frmAddExamination,
                            "Patient has to be chosen!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    } */
                    
                    if(patientMain == null){
                        JOptionPane.showMessageDialog(frmAddExamination,
                            "Patient has to be chosen!",
                            "Patient error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    TableModelAddExamination model = (TableModelAddExamination)
                            frmAddExamination.getTblItems().getModel();
                    
                    if(model.getExamination().getListaStavkePregleda().size() == 0){
                        JOptionPane.showMessageDialog(frmAddExamination,
                            "It has to be at least one item!",
                            "Examination item error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    boolean ok = validateTerminForExamination();
                    if(ok == false)
                        return;
                    
                    terminMain = makeTerminFromForm();
                    
                    boolean ok2 = checkIsTerminBusy();
                    if(ok2 == false)
                        return;
                    
                    
                    Pregled exam = model.getExamination();
                    //exam.setPacijent((Pacijent) frmAddExamination.getCbPatient().getSelectedItem());
                    exam.setPacijent(patientMain);
                    exam.setTermin(terminMain);
                    Communication.getInstance().addTermin(terminMain);
                    terminMain = null;
                    if (MainCordinator.getInstance().getMainContoller().registred) {
                        exam.setDoktor((Doktor) MainCordinator.getInstance().getParam("REGISTERED_USER"));
                    } else {
                        exam.setDoktor((Doktor) MainCordinator.getInstance().getParam("CURRENT_USER"));
                    }
                    exam.setUkupnaCena(Double.parseDouble(frmAddExamination.getTxtTotalPrice().getText()));
                    exam.setUkupnoTrajanje(Double.parseDouble(frmAddExamination.getTxtTotalDuration().getText()));
                    //for (StavkaPregleda item : exam.getListaStavkePregleda()) {
                      //  System.out.println(item);
                    //}

                    
                    Communication.getInstance().addExamination(exam);
                    frmAddExamination.getTxtID().setText(String.valueOf(exam.getId()));
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "The system has saved the examination!",
                            "Examination information",
                            JOptionPane.INFORMATION_MESSAGE);
                    saved = true;
                    
                    String recepientPatient = exam.getPacijent().getEmail();
                    String descriptionPatient = "Dear " + exam.getPacijent().toString() + ",\n\n"
                            + "You have scheduled an examination in offie LukaEye, these are the details: \n"
                            + "Patient: " + exam.getPacijent().toString() + "\n"
                            + "Doctor: " + exam.getDoktor().toString() + "\n"
                            +  exam.getTermin().getDatumTermina() 
                                + " "+ exam.getTermin().getVremeDo() + " " + exam.getTermin().getVremeDo()
                            + "\nWe are glad you are part of LukaEye.\n\n"
                            + "Yours sincerely,\n"
                            + "LukaEye";
                    MainCordinator.getInstance().sendEmail(recepientPatient, descriptionPatient);
                    String recepientDoctor = exam.getDoktor().getEmail();
                    String descriptionDoctor = "Dear " + exam.getDoktor() + ",\n\n"
                            + "You have scheduled an examination in offie LukaEye, these are the details: \n"
                            + "Doctor: " + exam.getDoktor().toString() + "\n"
                            + "Patient: " + exam.getPacijent().toString() + "\n"
                            + "Date of termin: " +  exam.getTermin().getDatumTermina().toString().substring(0, 10) + "\n" 
                            + "From: " + exam.getTermin().getVremeOd() + " to: " + exam.getTermin().getVremeDo()
                            + "\nWe are glad you are part of LukaEye.\n\n"
                            + "Yours sincerely,\n"
                            + "LukaEye";;
                    MainCordinator.getInstance().sendEmail(recepientDoctor, descriptionDoctor);
                            
                    //patientMain = null;
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "The system has not saved the eximanation!",
                            "Examination error",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);//izlaz
                }
            }

            
        });

        //Otvaranje forme sa terminima
        frmAddExamination.btnScheduledTerminsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openScheduledTerminsForm();
            }
        });

        //Prosirenje opis polja 
        frmAddExamination.getTblItems().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                popUp(evt);
            }

            private void popUp(java.awt.event.MouseEvent evt) {
                TableModelAddExamination tmae = (TableModelAddExamination) 
                        frmAddExamination.getTblItems().getModel();
                String description = tmae.getItemDescription(
                        frmAddExamination.getTblItems().rowAtPoint(evt.getPoint()),
                        frmAddExamination.getTblItems().columnAtPoint(evt.getPoint()));
                JOptionPane.showMessageDialog(frmAddExamination, description);
            }
        });

        //Ponistenje prozora medical examination
        frmAddExamination.btnCancelAllAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                terminMain = null;
                patientMain = null;
                frmAddExamination.dispose();
            }
        });

        frmAddExamination.btnEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        frmAddExamination.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editExamination();
                //refresh();
                if(edited == true){
                    setupComponents(FormMode.FORM_VIEW);
                    edited = false;
                }
            }

            private void editExamination() {
                
                /*if(frmAddExamination.getCbPatient().getSelectedIndex() == -1){
                        JOptionPane.showMessageDialog(frmAddExamination,
                            "Patient has to be chosen!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }                        */
                
                if(patientMain == null){
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "Patient has to be chosen!",
                            "Patient error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                }
                    
                    TableModelAddExamination model2 = (TableModelAddExamination)
                            frmAddExamination.getTblItems().getModel();
                    
                    if(model2.getExamination().getListaStavkePregleda().size() == 0){
                        JOptionPane.showMessageDialog(frmAddExamination,
                            "It has to be at least one item!",
                            "Examination item error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                
                try {
                    TableModelAddExamination model = (TableModelAddExamination)
                            frmAddExamination.getTblItems().getModel();
                    Pregled exam = model.getExamination();
                    //exam.setPacijent((Pacijent) frmAddExamination.getCbPatient().getSelectedItem());
                    exam.setPacijent(patientMain);
                    Doktor d;
                    if (MainCordinator.getInstance().getMainContoller().registred) {
                        d = (Doktor) MainCordinator.getInstance().getParam("REGISTERED_USER");
                        //exam.setDoktor((Doktor) MainCordinator.getInstance().getParam("REGISTERED_USER"));
                    } else {
                        //exam.setDoktor((Doktor) MainCordinator.getInstance().getParam("CURRENT_USER"));
                        d = (Doktor) MainCordinator.getInstance().getParam("CURRENT_USER");
                    }
                    exam.setDoktor(d);
                    
                    exam.setUkupnaCena(Double.parseDouble(frmAddExamination.getTxtTotalPrice().getText()));
                    exam.setUkupnoTrajanje(Double.parseDouble(frmAddExamination.getTxtTotalDuration().getText()));
                    
                    Termin exTermin = exam.getTermin();
                    
                    boolean ok = validateTerminForExamination();
                    if(ok == false)
                        return;
                    
                    terminMain = makeTerminFromForm();                                                           
                    
                    if(exTermin.equals(terminMain)) {
                        if(exTermin.getVremeDo().equals(terminMain.getVremeDo())) {
                            exam.setTermin(exTermin);
                            Communication.getInstance().editExamination(exam);
                            edited = true;
                        }else{
                            Communication.getInstance().deleteTermin(exTermin);
                            Communication.getInstance().addTermin(terminMain);
                            exam.setTermin(terminMain);
                            Communication.getInstance().addExamination(exam);
                            edited = true;
                        }
                    }else {
                        boolean ok2 = checkIsTerminBusy();
                        if(ok2 == false)
                            return;
                        exam.setTermin(terminMain);
                        Communication.getInstance().addTermin(terminMain);
                        Communication.getInstance().editExamination(exam);
                        Communication.getInstance().deleteTermin(exTermin);
                        edited = true;
                    }
                    
                    terminMain = null;
                    //patientMain = null;
                    
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "The examination has been edited and saved!",
                            "Examination information",
                            JOptionPane.INFORMATION_MESSAGE);

                    String recepientPatient = exam.getPacijent().getEmail();
                    String descriptionPatient = "Dear " + exam.getPacijent().toString() + ",\n\n"
                            + "You have edited the scheduled examination in offie LukaEye, these are the edited details: \n"
                            + "Patient: " + exam.getPacijent().toString() + "\n"
                            + "Doctor: " + exam.getDoktor().toString() + "\n"
                            + "Termin: " + exam.getTermin().toString() + "\nWe are glad you are part of LukaEye.\n\n"
                            + "Yours sincerely,\n"
                            + "LukaEye";
                    MainCordinator.getInstance().sendEmail(recepientPatient, descriptionPatient);
                    String recepientDoctor = exam.getDoktor().getEmail();
                    String descriptionDoctor = "Dear " + exam.getDoktor() + ",\n\n"
                            + "You have edited the scheduled examination in offie LukaEye, these are the new details: \n"
                            + "Doctor: " + exam.getDoktor().toString() + "\n"
                            + "Patient: " + exam.getPacijent().toString() + "\n"
                            + "Termin: " + exam.getTermin().toString() + "\nWe are glad you are part of LukaEye.\n\n"
                            + "Yours sincerely,\n"
                            + "LukaEye";;
                    MainCordinator.getInstance().sendEmail(recepientDoctor, descriptionDoctor);
                    
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "The examination has not been edited and saved!",
                            "Examination error",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                    setupComponents(FormMode.FORM_VIEW);
                }
            }

        });

        frmAddExamination.btnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteExamination();
                prepareView();
                clearView();
                setupComponents(FormMode.FORM_ADD);
                fillTblExamination();
                //refresh();
            }

            private void deleteExamination() {
                TableModelAddExamination model = (TableModelAddExamination)
                        frmAddExamination.getTblItems().getModel();
                Pregled exam = model.getExamination();
                //System.out.println("NAPOMENA: !!!!!!!!!!!" + exam.getTermin().toString());
                try {
                    Communication.getInstance().deleteExamination(exam);
                    Communication.getInstance().deleteTermin(exam.getTermin());
                    
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "The system has deleted the examination!",
                            "Examination information",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                    String recepientPatient = exam.getPacijent().getEmail();
                    String descriptionPatient = "Dear " + exam.getPacijent().toString() + ",\n\n"
                            + "You have canceled an examination in offie LukaEye, these were the details: \n"
                            + "Patient: " + exam.getPacijent().toString() + "\n"
                            + "Doctor: " + exam.getDoktor().toString() + "\n"
                            + "Termin: " + exam.getTermin().toString() + "\nWe are glad you are part of LukaEye.\n\n"
                            + "Yours sincerely,\n"
                            + "LukaEye";
                    MainCordinator.getInstance().sendEmail(recepientPatient, descriptionPatient);
                    String recepientDoctor = exam.getDoktor().getEmail();
                    String descriptionDoctor = "Dear " + exam.getDoktor() + ",\n\n"
                            + "You have canceled an examination in offie LukaEye, these were the details: \n"
                            + "Doctor: " + exam.getDoktor().toString() + "\n"
                            + "Patient: " + exam.getPacijent().toString() + "\n"
                            + "Termin: " + exam.getTermin().toString() + "\nWe are glad you are part of LukaEye.\n\n"
                            + "Yours sincerely,\n"
                            + "LukaEye";;
                    MainCordinator.getInstance().sendEmail(recepientDoctor, descriptionDoctor);
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "The system has not deleted the examination!\n"
                                    + "The system has not found that examination",
                            "Examination error",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);//izlaz
                }
            }
        });
        
        
        frmAddExamination.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                patientMain = null;
            }
        });
                

    }

    private void fillForm() {
        Pacijent p = new Pacijent();
        Usluga service = new Usluga();
        try {
            //frmAddExamination.getCbPatient().setModel(new DefaultComboBoxModel(Communication.getInstance().getAllPatients(p).toArray()));
            frmAddExamination.getCbService().setModel(new DefaultComboBoxModel(Communication.getInstance().getAllServices(service).toArray()));
            frmAddExamination.getCbPatient().setVisible(false);
        } catch (Exception ex) {
            //ex.printStackTrace();
            //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
            JOptionPane.showMessageDialog(frmAddExamination,
                    "Server is down!",
                    "Server down", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public void refresh() {
        prepareExaminationOnForm();
    }
    
    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmAddExamination.getTxtID().setEditable(false);
                frmAddExamination.getTxtTotalPrice().setEditable(false);
                frmAddExamination.getTxtTotalDuration().setEditable(false);
                frmAddExamination.getTxtCurrentDoctor().setEditable(false);
                frmAddExamination.getBtnEnableChanges().setVisible(false);
                frmAddExamination.getBtnEdit().setVisible(false);
                frmAddExamination.getBtnDelete().setVisible(false);
                frmAddExamination.getTxtPrice().setEditable(false);
                frmAddExamination.getBtnSaveAll().setVisible(true);
                frmAddExamination.getTxtPatient().setEditable(false);
                break;
            case FORM_VIEW:
                frmAddExamination.getTxtID().setEditable(false);
                frmAddExamination.getTxtTotalPrice().setEditable(false);
                frmAddExamination.getTxtTotalDuration().setEditable(false);
                frmAddExamination.getTxtCurrentDoctor().setEditable(false);
                frmAddExamination.getBtnEnableChanges().setVisible(true);
                frmAddExamination.getBtnEdit().setVisible(false);
                frmAddExamination.getBtnDelete().setVisible(false);
                frmAddExamination.getBtnCancelAll().setVisible(true);
                frmAddExamination.getBtnSaveAll().setVisible(false);
                frmAddExamination.getTxtStartHours().setEditable(false);
                frmAddExamination.getTxtStartMinutes().setEditable(false);
                frmAddExamination.getTxtTheEndHours().setEditable(false);
                frmAddExamination.getTxtTheEndMinutes().setEditable(false);
                frmAddExamination.getTxtPrice().setEditable(false);
                frmAddExamination.getTxtDuration().setEditable(false);
                frmAddExamination.getTxtNote().setEditable(false);
                frmAddExamination.getBtnAddItem().setVisible(false);
                frmAddExamination.getBtnScheduledTermins().setVisible(false);
                frmAddExamination.getBtnDeleteItem().setVisible(false);
                frmAddExamination.getCbPatient().setVisible(false);
                frmAddExamination.getCbPatient().setEditable(false);
                frmAddExamination.getCbService().setVisible(false);
                frmAddExamination.getDateTermin().setVisible(true);
                frmAddExamination.getTxtPatient().setEditable(false);
                break;
            case FORM_EDIT:
                frmAddExamination.getTxtID().setEditable(false);
                frmAddExamination.getTxtTotalPrice().setEditable(false);
                frmAddExamination.getTxtTotalDuration().setEditable(false);
                frmAddExamination.getTxtCurrentDoctor().setEditable(false);
                frmAddExamination.getBtnEnableChanges().setVisible(false);
                frmAddExamination.getBtnEdit().setVisible(true);
                frmAddExamination.getBtnDelete().setVisible(true);
                frmAddExamination.getBtnCancelAll().setVisible(true);
                frmAddExamination.getBtnSaveAll().setVisible(false);
                frmAddExamination.getBtnScheduledTermins().setVisible(true);
                frmAddExamination.getTxtStartHours().setEditable(true);
                frmAddExamination.getTxtStartMinutes().setEditable(true);
                frmAddExamination.getTxtTheEndHours().setEditable(true);
                frmAddExamination.getTxtTheEndMinutes().setEditable(true);
                frmAddExamination.getTxtPrice().setEditable(true);                
                frmAddExamination.getTxtDuration().setEditable(true);
                frmAddExamination.getTxtNote().setEditable(true);
                frmAddExamination.getBtnAddItem().setVisible(true);
                frmAddExamination.getBtnDeleteItem().setVisible(true);
                frmAddExamination.getCbPatient().setVisible(false);
                frmAddExamination.getCbPatient().setEditable(false);
                frmAddExamination.getCbService().setVisible(true);
                frmAddExamination.getDateTermin().setVisible(true);
                frmAddExamination.getTxtPatient().setEditable(false);
                break;
        }
    }

    private void prepareView() {
        frmAddExamination.setTitle("Add examination");
        frmAddExamination.setLocationRelativeTo(null);
        frmAddExamination.setResizable(false);
        Doktor doctor = null;
        if (MainCordinator.getInstance().getMainContoller().registred == true) {
            doctor = (Doktor) MainCordinator.getInstance().getParam("REGISTERED_USER");
        } else {
            doctor = (Doktor) MainCordinator.getInstance().getParam("CURRENT_USER");
        }

        //Examination
        frmAddExamination.getTxtID().setText("");
        frmAddExamination.getTxtTotalPrice().setText("0.0");
        frmAddExamination.getTxtTotalDuration().setText("0.0");
        //frmAddExamination.getCbPatient().setSelectedIndex(-1);
        frmAddExamination.getTxtCurrentDoctor().setText(doctor.getIme() + " " + doctor.getPrezime());

        //Item of examination
        frmAddExamination.getTxtPrice().setText("0.0");
        frmAddExamination.getTxtPrice().grabFocus();
        frmAddExamination.getTxtPrice().setSelectionStart(0);
        frmAddExamination.getTxtDuration().setText("0.0");
        frmAddExamination.getCbService().setSelectedIndex(-1);
    }

    private void prepareExaminationOnForm() {
        Pregled examination = (Pregled) MainCordinator.getInstance().getParam("PARAM_EXAMINATION");
        //if(examination == null)
          //  System.out.println("NULL JE EXAMINATION");
        //System.out.println(examination.toString());
        frmAddExamination.getTxtID().setText(String.valueOf(examination.getId()));
        frmAddExamination.getTxtTotalPrice().setText(String.valueOf(examination.getUkupnaCena()));
        frmAddExamination.getTxtTotalDuration().setText(String.valueOf(examination.getUkupnoTrajanje()));
        frmAddExamination.getTxtCurrentDoctor().setText(examination.getDoktor().getIme() + " " + 
                examination.getDoktor().getPrezime());
        //frmAddExamination.getCbPatient().setSelectedItem(examination.getPacijent());
        frmAddExamination.getTxtPatient().setText(examination.getPacijent().toString());
        patientMain = examination.getPacijent();
        
        /*Izvlacimo sate i minute iz relacije*/
        String[] startTime = examination.getTermin().getVremeOd().split(":");
        String[] theEndTime = examination.getTermin().getVremeDo().split(":");
        frmAddExamination.getTxtStartHours().setText(startTime[0]);
        frmAddExamination.getTxtStartMinutes().setText(startTime[1]);
        frmAddExamination.getTxtTheEndHours().setText(theEndTime[0]);
        frmAddExamination.getTxtTheEndMinutes().setText(theEndTime[1]);
        frmAddExamination.getDateTermin().setDate(examination.getTermin().getDatumTermina());

        try {
            StavkaPregleda item = new StavkaPregleda();
            Pregled p = new Pregled();
            p.setId(examination.getId());
            item.setPregled(p);
            //List<StavkaPregleda> stavkePregleda = pokupiStavku(String.valueOf(medicalExamination.getId()));
            
            System.out.println("!!!!!!!!!!!!!" + item.getPregled().getId());
            
            List<StavkaPregleda> listOfItems = getAllItems(item);
            //List<StavkaPregleda> listOfAllItems = getAllItems(item);
            //List<StavkaPregleda> listOfDesiredItems = new ArrayList<>();
            //for(StavkaPregleda sp : listOfAllItems)
              //  if(sp.getPregled().getId() == examination.getId())
                //    listOfDesiredItems.add(sp);
            
            examination.setListaStavkePregleda(listOfItems);
            //examination.setListaStavkePregleda(listOfDesiredItems);
            TableModelAddExamination tmae = new TableModelAddExamination(examination);
            frmAddExamination.getTblItems().setModel(tmae);

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(AddExaminationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillTblExamination() {
        modelMain = new TableModelAddExamination(new Pregled());
        frmAddExamination.getTblItems().setModel(modelMain);
    }

    private List<StavkaPregleda> getAllItems(StavkaPregleda item) throws Exception {
        return Communication.getInstance().getAllExaminationItems(item);
    }
    
    
    private void clearView() {
        frmAddExamination.getDateTermin().setDate(new Date());
        frmAddExamination.getTxtStartHours().setText("");
        frmAddExamination.getTxtStartMinutes().setText("");
        frmAddExamination.getTxtTheEndHours().setText("");
        frmAddExamination.getTxtTheEndMinutes().setText("");
        frmAddExamination.getTxtPatient().setText("");
    }
    
    private boolean validateTermin(Date dateOfExamination, String startHours, String startMinutes,
            String theEndHours, String theEndMinutes) throws Exception {
        
        if (dateOfExamination.before(new Date())) {
                throw new Exception("Date cannot be from the past!");
        } 
        
        if ((Integer.parseInt(startHours) < 23 && Integer.parseInt(startHours) >= 0)
                && (Integer.parseInt(startMinutes) < 60 && Integer.parseInt(startMinutes) >= 0)
                && (Integer.parseInt(theEndHours) < 23 && Integer.parseInt(theEndHours) >= 0)
                && (Integer.parseInt(theEndMinutes) < 60 && Integer.parseInt(theEndMinutes) >= 0)) {
            if (Integer.parseInt(theEndHours) < Integer.parseInt(startHours)) {
                throw new Exception("The end of termin cannot be before the start of termin!");
                //JOptionPane.showMessageDialog(frmAddExamination, this);
            }
            return true;
        } else {
            throw new Exception("Hours have to be between 0 and 23!\nMinutes have to be between 0 and 60");
        }

    }
    
    private Termin makeTerminFromForm() {
        Termin termin = new Termin();

        Date dateOfExamination = frmAddExamination.getDateTermin().getDate();
        String startTime = frmAddExamination.getTxtStartHours().getText().trim() + ":"
                + frmAddExamination.getTxtStartMinutes().getText().trim();

        String theEndTime = frmAddExamination.getTxtTheEndHours().getText().trim() + ":"
                + frmAddExamination.getTxtTheEndMinutes().getText().trim();

        termin.setDatumTermina(dateOfExamination);
        termin.setVremeOd(startTime);
        termin.setVremeDo(theEndTime);
        /*try {
            validatedTermin = validateTermin(dateOfExamination, frmAddExamination.getTxtStartHours().getText().trim(),
                    frmAddExamination.getTxtStartMinutes().getText().trim(), frmAddExamination.getTxtTheEndHours().getText().trim(),
                    frmAddExamination.getTxtTheEndMinutes().getText().trim());
        } catch (Exception ex) {
            Logger.getLogger(AddExaminationController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmAddExamination,
                    "Check values for the date and the time of termin!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }*/
        return termin;
    }
    
     private void clearViewItem() {
                frmAddExamination.getTxtDuration().setText("0.0");
                frmAddExamination.getTxtPrice().setText("0.0");
                frmAddExamination.getTxtNote().setText("");
                frmAddExamination.getCbService().setSelectedIndex(-1);
    }
     
    private boolean validateTerminForExamination() {
        if(frmAddExamination.getTxtStartHours().getText().equals("") || frmAddExamination.getTxtStartMinutes().getText().equals("")
                        || frmAddExamination.getTxtTheEndHours().getText().equals("") || frmAddExamination.getTxtTheEndMinutes().getText().equals("")) {
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "Fields for date can't be empty!",
                            "Date error",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                
                if(frmAddExamination.getTxtStartHours().getText().length() != 2 || 
                        frmAddExamination.getTxtStartMinutes().getText().length() != 2 ||
                        frmAddExamination.getTxtTheEndHours().getText().length() != 2 ||
                        frmAddExamination.getTxtTheEndMinutes().getText().length() != 2) {
                    
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "Fields of date have to be with 2 numbers!\n"
                                    + "For example: \n"
                                    + "Start time: 13:00\n"
                                    + "The end time: 14:00",
                            "Date error",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                    
                }
                
                if(frmAddExamination.getDateTermin().getDate().before(new Date())) {
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "Date can't be in the past!",
                            "Date error",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }

        Date dateOfExamination = frmAddExamination.getDateTermin().getDate();
        
        String startHours = frmAddExamination.getTxtStartHours().getText().trim();
        String startMinutes = frmAddExamination.getTxtStartMinutes().getText().trim();

        String theEndHours = frmAddExamination.getTxtTheEndHours().getText().trim();
        String theEndMinutes = frmAddExamination.getTxtTheEndMinutes().getText().trim();
           
                if ((Integer.parseInt(startHours) < 24 && Integer.parseInt(startHours) >= 0)
                    && (Integer.parseInt(startMinutes) < 60 && Integer.parseInt(startMinutes) >= 0)
                    && (Integer.parseInt(theEndHours) < 24 && Integer.parseInt(theEndHours) >= 0)
                    && (Integer.parseInt(theEndMinutes) < 60 && Integer.parseInt(theEndMinutes) >= 0)) {
                    
                    if (Integer.parseInt(theEndHours) < Integer.parseInt(startHours)) {
                        
                        JOptionPane.showMessageDialog(frmAddExamination,
                            "The end hours can't be before start hours",
                            "Date error",
                            JOptionPane.ERROR_MESSAGE);
                        return false; 
                        
                    }
                    if(Integer.parseInt(theEndHours) == Integer.parseInt(startHours) &&
                            Integer.parseInt(theEndMinutes) <= Integer.parseInt(startMinutes)){
                        
                        JOptionPane.showMessageDialog(frmAddExamination,
                            "Because start hour and the end hour are the same, "
                                    + "the end minutes can't be before start minutes",
                            "Date error",
                            JOptionPane.ERROR_MESSAGE);
                        return false; 
                        
                    }
        } else {
                    
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "Hours have to be between 0 and 23!\nMinutes have to be between 0 and 60",
                            "Date error",
                            JOptionPane.ERROR_MESSAGE);
                        return false; 
            
        }
                return true;
    }
    
    private boolean checkIsTerminBusy() {
                try {
                    //if(validateTermin)
                        //validatedTermin = false;
                        Pregled p = new Pregled();
                        Doktor d;
                        if (!MainCordinator.getInstance().getMainContoller().registred){
                            d = (Doktor) MainCordinator.getInstance().getParam("CURRENT_USER");
                        }
                        else d = (Doktor) MainCordinator.getInstance().getParam("REGISTERED_USER");
                        p.setDoktor(d);
                        List<Pregled> exams = Communication.getInstance().getAllExaminations(p);
                        List<Termin> listOfTermins = new ArrayList<>();
                        for(Pregled e : exams)
                            listOfTermins.add(e.getTermin());
                        for(Termin t : listOfTermins)
                            if(terminMain.equals(t)){
                                JOptionPane.showMessageDialog(frmAddExamination,
                                    "This termin is busy!",
                                    "Termin error",
                                    JOptionPane.ERROR_MESSAGE);
                                terminMain = null;
                                
                                return false;
                            }
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAddExamination,
                            "Server is down!",
                            "Server down",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                    //System.exit(1);
                }
                return true;
            }
    
}
