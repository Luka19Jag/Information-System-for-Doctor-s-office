/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.communation;

import java.io.Serializable;

/**
 *
 * @author Zbook G3
 */
public enum Operation implements Serializable{
    
    REGISTER,
    LOGIN,
    LOGOUT,
    GET_ALL_PATIENTS,
    GET_ALL_DOCTORS,
    GET_ALL_TERMINS,
    GET_ALL_EXAMINATIONS,
    GET_ALL_EXAMINATION_ITEMS,
    GET_ALL_SERVICES,
    ADD_PATIENT,
    DELETE_PATIENT,
    EDIT_PATIENT,
    ADD_TERMIN,
    EDIT_TERMIN,
    DELETE_TERMIN,
    ADD_EXAMINATION,
    EDIT_EXAMINATION,
    DELETE_EXAMINATION,
    GET_ALL_EXAMINATIONS_CRITERION,
    EDIT_DOCTOR
}
