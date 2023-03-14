/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import ps.communication.Communication;
import ps.constants.Constant;
import ps.domain.Doktor;
import ps.view.cordinator.MainCordinator;
import ps.view.form.FrmRegister;
import ps.view.util.FormMode;
import ps.view.validation.Validate;
import ps.view.validation.ValidateRegisterDoctor;

/**
 *
 * @author Zbook G3
 */
public class RegisterController {
    
    private final FrmRegister frmRegister;
    Doktor doctorMain;
    FormMode formMode;
    
    public RegisterController(FrmRegister frmRegister) {
        this.frmRegister = frmRegister;
        prepareView();
        addActionListenerText();
        addActionListener();
    }
    
    public void openForm() {
        frmRegister.setVisible(true);
        frmRegister.setTitle("New user registration");
    }
    
    public void openF(FormMode formMode) {
        frmRegister.setVisible(true);
        this.formMode = formMode;
        
        switch(formMode){
            case FORM_ADD: 
                formAdd();
                break;
            case FORM_EDIT:
                formEdit();
                break;
        }
    }

    private void addActionListener() {
        frmRegister.btnRegisterAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateForm();
                registerUser(e);
            }

            private void registerUser(ActionEvent e) {
                
                if(frmRegister.getTxtName().getText().equals("Name must have only letters!") ||
                        frmRegister.getTxtName().getText().equals("Name shouldn't be empty!") ||
                        frmRegister.getTxtLastname().getText().equals("Lastname must have only letters") ||
                        frmRegister.getTxtLastname().getText().equals("Lastname shouldn't be empty") ||
                        frmRegister.getTxtAddress().getText().equals("Address must have only letters and numbers!") ||
                        frmRegister.getTxtAddress().getText().equals("Address shouldn't be empty!") ||
                        frmRegister.getTxtEmail().getText().equals("Email shouldn't be empty!") ||
                        frmRegister.getTxtEmail().getText().equals("Email must have only letters!") ||
                        frmRegister.getLblPasswordWarning().getText().equals("Password must have only letters and numbers!") ||
                        frmRegister.getLblConfirmPasswordWarning().getText().equals("Confirm password must have only letters and numbers!") ||
                        frmRegister.getLblPasswordWarning().getText().equals("Password shouldn't be empty!") ||
                        frmRegister.getLblConfirmPasswordWarning().getText().equals("Confirm password shouldn't be empty!")){
                
                    JOptionPane.showMessageDialog(
                        null,
                        "You didn't fill the blanks crrectly!",
                        "Filling the blanks error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                    
                }              
                
                
                try {
                    String name = frmRegister.getTxtName().getText().trim();
                    String lastName = frmRegister.getTxtLastname().getText().trim();
                    String email = frmRegister.getTxtEmail().getText().trim();
                    String password = String.copyValueOf(frmRegister.getTxtPassword().getPassword());
                    String address = frmRegister.getTxtAddress().getText().trim();
                    Date dateOfBirth = frmRegister.getjCalDateOfBirth().getDate(); 
                    
                    if(!String.copyValueOf(frmRegister.getTxtPassword().getPassword()).equals(String.copyValueOf(frmRegister.getTxtConfirmPassword().getPassword()))){
                        JOptionPane.showMessageDialog(frmRegister,
                        "Passwords doesn't match!",
                        "Password error" ,
                        JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if(dateOfBirth.after(new Date())) {
                        JOptionPane.showMessageDialog(
                        null,
                        "The date can't be in the future!",
                        "Date error",
                        JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                        Doktor d = new Doktor(name, name, email, password, dateOfBirth, address);
                        List<Doktor> allDoctors = Communication.getInstance().getAllDoctors(new Doktor());
                        
                        boolean changed = false;
                        
                        for(Doktor doc : allDoctors)
                            if(doc.getEmail().equals(d.getEmail())){
                                changed = true;
                            }
                        
                        if(changed == true){
                            JOptionPane.showMessageDialog(
                                null,
                                "There can't be two the same email addresses\n"
                                        + "Please, enter other email address!",
                                "Email error",
                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }
                        
                        Doktor doktor = Communication.getInstance().register(name, lastName, email, password, dateOfBirth ,address);
                        
                        JOptionPane.showMessageDialog(
                                frmRegister,
                                "The new doctor is successufully registred",
                                doktor.toString(),
                                JOptionPane.INFORMATION_MESSAGE
                        );
                       
                        frmRegister.dispose();
                        MainCordinator.getInstance().addParam(Constant.REGISTERED_USER, doktor);
                        MainCordinator.getInstance().getMainContoller().registred = true;
                        MainCordinator.getInstance().openMainForm();
                        String receptient = doktor.getEmail();
                        String description = "Dear " + doktor.toString() + ", \n\n"
                                + "you have become a part of office LukaEye!\n"
                                + "We are very glad we have opportunity to cooperate with you. \n\n"
                                + "Yours sincerely, \n"
                                + "LukaEye";
                        MainCordinator.getInstance().sendEmail(receptient, description);
                    //}
                    //else throw new Exception();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmRegister,
                            ex.getMessage(),
                            "Error : The new doctor is not successufully registred",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frmRegister.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                validateLastname();
                validateAddress();
                validateEmail();
                validatePassword();
                validateConfirmPassword();
                editUser();
            }

            private void editUser() {
                if(
                        frmRegister.getTxtLastname().getText().equals("Lastname must have only letters") ||
                        frmRegister.getTxtLastname().getText().equals("Lastname shouldn't be empty") ||
                        frmRegister.getTxtAddress().getText().equals("Address must have only letters and numbers!") ||
                        frmRegister.getTxtAddress().getText().equals("Address shouldn't be empty!") ||
                        frmRegister.getTxtEmail().getText().equals("Email shouldn't be empty!") ||
                        frmRegister.getTxtEmail().getText().equals("Email must have only letters!") ||
                        frmRegister.getLblPasswordWarning().getText().equals("Password must have only letters and numbers!") ||
                        frmRegister.getLblConfirmPasswordWarning().getText().equals("Confirm password must have only letters and numbers!") ||
                        frmRegister.getLblPasswordWarning().getText().equals("Password shouldn't be empty!") ||
                        frmRegister.getLblConfirmPasswordWarning().getText().equals("Confirm password shouldn't be empty!")){
                
                    JOptionPane.showMessageDialog(
                        null,
                        "You didn't fill the blanks crrectly!",
                        "Filling the blanks error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                    
                }  
                
                try {
                    String name = frmRegister.getTxtName().getText().trim();
                    String lastName = frmRegister.getTxtLastname().getText().trim();
                    String email = frmRegister.getTxtEmail().getText().trim();
                    String password = String.copyValueOf(frmRegister.getTxtPassword().getPassword());
                    String address = frmRegister.getTxtAddress().getText().trim();
                    Date dateOfBirth = frmRegister.getjCalDateOfBirth().getDate(); 
                    
                    if(!String.copyValueOf(frmRegister.getTxtPassword().getPassword()).equals(String.copyValueOf(frmRegister.getTxtConfirmPassword().getPassword()))){
                        JOptionPane.showMessageDialog(frmRegister,
                        "Passwords doesn't match!",
                        "Password error" ,
                        JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    
                        Doktor d = new Doktor(name, lastName, email, password, dateOfBirth, address);
                        List<Doktor> allDoctors = Communication.getInstance().getAllDoctors(new Doktor());
                        
                        boolean changed = false;
                        
                        for(Doktor doc : allDoctors)
                            if(doc.getEmail().equals(d.getEmail())){
                                if(!doc.getId().equals(doctorMain.getId())){
                                    changed = true;
                                }
                            }
                        
                        if(changed == true){
                            JOptionPane.showMessageDialog(
                                null,
                                "There can't be two the same email addresses\n"
                                        + "Please, enter other email address!",
                                "Email error",
                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }
                        
                        int reply = JOptionPane.showConfirmDialog(
                            frmRegister,
                            "Are you sure you want to edit tha doctor?",
                            "Confirm editing the doctor",
                            JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.NO_OPTION) {
                            return;
                        }
                        
                        //Doktor doktor = Communication.getInstance().register(name, lastName, email, password, dateOfBirth ,address);
                        doctorMain.setPrezime(d.getPrezime());
                        doctorMain.setAdresa(d.getAdresa());
                        doctorMain.setPassword(d.getPassword());
                        doctorMain.setEmail(d.getEmail());
                        
                        
                        
                        Communication.getInstance().editDoctor(doctorMain);
                        
                        JOptionPane.showMessageDialog(
                                frmRegister,
                                "The doctor has been edited and saved!",
                                "Edited doctor",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                       
                        
                        //frmRegister.dispose();
                        
                        
                        //MainCordinator.getInstance().openMainForm();
                    //}
                    //else throw new Exception();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmRegister,
                            "The new doctor is not successufully registred",
                            "Error editing doctor ",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        
        frmRegister.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cancel();
            }

            private void cancel() {
                frmRegister.dispose();
                if(formMode == FormMode.FORM_EDIT){
                    MainCordinator.getInstance().openMainForm();
                    doctorMain = null;
                }
                else
                    MainCordinator.getInstance().openLoginForm();
            }
        });
        
        frmRegister.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                frmRegister.dispose();
                MainCordinator.getInstance().openMainForm();
            }
        });
    }

    private void prepareView() {
        //frmRegister.setTitle("Register");
        //frmRegister.setLocationRelativeTo(null);
        //frmRegister.setResizable(false);
    }
    
    public void validateForm(){
        /*if(!String.copyValueOf(frmRegister.getTxtPassword().getPassword()).equals(String.copyValueOf(frmRegister.getTxtConfirmPassword().getPassword()))){
            JOptionPane.showMessageDialog(frmRegister,
                    "Passwords doesn't match!",
                    "Password error" ,
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;*/
        
        validateName();
        validateLastname();
        validateAddress();
        validateEmail();
        validatePassword();
        validateConfirmPassword();
        
    }
    
    public void resetForm(){
        frmRegister.getTxtName().setText("");
        frmRegister.getTxtLastname().setText("");
        frmRegister.getTxtEmail().setText("");
        frmRegister.getTxtPassword().setText("");
        frmRegister.getTxtConfirmPassword().setText("");
        frmRegister.getTxtAddress().setText("");
    }

    private void addActionListenerText() {
        frmRegister.getTxtName().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ValidateRegisterDoctor().validate(frmRegister, "", "NameClick");
            }
        });

        frmRegister.getTxtLastname().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ValidateRegisterDoctor().validate(frmRegister, "", "LastnameClick");
            }
        });

        frmRegister.getTxtEmail().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ValidateRegisterDoctor().validate(frmRegister, "", "EmailClick");
            }
        });

        frmRegister.getTxtAddress().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ValidateRegisterDoctor().validate(frmRegister, "", "AddressClick");
            }
        });
        
        frmRegister.getTxtPassword().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ValidateRegisterDoctor().validate(frmRegister, "", "PasswordClick");
            }
        });
        
        frmRegister.getTxtConfirmPassword().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ValidateRegisterDoctor().validate(frmRegister, "", "ConfirmPasswordClick");
            }
        });
        
    }

    private void validateName() {
        new ValidateRegisterDoctor().validate(frmRegister, "[^A-Za-z\\s]", "Firstname");
    }

    private void validateLastname() {
        new ValidateRegisterDoctor().validate(frmRegister, "[^A-Za-z\\s]", "Lastname");
    }

    private void validateAddress() {
        new ValidateRegisterDoctor().validate(frmRegister, "[^A-Za-z0-9\\s/]", "Address");
    }

    private void validateEmail() {
        new ValidateRegisterDoctor().validate(frmRegister, "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", "Email");
    }

    private void validatePassword() {
        new ValidateRegisterDoctor().validate(frmRegister, "[^A-Za-z0-9]", "Password");
    }

    private void validateConfirmPassword() {
        new ValidateRegisterDoctor().validate(frmRegister, "[^A-Za-z0-9]", "ConfirmPassword");
    }

    private void formAdd() {
        frmRegister.getBtnEdit().setVisible(false);
        frmRegister.getBtnRegister().setVisible(true);
        frmRegister.getTxtName().setEditable(true);
        frmRegister.getjCalDateOfBirth().setEnabled(true);
        frmRegister.setTitle("New user registration");        
        frmRegister.setLocationRelativeTo(null);
        frmRegister.setResizable(false);
    }

    private void formEdit() {
        prepareForm();
        frmRegister.getBtnEdit().setVisible(true);
        frmRegister.getBtnRegister().setVisible(false);
        frmRegister.getTxtName().setEditable(false);
        frmRegister.getjCalDateOfBirth().setEnabled(false);
        frmRegister.setTitle("Edit doctor");        
        frmRegister.setLocationRelativeTo(null);
        frmRegister.setResizable(false);
    }

    private void prepareForm() {
        if (MainCordinator.getInstance().getMainContoller().registred) {
            doctorMain = (Doktor)MainCordinator.getInstance().getParam("REGISTERED_USER");
        } else {
            doctorMain = (Doktor)MainCordinator.getInstance().getParam("CURRENT_USER");
        }
        frmRegister.getTxtName().setText(doctorMain.getIme());
        frmRegister.getTxtLastname().setText(doctorMain.getPrezime());
        frmRegister.getTxtAddress().setText(doctorMain.getAdresa());
        frmRegister.getTxtEmail().setText(doctorMain.getEmail());
        frmRegister.getTxtPassword().setText(doctorMain.getPassword());
        frmRegister.getTxtConfirmPassword().setText(doctorMain.getPassword());
        frmRegister.getjCalDateOfBirth().setDate(doctorMain.getDatumRodjenja());
    }
    
}
