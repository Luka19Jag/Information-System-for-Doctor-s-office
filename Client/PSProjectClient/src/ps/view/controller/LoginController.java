/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ps.communication.Communication;
import ps.constants.Constant;
import ps.domain.Doktor;
import ps.view.cordinator.MainCordinator;
import ps.view.form.FrmLogin;
import ps.view.util.FormMode;

/**
 *
 * @author Zbook G3
 */
public class LoginController {
    
    private final FrmLogin frmLogin;

    public LoginController(FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        prepareView();
        addActionListenerShowPassword();
        addActionListenerLogin();
        addActionListenerRegister();
    }
    
    public void openForm(){
        this.frmLogin.setVisible(true);
    }
    
    private void addActionListenerLogin(){
        frmLogin.btnLoginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                loginUser();
            }

            private void loginUser() {
                try {
                    String email = frmLogin.getTxtEmail().getText().trim();
                    String password = String.valueOf(frmLogin.getTxtPassword().getPassword());
                    
                    Doktor doktor = Communication.getInstance().login(email,password);
                    
                    JOptionPane.showMessageDialog(
                            frmLogin,
                            "The system has found the doctor",
                            "Login : " + doktor.getIme()+ " " + doktor.getPrezime(),
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    frmLogin.dispose();
                    
                    MainCordinator.getInstance().addParam(Constant.CURRENT_USER, doktor);
                    MainCordinator.getInstance().openMainForm();
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmLogin,
                            "The system has not found the doctor!",
                            "Login error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    private void addActionListenerRegister(){
        frmLogin.btnRegisterAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                frmLogin.dispose();
                MainCordinator.getInstance().openRegisterForm(FormMode.FORM_ADD);
            }
        });
    }
    
    private void prepareView() {
        frmLogin.setTitle("Login");
        frmLogin.setLocationRelativeTo(null);
    }

    private void addActionListenerShowPassword() {
        
        frmLogin.jcbShowPasswordAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(frmLogin.getJcbShowPassword().isSelected()){
                    frmLogin.getTxtPassword().setEchoChar((char)0);
                }else {
                    frmLogin.getTxtPassword().setEchoChar('•');
                }
                    
            }
        });
    }
     
    
}
