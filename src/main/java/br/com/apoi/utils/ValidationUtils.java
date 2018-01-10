package br.com.apoi.utils;

import org.apache.commons.validator.routines.EmailValidator;

import br.com.caelum.stella.validation.CPFValidator;

public class ValidationUtils {

    public static boolean validEmail(String emailString) {
        return EmailValidator.getInstance().isValid(emailString);
    }
    
    public static boolean validCPF(String cpfString) {
        CPFValidator cpfValidator = new CPFValidator();
        return cpfValidator.isEligible(cpfString);
    }
}
