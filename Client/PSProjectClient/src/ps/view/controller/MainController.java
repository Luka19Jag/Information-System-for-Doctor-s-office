/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.view.controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ps.communication.Communication;
import ps.constants.Constant;
import ps.domain.Doktor;
import ps.view.cordinator.MainCordinator;
import ps.view.form.FrmMain;
import ps.view.util.FormMode;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Zbook G3
 */
public class MainController {

    private final FrmMain frmMain;
    public boolean registred = false;

    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;
        prepareView();
        addActionListener();
    }

    public void openForm() {
        Doktor doktor;
        if(!registred)
            doktor = (Doktor) MainCordinator.getInstance().getParam(Constant.CURRENT_USER);
        else
            doktor = (Doktor) MainCordinator.getInstance().getParam(Constant.REGISTERED_USER);
        frmMain.getJmCurrentDoctor().setText(doktor.getIme() + " " + doktor.getPrezime());
        frmMain.setVisible(true);
    }

    private void prepareView() {
        //frmMain.setLocationRelativeTo(null);
        
        //frmMain.setAlwaysOnTop(true);
        frmMain.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //frmMain.setUndecorated(true);
        frmMain.getLbl1().setHorizontalAlignment(JLabel.CENTER);
        //frmMain.getLbl1().setPreferredSize(new Dimension(1000  , 1000));
        frmMain.setResizable(true);
        ImageIcon image1 = new ImageIcon("1.jpg");
        ImageIcon image2 = new ImageIcon("2.jpg");
        ImageIcon image3 = new ImageIcon("3.jpg");
        ImageIcon image4 = new ImageIcon("4.jpg");
        frmMain.getLbl1().setText("");
        //frmMain.getLbl1().setIcon(image2);
        //frmMain.getLbl2().setIcon(image2);
        //frmMain.getLbl3().setIcon(image3);
        //frmMain.getLbl4().setIcon(image4);
        /*Toolkit tlkt = Toolkit.getDefaultToolkit();
        int xsize = (int) tlkt.getScreenSize().getWidth();
        int ysize = (int) tlkt.getScreenSize().getHeight();
        this.frmMain.setSize(xsize, ysize);*/
    }

    private void addActionListener() {
        
        frmMain.jmiNewPatientAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jmiNewPatientAddActionPerformed(e);
            }

            private void jmiNewPatientAddActionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openAddNewPatientForm();
            }
        });
        
        frmMain.jmiShowPatientsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jmiShowPatientActionPerformed(e);
            }

            private void jmiShowPatientActionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openFrmViewPatients(FormMode.FORM_VIEW, null);
            }
        });
        
        
        frmMain.jmiViewAllExaminationsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openViewAllExaminations();
            }
        });
        
        frmMain.jmiAddExaminationActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openAddExamination();
            }
        });
        
        
        frmMain.jmiLogOutActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!MainCordinator.getInstance().getMainContoller().registred) {

                        int reply = JOptionPane.showConfirmDialog(frmMain,
                                MainCordinator.getInstance().getParam("CURRENT_USER") +  
                                        ", do you want to log out " + "?");
                        if(reply == JOptionPane.YES_OPTION){
                            Communication.getInstance().logOut(MainCordinator.getInstance().getParam("CURRENT_USER"));
                            
                            //MainCordinator.getInstance().openLoginForm();
                            frmMain.dispose();
                        }
                        //System.exit(1);
                    } else {

                        int reply = JOptionPane.showConfirmDialog(frmMain,
                                MainCordinator.getInstance().getParam("REGISTERED_USER") +
                                        ", do you want to log out " + "?");
                        if(reply == JOptionPane.YES_OPTION){
                            Communication.getInstance().logOut(MainCordinator.getInstance().getParam("REGISTERED_USER"));

                            //MainCordinator.getInstance().openLoginForm();
                            frmMain.dispose();
                        }
                        //System.exit(1);
                    }
                } catch (SocketException ex) {
                    //ex.printStackTrace();
                    //frmMain.dispose();
                    //MainCordinator.getInstance().openLoginForm();
                    
                } catch (Exception ex) {
                    
                    JOptionPane.showMessageDialog(frmMain,
                            "Server is down!",
                            "Server down",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            
            }
                
        });
        
        frmMain.jmiEditDoctorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Staaaa");
                MainCordinator.getInstance().openRegisterForm(FormMode.FORM_EDIT);
            }
        });
        
        frmMain.jmiEditInformationActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                MainCordinator.getInstance().openRegisterForm(FormMode.FORM_EDIT);
            }
        });
        
        
        frmMain.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                
                try {
                    if (!MainCordinator.getInstance().getMainContoller().registred) {

                        
                            Communication.getInstance().logOut(MainCordinator.getInstance().getParam("CURRENT_USER"));
                            
                            MainCordinator.getInstance().openLoginForm();
                            frmMain.dispose();
                        
                        //System.exit(1);
                    } else {

                        
                            Communication.getInstance().logOut(MainCordinator.getInstance().getParam("REGISTERED_USER"));

                            MainCordinator.getInstance().openLoginForm();
                            frmMain.dispose();
                        
                        //System.exit(1);
                    }
                } catch (SocketException ex) {
                    //ex.printStackTrace();
                    //frmMain.dispose();
                    //MainCordinator.getInstance().openLoginForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmMain,
                            "Server is down!",
                            "Server down",
                            JOptionPane.ERROR_MESSAGE);
                   // System.exit(1);
                }
            }
        });
    }

    public Component getFrmMain() {
        return frmMain;
    }
    
}
