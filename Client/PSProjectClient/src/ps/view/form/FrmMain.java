/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.view.form;

import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Zbook G3
 */
public class FrmMain extends javax.swing.JFrame {

    /**
     * Creates new form FrmMain
     */
    public FrmMain() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnAll = new javax.swing.JPanel();
        lbl1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmiEditInforamtion = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jmPatient = new javax.swing.JMenu();
        jmiNewPatient = new javax.swing.JMenuItem();
        jmiShowPatients = new javax.swing.JMenuItem();
        jmExamination = new javax.swing.JMenu();
        jmiAddExamination = new javax.swing.JMenuItem();
        jmiViewAllExaminations = new javax.swing.JMenuItem();
        jmAboutDoctor = new javax.swing.JMenu();
        jmiEditDoctor = new javax.swing.JMenuItem();
        jmLogOut = new javax.swing.JMenu();
        jmiLogOut = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbl1.setText("jLabel1");

        javax.swing.GroupLayout pnAllLayout = new javax.swing.GroupLayout(pnAll);
        pnAll.setLayout(pnAllLayout);
        pnAllLayout.setHorizontalGroup(
            pnAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAllLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnAllLayout.setVerticalGroup(
            pnAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAllLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jmiEditInforamtion.setText("Current doctor");

        jMenuItem1.setText("Edit information");
        jmiEditInforamtion.add(jMenuItem1);

        jMenuBar1.add(jmiEditInforamtion);

        jmPatient.setText("Patient");

        jmiNewPatient.setText("New patient");
        jmiNewPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiNewPatientActionPerformed(evt);
            }
        });
        jmPatient.add(jmiNewPatient);

        jmiShowPatients.setText("Show patients");
        jmPatient.add(jmiShowPatients);

        jMenuBar1.add(jmPatient);

        jmExamination.setText("Examination");

        jmiAddExamination.setText("Add examination");
        jmExamination.add(jmiAddExamination);

        jmiViewAllExaminations.setText("View all examinations");
        jmExamination.add(jmiViewAllExaminations);

        jMenuBar1.add(jmExamination);

        jmAboutDoctor.setText("About doctor");

        jmiEditDoctor.setText("jMenuItem2");
        jmAboutDoctor.add(jmiEditDoctor);

        jMenuBar1.add(jmAboutDoctor);

        jmLogOut.setText("LogOut");

        jmiLogOut.setText("LogOut");
        jmLogOut.add(jmiLogOut);

        jMenuBar1.add(jmLogOut);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnAll, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiNewPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiNewPatientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmiNewPatientActionPerformed

    public JMenuBar getjMenuBar1() {
        return jMenuBar1;
    }

    

    public JMenu getJmCurrentDoctor() {
        return jmiEditInforamtion;
    }

    public JMenu getJmExamination() {
        return jmExamination;
    }

    public JMenu getJmLogOut() {
        return jmLogOut;
    }

    public JMenu getJmPatient() {
        return jmPatient;
    }

    public JMenuItem getJmiLogOut() {
        return jmiLogOut;
    }

    public JMenuItem getJmiNewPatient() {
        return jmiNewPatient;
    }

    public JMenuItem getJmiViewAllExaminations() {
        return jmiViewAllExaminations;
    }

    public JMenuItem getJmiAddExamination() {
        return jmiAddExamination;
    }

    public JMenuItem getJmiShowPatients() {
        return jmiShowPatients;
    }

    public JPanel getPnAll() {
        return pnAll;
    }

    public void setjMenuBar1(JMenuBar jMenuBar1) {
        this.jMenuBar1 = jMenuBar1;
    }

    

    public void setJmCurrentDoctor(JMenu jmCurrentDoctor) {
        this.jmiEditInforamtion = jmCurrentDoctor;
    }

    public void setJmExamination(JMenu jmExamination) {
        this.jmExamination = jmExamination;
    }

    public void setJmLogOut(JMenu jmLogOut) {
        this.jmLogOut = jmLogOut;
    }

    public void setJmPatient(JMenu jmPatient) {
        this.jmPatient = jmPatient;
    }

    public void setJmiLogOut(JMenuItem jmiLogOut) {
        this.jmiLogOut = jmiLogOut;
    }

    public void setJmiNewPatient(JMenuItem jmiNewPatient) {
        this.jmiNewPatient = jmiNewPatient;
    }

    public void setJmiViewAllExaminations(JMenuItem jmiOrderExaminations) {
        this.jmiViewAllExaminations = jmiOrderExaminations;
    }

    public void setJmiAddExamination(JMenuItem jmiScheduleExamination) {
        this.jmiAddExamination = jmiScheduleExamination;
    }

    public void setJmiShowPatients(JMenuItem jmiShowPatients) {
        this.jmiShowPatients = jmiShowPatients;
    }

    public void setPnAll(JPanel pnAll) {
        this.pnAll = pnAll;
    }

    public JMenuItem getjMenuItem1() {
        return jMenuItem1;
    }

    public JMenu getJmAboutDoctor() {
        return jmAboutDoctor;
    }

    
    public JMenu getJmiEditInforamtion() {
        return jmiEditInforamtion;
    }

    

    public void setjMenuItem1(JMenuItem jMenuItem1) {
        this.jMenuItem1 = jMenuItem1;
    }

    public void setJmAboutDoctor(JMenu jmAboutDoctor) {
        this.jmAboutDoctor = jmAboutDoctor;
    }


    public void setJmiEditInforamtion(JMenu jmiEditInforamtion) {
        this.jmiEditInforamtion = jmiEditInforamtion;
    }

    public JMenuItem getJmiEditDoctor() {
        return jmiEditDoctor;
    }

    public JLabel getLbl1() {
        return lbl1;
    }

    /*public JLabel getLbl2() {
        return lbl2;
    }

    public JLabel getLbl3() {
        return lbl3;
    }

    public JLabel getLbl4() {
        return lbl4;
    }*/

    public void setJmiEditDoctor(JMenuItem jmiEditDoctor) {
        this.jmiEditDoctor = jmiEditDoctor;
    }

    public void setLbl1(JLabel lbl1) {
        this.lbl1 = lbl1;
    }

    /*public void setLbl2(JLabel lbl2) {
        this.lbl2 = lbl2;
    }

    public void setLbl3(JLabel lbl3) {
        this.lbl3 = lbl3;
    }

    public void setLbl4(JLabel lbl4) {
        this.lbl4 = lbl4;
    }*/
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenu jmAboutDoctor;
    private javax.swing.JMenu jmExamination;
    private javax.swing.JMenu jmLogOut;
    private javax.swing.JMenu jmPatient;
    private javax.swing.JMenuItem jmiAddExamination;
    private javax.swing.JMenuItem jmiEditDoctor;
    private javax.swing.JMenu jmiEditInforamtion;
    private javax.swing.JMenuItem jmiLogOut;
    private javax.swing.JMenuItem jmiNewPatient;
    private javax.swing.JMenuItem jmiShowPatients;
    private javax.swing.JMenuItem jmiViewAllExaminations;
    private javax.swing.JLabel lbl1;
    private javax.swing.JPanel pnAll;
    // End of variables declaration//GEN-END:variables

    public void jmiNewPatientAddActionListener(ActionListener actionListener) {
        jmiNewPatient.addActionListener(actionListener);
    }

    public void jmiShowPatientsActionListener(ActionListener actionListener) {
        jmiShowPatients.addActionListener(actionListener);
    }

    public void jmiAddExaminationActionListener(ActionListener actionListener) {
        jmiAddExamination.addActionListener(actionListener);
    }
    
    public void jmiViewAllExaminationsActionListener(ActionListener actionListener) {
        jmiViewAllExaminations.addActionListener(actionListener);
    }
    
    public void jmiLogOutActionListener(ActionListener actionListener) {
        jmiLogOut.addActionListener(actionListener);
    }

    public void jmiEditDoctorActionListener(ActionListener actionListener) {
        jmiEditDoctor.addActionListener(actionListener);
    }
    
    /*public void jmiLogOut2ActionListener(ActionListener actionListener) {
        jmiLogOut2.addActionListener(actionListener);
    }*/

    public void jmiEditInformationActionListener(ActionListener actionListener) {
        jmiEditInforamtion.addActionListener(actionListener);
    }
    
}