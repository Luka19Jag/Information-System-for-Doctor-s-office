/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.view.validation;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ps.view.form.FrmRegister;
import ps.view.util.FormMode;

/**
 *
 * @author Zbook G3
 */
public class ValidateRegisterDoctor implements ValidationRegisterDoctor{

    @Override
    public void validate(FrmRegister frmRegister, String pattern, String variable) {
        switch (variable) {
            case "Firstname":
                name(frmRegister, pattern);
                break;
            case "Lastname":
                lastname(frmRegister, pattern);
                break;
            case "Email":
                email(frmRegister, pattern);
                break;
            case "Address":
                address(frmRegister, pattern);
                break;
            case "Password":
                password(frmRegister, pattern);
                break;
            case "ConfirmPassword":
                confirmPassword(frmRegister, pattern);
                break;
            case "NameClick":
                nameClick(frmRegister);
                break;
            case "LastnameClick":
                lastnameClick(frmRegister);
                break;
            case "EmailClick":
                emailClick(frmRegister);
                break;
            case "AddressClick":
                addressClick(frmRegister);
                break;
            case "PasswordClick":
                passwordClick(frmRegister);
                break;
            case "ConfirmPasswordClick":
                confirmPasswordClick(frmRegister);
                break;
            default: System.out.println("Validate switch doesn't work!");
        }
    }

    private void name(FrmRegister frmRegister, String pattern) {
        if (frmRegister.getTxtName().getText() == null || 
                frmRegister.getTxtName().getText().trim().isEmpty()) {
            frmRegister.getTxtName().setText("Name shouldn't be empty!");
            frmRegister.getTxtName().setForeground(Color.red);
        }       
        else {
            Pattern p = Pattern.compile(pattern);
            Matcher m;
            if (!frmRegister.getTxtName().getText().equals("Name shouldn't be empty!")) {
                m = p.matcher(frmRegister.getTxtName().getText().trim());
                boolean b = m.find();
                if (b) {
                    frmRegister.getTxtName().setText("Name must have only letters!");
                    frmRegister.getTxtName().setForeground(Color.red);
                }
            }
        }
    }

