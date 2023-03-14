/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.view.cordinator;

import java.awt.Frame;
import java.util.HashMap;
import java.util.Map;
import ps.domain.Doktor;
import ps.view.controller.AddExaminationController;
import ps.view.controller.LoginController;
import ps.view.controller.MainController;
import ps.view.controller.NewPatientController;
import ps.view.controller.RegisterController;
import ps.view.controller.ScheduledTerminsController;
import ps.view.controller.ViewAllExaminationsController;
import ps.view.controller.ViewPatientsController;
import ps.view.form.FrmAddExamination;
import ps.view.form.FrmLogin;
import ps.view.form.FrmMain;
import ps.view.form.FrmNewPatient;
import ps.view.form.FrmRegister;
import ps.view.form.FrmScheduledTermins;
import ps.view.form.FrmViewAllExaminations;
import ps.view.form.FrmViewPatients;
import ps.view.util.FormMode;

/**
 *
 * @author Zbook G3
 */
public class MainCordinator {
    
    private static MainCordinator instance;
    
    private final MainController mainContoller;
    
    private final Map<String, Object> params;
    
    private MainCordinator() {
        mainContoller = new MainController(new FrmMain());
        params = new HashMap<>();
    }
    
    public static MainCordinator getInstance(){
        if(instance == null)
            instance = new MainCordinator();
        return instance;
    }
    
    public void openLoginForm() {
        LoginController loginController = new LoginController(new FrmLogin());
        loginController.openForm();
    }
    
    public void openRegisterForm(FormMode formMode){
        //FrmRegister frmRegister = new FrmRegister((Frame) mainContoller.getFrmMain(), true);
        FrmRegister frmRegister = new FrmRegister();
        RegisterController registerController = new RegisterController(frmRegister);
        registerController.openF(formMode);
    }

    public void addParam(String name, Object key) {
        params.put(name, key);
    }
    
    public Object getParam(String name){
        return params.get(name);
    }

    public void openMainForm() {
        mainContoller.openForm();
    }

    public MainController getMainContoller() {
        return mainContoller;
    }

    public void openAddNewPatientForm() {
        NewPatientController patientController =
                new NewPatientController(new FrmNewPatient((Frame) mainContoller.getFrmMain(), true));
        patientController.openForm(FormMode.FORM_ADD);
    }

    public void openPatientDetailsPatientForm() {
        FrmNewPatient patientDetails = new FrmNewPatient((Frame) mainContoller.getFrmMain(), true);
        NewPatientController newPatientController = new NewPatientController(patientDetails);
        newPatientController.openForm(FormMode.FORM_VIEW);
    }

    public void openFrmViewPatients(FormMode formMode, AddExaminationController addExaminationController) {
        FrmViewPatients form = null;
        if(formMode == FormMode.FORM_ADD){
            form = new FrmViewPatients((Frame) mainContoller.getFrmMain(),  true);;
            ViewPatientsController viewPatientsController = new ViewPatientsController(form, addExaminationController);
            viewPatientsController.openF(formMode);
        }
        else{
            form = new FrmViewPatients((Frame) mainContoller.getFrmMain(), true);
            ViewPatientsController viewPatientsController = new ViewPatientsController(form);
            viewPatientsController.openF(formMode);
        }
    }

    public void openViewAllExaminations() {
        ViewAllExaminationsController viewAllExaminations = new ViewAllExaminationsController(
            new FrmViewAllExaminations((Frame) mainContoller.getFrmMain(), true));
        viewAllExaminations.openForm(FormMode.FORM_VIEW);
    }

    public void openAddExamination() {
        AddExaminationController addExaminationController = new AddExaminationController(
                new FrmAddExamination((Frame) mainContoller.getFrmMain(), true));
        addExaminationController.openForm(FormMode.FORM_ADD);
    }

    public void openExaminationDetailsForm() {
        AddExaminationController addExaminationController = new AddExaminationController(new FrmAddExamination((Frame) mainContoller.getFrmMain(), true));
        addExaminationController.openForm(FormMode.FORM_VIEW);
    }

    public void openScheduledTerminsForm() {
        ScheduledTerminsController scheduledTerminsController = new ScheduledTerminsController(new FrmScheduledTermins((Frame) mainContoller.getFrmMain(), true));
        scheduledTerminsController.openForm(); 
    }

    public void sendEmail(String recepient, String description) {
        ps.EmailUtil.EmaiSender.sendMail(recepient, description);
    }
    
}
