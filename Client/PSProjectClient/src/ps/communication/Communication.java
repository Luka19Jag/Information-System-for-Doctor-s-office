/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.communication;

import java.net.Socket;
import java.util.Date;
import java.util.List;
import ps.communation.Operation;
import ps.communation.Receiver;
import ps.communation.Request;
import ps.communation.Response;
import ps.communation.Sender;
import ps.domain.Doktor;
import ps.domain.Pacijent;
import ps.domain.Pregled;
import ps.domain.StavkaPregleda;
import ps.domain.Termin;
import ps.domain.Usluga;

/**
 *
 * @author Zbook G3
 */
public class Communication {
    
    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;
    private static Communication instance;
    
    private Communication() throws Exception{
        socket = new Socket("localhost", 10000);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }
    
    public static Communication getInstance() throws Exception{
        if(instance ==null){
            instance = new Communication();
        }
        return instance;
    }

    public Doktor login(String email, String password) throws Exception{
        Doktor doktor = new Doktor();
        doktor.setEmail(email);
        doktor.setPassword(password);
        Request request = new Request(Operation.LOGIN, doktor);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException() == null){
            return (Doktor)response.getResult();
        } else {
            throw response.getException();
        }
    }

    public Doktor register(String name, String lastName, String email, String password, Date dateOfBirth, String address) throws Exception {
        Doktor doktor = new Doktor(name, lastName, email, password, dateOfBirth, address);
        Request request = new Request(Operation.REGISTER, doktor);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (Doktor)response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void logOut(Object param) throws Exception {
        Request request = new Request(Operation.LOGOUT, param);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
             
        } else {
            throw response.getException();
        }
    }

    public void addPatient(Pacijent patient) throws Exception {
        Request request = new Request(Operation.ADD_PATIENT, patient);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }

    public void deletePatient(Pacijent patient) throws Exception {
        Request request = new Request(Operation.DELETE_PATIENT, patient);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }

    public void editPatient(Pacijent patient) throws Exception {
        Request request = new Request(Operation.EDIT_PATIENT, patient);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }

    public List<Pacijent> getAllPatients(Pacijent patient) throws Exception {
        Request request = new Request(Operation.GET_ALL_PATIENTS, patient);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (List<Pacijent>)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public List<Doktor> getAllDoctors(Doktor condition) throws Exception{
        Request request = new Request(Operation.GET_ALL_DOCTORS, condition);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (List<Doktor>)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public List<Termin> getAllTermins(Termin condition) throws Exception{
        Request request = new Request(Operation.GET_ALL_TERMINS, condition);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (List<Termin>)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public List<Pregled> getAllExaminations(Pregled condition) throws Exception{
        Request request = new Request(Operation.GET_ALL_EXAMINATIONS, condition);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (List<Pregled>)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    /*public List<Pregled> getAllExaminations(String searchValue, Pregled condition) throws Exception{
        Request request = new Request(Operation.GET_ALL_EXAMINATIONS_CRITERION, searchValue);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
            return (List<Pregled>) response.getResult();
        }else{
            throw response.getException();
        }
    }*/
    
    public List<Usluga> getAllServices(Usluga condition) throws Exception{
        Request request = new Request(Operation.GET_ALL_SERVICES, condition);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (List<Usluga>)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public List<StavkaPregleda> getAllExaminationItems(StavkaPregleda condition) throws Exception {
        Request request = new Request(Operation.GET_ALL_EXAMINATION_ITEMS, condition);
        sender.send(request);
            Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (List<StavkaPregleda>)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public void addTermin(Termin termin) throws Exception {
        Request request = new Request(Operation.ADD_TERMIN, termin);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }
    
    public void editTermin(Termin termin) throws Exception {
        Request request = new Request(Operation.EDIT_TERMIN, termin);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }
    
     public void deleteTermin(Termin termin) throws Exception{
        Request request = new Request(Operation.DELETE_TERMIN, termin);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }
    
    public void addExamination(Pregled medicalExamination) throws Exception {
        Request request = new Request(Operation.ADD_EXAMINATION, medicalExamination);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            Pregled newMedicalExamination = (Pregled)response.getResult();
            medicalExamination.setId(newMedicalExamination.getId());
        } else {
            throw response.getException();
        }
    }
    
    public void editExamination(Pregled medicalExamination) throws Exception {
        Request request = new Request(Operation.EDIT_EXAMINATION, medicalExamination);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            Pregled newMedicalExamination = (Pregled)response.getResult();
            newMedicalExamination.setId(newMedicalExamination.getId());//TO CHECK
        } else {
            throw response.getException();
        }
    }
    
    public void deleteExamination(Pregled medicalExamination) throws Exception {
        Request request = new Request(Operation.DELETE_EXAMINATION, medicalExamination);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            Pregled newMedicalExamination = (Pregled)response.getResult();
            newMedicalExamination.setId(newMedicalExamination.getId());//TO CHECK
        } else {
            throw response.getException();
        }
    }

    public void editDoctor(Doktor doctorMain) throws Exception {
        Request request = new Request(Operation.EDIT_DOCTOR, doctorMain);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            Doktor newDoktor = (Doktor)response.getResult();
            //newDoktor.setId(newDoktor.getId());//TO CHECK
        } else {
            throw response.getException();
        }
    }
    
}