    private void lastname(FrmRegister frmRegister, String pattern) {
        if (frmRegister.getTxtLastname() == null ||
                frmRegister.getTxtLastname().getText().trim().isEmpty()) {
                frmRegister.getTxtLastname().setText("Lastname shouldn't be empty");
                frmRegister.getTxtLastname().setForeground(Color.red);
            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmRegister.getTxtLastname().getText().equals("Lastname shouldn't be empty")) {
                    m = p.matcher(frmRegister.getTxtLastname().getText().trim());
                    boolean b = m.find();
                    if (b) {
                        frmRegister.getTxtLastname().setText("Lastname must have only letters");
                        frmRegister.getTxtLastname().setForeground(Color.red);
                }
            }
            }
    }

    private void email(FrmRegister frmRegister, String pattern) {
        if (frmRegister.getTxtEmail() == null || frmRegister.getTxtEmail().getText().trim().isEmpty()) {
                frmRegister.getTxtEmail().setText("Email shouldn't be empty!");
                frmRegister.getTxtEmail().setForeground(Color.red);
            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmRegister.getTxtEmail().getText().equals("Email shouldn't be empty!")) {
                    m = p.matcher(frmRegister.getTxtEmail().getText().trim());
                    boolean b = m.find();
                    if (!b) {
                        frmRegister.getTxtEmail().setText("Email should be in valid form!");
                        frmRegister.getTxtEmail().setForeground(Color.red);
                    }
                }
            }
    }

    private void address(FrmRegister frmRegister, String pattern) {
        if (frmRegister.getTxtAddress() == null || frmRegister.getTxtAddress().getText().trim().isEmpty()) {
                frmRegister.getTxtAddress().setText("Address shouldn't be empty!");
                frmRegister.getTxtAddress().setForeground(Color.red);
            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmRegister.getTxtAddress().getText().equals("Address shouldn't be empty!")) {
                    m = p.matcher(frmRegister.getTxtAddress().getText().trim());
                    boolean b = m.find();
                    if (b) {
                        frmRegister.getTxtAddress().setText("Address must have only letters and numbers!");
                        frmRegister.getTxtAddress().setForeground(Color.red);
                    }
                }
            }
    }
    
    private void password(FrmRegister frmRegister, String pattern) {
        if (frmRegister.getTxtPassword() == null || frmRegister.getTxtPassword().getText().trim().isEmpty()) {
                frmRegister.getLblPasswordWarning().setText("Password shouldn't be empty!");
                frmRegister.getLblPasswordWarning().setForeground(Color.red);
            }else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmRegister.getLblPasswordWarning().getText().equals("Password shouldn't be empty!")) {
                    m = p.matcher(frmRegister.getTxtPassword().getText().trim());
                    boolean b = m.find();
                    if (b) {
                        frmRegister.getLblPasswordWarning().setText("Password must have only letters and numbers!");
                        frmRegister.getLblPasswordWarning().setForeground(Color.red);                        
                    }
                }
            }
    }
    
    private void confirmPassword(FrmRegister frmRegister, String pattern) {
        if (frmRegister.getTxtConfirmPassword()== null || frmRegister.getTxtConfirmPassword().getText().trim().isEmpty()) {
                frmRegister.getLblConfirmPasswordWarning().setText("Confirm password shouldn't be empty!");
                frmRegister.getLblConfirmPasswordWarning().setForeground(Color.red);                
            }else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmRegister.getLblConfirmPasswordWarning().getText().equals("Confirm password shouldn't be empty!")) {
                    m = p.matcher(frmRegister.getTxtConfirmPassword().getText().trim());
                    boolean b = m.find();
                    if (b) {
                        frmRegister.getLblConfirmPasswordWarning().setText("Confirm password must have only letters and numbers!");
                        frmRegister.getLblConfirmPasswordWarning().setForeground(Color.red);                        
                    }
                }
            }
    }

    private void nameClick(FrmRegister frmRegister) {
        if (frmRegister.getTxtName().getText().equals("Name must have only letters!")
                    || frmRegister.getTxtName().getText().equals("Name shouldn't be empty!")) {
                frmRegister.getTxtName().setText("");
                frmRegister.getTxtName().setForeground(Color.black);
            }
    }

    private void lastnameClick(FrmRegister frmRegister) {
        if (frmRegister.getTxtLastname().getText().equals("Lastname must have only letters")
                    || frmRegister.getTxtLastname().getText().equals("Lastname shouldn't be empty")) {
                frmRegister.getTxtLastname().setText("");
                frmRegister.getTxtLastname().setForeground(Color.black);
            }
    }

    private void emailClick(FrmRegister frmRegister) {
        if (frmRegister.getTxtEmail().getText().equals("Email should be in valid form!")
                    || frmRegister.getTxtEmail().getText().equals("Email shouldn't be empty!")) {
                frmRegister.getTxtEmail().setText("");
                frmRegister.getTxtEmail().setForeground(Color.black);
            }
    }

    private void addressClick(FrmRegister frmRegister) {
        if (frmRegister.getTxtAddress().getText().equals("Address must have only letters and numbers!")
                    || frmRegister.getTxtAddress().getText().equals("Address shouldn't be empty!")) {
                frmRegister.getTxtAddress().setText("");
                frmRegister.getTxtAddress().setForeground(Color.black);
            }
    }
    
    private void passwordClick(FrmRegister frmRegister) {
        if (frmRegister.getLblPasswordWarning().getText().equals("Password must have only letters and numbers!")
                    || frmRegister.getLblPasswordWarning().getText().equals("Password shouldn't be empty!")) {
                frmRegister.getTxtPassword().setText("");
                frmRegister.getTxtPassword().setForeground(Color.black);
                frmRegister.getLblPasswordWarning().setText("");
                frmRegister.getLblPasswordWarning().setForeground(Color.black);
            }
    }

    private void confirmPasswordClick(FrmRegister frmRegister) {
        if (frmRegister.getLblConfirmPasswordWarning().getText().equals("Confirm password must have only letters and numbers!")
                    || frmRegister.getLblConfirmPasswordWarning().getText().equals("Confirm password shouldn't be empty!")) {
                frmRegister.getTxtConfirmPassword().setText("");
                frmRegister.getTxtConfirmPassword().setForeground(Color.black);
                frmRegister.getLblConfirmPasswordWarning().setText("");
                frmRegister.getLblConfirmPasswordWarning().setForeground(Color.black);
            }
    }
    
}
