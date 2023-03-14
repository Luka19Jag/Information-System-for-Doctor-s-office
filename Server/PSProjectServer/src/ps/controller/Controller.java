/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.controller;

import java.util.List;
import ps.domain.Doktor;
import ps.domain.Pacijent;
import ps.domain.Pregled;
import ps.domain.StavkaPregleda;
import ps.domain.Termin;
import ps.domain.Usluga;
import ps.operation.AbstractGenericOperation;
import ps.operation.doctor.AddDoctor;
import ps.operation.doctor.EditDoctor;
import ps.operation.doctor.GetAllDoctors;
import ps.operation.doctor.GetDoctor;
import ps.operation.examination.AddExamination;
import ps.operation.examination.DeleteExamination;
import ps.operation.examination.EditExamination;
import ps.operation.examination.GetAllExaminations;
import ps.operation.examinationItem.DeleteItem;
import ps.operation.examinationItem.GetAllItems;
import ps.operation.patient.AddPatient;
import ps.operation.patient.DeletePatient;
import ps.operation.patient.EditPatient;
import ps.operation.patient.GetAllPatients;
import ps.operation.service.GetAllServices;
import ps.operation.termin.AddTermin;
import ps.operation.termin.DeleteTermin;
import ps.operation.termin.EditTermin;
import ps.operation.termin.GetAllTermins;

/**
 *
 * @author Zbook G3
 */
public class Controller {
    
    private static Controller instance;
    
    private Controller() {
        
    }
    
    public static Controller getInstance(){
        if(instance==null)
            instance = new Controller();
        return instance;
    }
    
    public void login(Doktor doctor) throws Exception {
        AbstractGenericOperation operation = new GetDoctor();
        operation.execute(doctor);
    }
    
    public void register(Doktor doctor) throws Exception {        
        AbstractGenericOperation operation = new AddDoctor();
        operation.execute(doctor);
        
    }
    
    public void getAllDoctors(List<Doktor> doctors) throws Exception {
        AbstractGenericOperation operation = new GetAllDoctors();
        operation.execute(doctors);
    }
    
    public void getAllPatient(List<Pacijent> patients) throws Exception {
        AbstractGenericOperation operation = new GetAllPatients();
        operation.execute(patients);
    }

    public void getAllExaminations(List<Pregled> examinations) throws Exception {
        AbstractGenericOperation operation = new GetAllExaminations();
        operation.execute(examinations);
    }

    public void getAllExaminationsItems(List<StavkaPregleda> items) throws Exception {
        AbstractGenericOperation operation = new GetAllItems();
        operation.execute(items);
    }

    public void getAllTermins(List<Termin> termins) throws Exception {
        AbstractGenericOperation operation = new GetAllTermins();
        operation.execute(termins);
    }

    public void getAllServices(List<Usluga> services) throws Exception {
        AbstractGenericOperation operation = new GetAllServices();
        operation.execute(services);
    }
    
    public void addPatient(Pacijent patientAdd) throws Exception {
        AbstractGenericOperation operation = new AddPatient();
        operation.execute(patientAdd);
    }

    public void editPatient(Pacijent patientEdit) throws Exception {
        AbstractGenericOperation operation = new EditPatient();
        operation.execute(patientEdit);
    }

    public void addTermin(Termin terminAdd) throws Exception {
        AbstractGenericOperation operation = new AddTermin();
        operation.execute(terminAdd);
    }

    public void addExamination(Pregled examinationAdd) throws Exception {
        AbstractGenericOperation operation = new AddExamination();
        operation.execute(examinationAdd);
    }

    public void editExamination(Pregled examinationEdit) throws Exception {
        AbstractGenericOperation operation = new EditExamination();
        operation.execute(examinationEdit);
    }

    public void deleteExamination(Pregled examinationDelete) throws Exception {
        AbstractGenericOperation operation = new DeleteExamination();
        operation.execute(examinationDelete);
        //for (StavkaPregleda item : examinationDelete.getListaStavkePregleda()) {
          //  AbstractGenericOperation operation2 = new DeleteItem();
            //operation2.execute(item);
        //}
    }

    public void deletePatient(Pacijent patientDelete) throws Exception {
        AbstractGenericOperation operation = new DeletePatient();
        operation.execute(patientDelete);
    }
    
    public void deleteTermin(Termin terminDelate) throws Exception {
        AbstractGenericOperation operation = new DeleteTermin();
        operation.execute(terminDelate);
    }

    public void editTermin(Termin terminEdit) throws Exception {
        AbstractGenericOperation operation = new EditTermin();
        operation.execute(terminEdit);
    }

    public void editDoctor(Doktor doctorEdit) throws Exception {
        AbstractGenericOperation operation = new EditDoctor();
        operation.execute(doctorEdit);
    }
 
}
