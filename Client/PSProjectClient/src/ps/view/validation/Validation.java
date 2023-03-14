/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.view.validation;

import ps.view.form.FrmNewPatient;
import ps.view.util.FormMode;

/**
 *
 * @author Zbook G3
 */
public interface Validation {
    public void validate(FrmNewPatient forma, String pattern, String variable);
    public void formModeSetter(FrmNewPatient forma, FormMode formMode);
}
