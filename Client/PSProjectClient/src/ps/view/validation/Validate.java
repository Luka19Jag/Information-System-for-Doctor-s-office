/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.view.validation;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ps.constants.Constant;
import ps.domain.Pacijent;
import ps.view.cordinator.MainCordinator;
import ps.view.form.FrmNewPatient;
import ps.view.util.FormMode;

/**
 *
 * @author Zbook G3
 */
public class Validate implements Validation{

    @Override
    public void validate(FrmNewPatient frmNewPatient, String pattern, String variable) {
        switch (variable) {
            case "Firstname":
                name(frmNewPatient, pattern);
                break;
            case "Lastname":
                lastname(frmNewPatient, pattern);
                break;
            case "Email":
                email(frmNewPatient, pattern);
                break;
            case "Address":
                address(frmNewPatient, pattern);
                break;
            case "NameClick":
                nameClick(frmNewPatient);
                break;
            case "LastnameClick":
                lastnameClick(frmNewPatient);
                break;
            case "EmailClick":
                emailClick(frmNewPatient);
                break;
            case "AddressClick":
                addressClick(frmNewPatient);
                break;
            default: System.out.println("Validate switch doesn't work!");
        }
    }

    @Override
    public void formModeSetter(FrmNewPatient frmNewPatient, FormMode formMode) {
        switch(formMode){
            case FORM_ADD:
                frmNewPatient.getBtnCancel().setVisible(true);
                frmNewPatient.getBtnDelete().setVisible(false);
                frmNewPatient.getBtnEdit().setVisible(false);
                frmNewPatient.getBtnEnableChange().setVisible(false);
                frmNewPatient.getBtnSave().setVisible(true);

                frmNewPatient.getTxtName().setEnabled(true);
                frmNewPatient.getTxtLastname().setEnabled(true);
                frmNewPatient.getTxtEmail().setEnabled(true);
                frmNewPatient.getTxtAddress().setEnabled(true);
                frmNewPatient.getTxtId().setVisible(false);
                frmNewPatient.getLblId().setVisible(false);
                break;
            case FORM_VIEW:
                frmNewPatient.getBtnCancel().setVisible(true);
                frmNewPatient.getBtnDelete().setVisible(true);
                frmNewPatient.getBtnEdit().setVisible(false);
                frmNewPatient.getBtnEnableChange().setVisible(true);
                frmNewPatient.getBtnSave().setVisible(false);

                //zabrani izmenu vrednosti
                frmNewPatient.getTxtName().setEnabled(false);
                frmNewPatient.getTxtLastname().setEnabled(false);
                frmNewPatient.getTxtEmail().setEnabled(false);
                frmNewPatient.getTxtAddress().setEnabled(false);
                frmNewPatient.getjCalDateOfBirth().setEnabled(false);
                frmNewPatient.getTxtId().setVisible(false);
                frmNewPatient.getLblId().setVisible(false);

                //get patient
                Pacijent patient = (Pacijent) MainCordinator.getInstance().getParam(Constant.PARAM_PATIENT);
                frmNewPatient.getTxtName().setText(patient.getIme());
                frmNewPatient.getTxtLastname().setText(patient.getPrezime());
                frmNewPatient.getTxtEmail().setText(patient.getEmail());
                frmNewPatient.getTxtAddress().setText(patient.getAdresa());
                frmNewPatient.getjCalDateOfBirth().setDate(patient.getDatumRodjenja());
                frmNewPatient.getTxtId().setText(String.valueOf(patient.getId()));
                break;
            case FORM_EDIT:
                frmNewPatient.getBtnCancel().setVisible(true);
                frmNewPatient.getBtnDelete().setVisible(false);
                frmNewPatient.getBtnEdit().setVisible(true);
                frmNewPatient.getBtnEnableChange().setVisible(false);
                frmNewPatient.getBtnSave().setVisible(false);

                //zabrani izmenu vrednosti
                frmNewPatient.getTxtName().setEnabled(true);
                frmNewPatient.getTxtLastname().setEnabled(true);
                frmNewPatient.getTxtEmail().setEnabled(false);
                frmNewPatient.getTxtAddress().setEnabled(true);
                frmNewPatient.getjCalDateOfBirth().setEnabled(true);
                frmNewPatient.getTxtId().setVisible(false);
                frmNewPatient.getLblId().setVisible(false);
                break;
        }
    }

    private void name(FrmNewPatient frmNewPatient, String pattern) {
        if (frmNewPatient.getTxtName().getText() == null || 
                frmNewPatient.getTxtName().getText().trim().isEmpty()) {
            frmNewPatient.getTxtName().setText("Name shouldn't be empty!");
            frmNewPatient.getTxtName().setForeground(Color.red);
        }       
        else {
            Pattern p = Pattern.compile(pattern);
            Matcher m;
            if (!frmNewPatient.getTxtName().getText().equals("Name shouldn't be empty!")) {
                m = p.matcher(frmNewPatient.getTxtName().getText().trim());
                boolean b = m.find();
                if (b) {
                    frmNewPatient.getTxtName().setText("Name must have only letters!");
                    frmNewPatient.getTxtName().setForeground(Color.red);
                }
            }
        }
    }

    private void lastname(FrmNewPatient frmNewPatient, String pattern) {
        if (frmNewPatient.getTxtLastname() == null ||
                frmNewPatient.getTxtLastname().getText().trim().isEmpty()) {
                frmNewPatient.getTxtLastname().setText("Lastname shouldn't be empty");
                frmNewPatient.getTxtLastname().setForeground(Color.red);
            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmNewPatient.getTxtLastname().getText().equals("Lastname shouldn't be empty")) {
                    m = p.matcher(frmNewPatient.getTxtLastname().getText().trim());
                    boolean b = m.find();
                    if (b) {
                        frmNewPatient.getTxtLastname().setText("Lastname must have only letters");
                        frmNewPatient.getTxtLastname().setForeground(Color.red);
                }
            }
            }
    }

    private void email(FrmNewPatient frmNewPatient, String pattern) {
        if (frmNewPatient.getTxtEmail() == null || frmNewPatient.getTxtEmail().getText().trim().isEmpty()) {
                frmNewPatient.getTxtEmail().setText("Email shouldn't be empty!");
                frmNewPatient.getTxtEmail().setForeground(Color.red);
            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmNewPatient.getTxtEmail().getText().equals("Email shouldn't be empty!")) {
                    m = p.matcher(frmNewPatient.getTxtEmail().getText().trim());
                    boolean b = m.find();
                    if (!b) {
                        frmNewPatient.getTxtEmail().setText("Email should be in valid form!");
                        frmNewPatient.getTxtEmail().setForeground(Color.red);
                    }
                }
            }
    }

    private void address(FrmNewPatient frmNewPatient, String pattern) {
        if (frmNewPatient.getTxtAddress() == null || frmNewPatient.getTxtAddress().getText().trim().isEmpty()) {
                frmNewPatient.getTxtAddress().setText("Address shouldn't be empty!");
                frmNewPatient.getTxtAddress().setForeground(Color.red);
            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmNewPatient.getTxtAddress().getText().equals("Address shouldn't be empty!")) {
                    m = p.matcher(frmNewPatient.getTxtAddress().getText().trim());
                    boolean b = m.find();
                    if (b) {
                        frmNewPatient.getTxtAddress().setText("Address must have only letters and numbers!");
                        frmNewPatient.getTxtAddress().setForeground(Color.red);
                    }
                }
            }
    }

    private void nameClick(FrmNewPatient frmNewPatient) {
        if (frmNewPatient.getTxtName().getText().equals("Name must have only letters!")
                    || frmNewPatient.getTxtName().getText().equals("Name shouldn't be empty!")) {
                frmNewPatient.getTxtName().setText("");
                frmNewPatient.getTxtName().setForeground(Color.black);
            }
    }

    private void lastnameClick(FrmNewPatient frmNewPatient) {
        if (frmNewPatient.getTxtLastname().getText().equals("Lastname must have only letters")
                    || frmNewPatient.getTxtLastname().getText().equals("Lastname shouldn't be empty")) {
                frmNewPatient.getTxtLastname().setText("");
                frmNewPatient.getTxtLastname().setForeground(Color.black);
            }
    }

    private void emailClick(FrmNewPatient frmNewPatient) {
        if (frmNewPatient.getTxtEmail().getText().equals("Email should be in valid form!")
                    || frmNewPatient.getTxtEmail().getText().equals("Email shouldn't be empty!")) {
                frmNewPatient.getTxtEmail().setText("");
                frmNewPatient.getTxtEmail().setForeground(Color.black);
            }
    }

    private void addressClick(FrmNewPatient frmNewPatient) {
        if (frmNewPatient.getTxtAddress().getText().equals("Address must have only letters and numbers!")
                    || frmNewPatient.getTxtAddress().getText().equals("Address shouldn't be empty!")) {
                frmNewPatient.getTxtAddress().setText("");
                frmNewPatient.getTxtAddress().setForeground(Color.black);
            }
    }
    
}

    