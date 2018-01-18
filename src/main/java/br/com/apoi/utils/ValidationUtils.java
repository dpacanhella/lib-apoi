package br.com.apoi.utils;

import org.apache.commons.validator.routines.EmailValidator;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;

public class ValidationUtils {

    public static boolean validEmail(String emailString) {
        return EmailValidator.getInstance().isValid(emailString);
    }
    
    public static boolean validCPF(String cpfString) {
        CPFValidator cpfValidator = new CPFValidator();
        return cpfValidator.isEligible(cpfString);
    }
    
    public static boolean validCNPJ(String cnpjString) {
        CNPJValidator cnpjValidator = new CNPJValidator();
        return cnpjValidator.isEligible(cnpjString);
    }
    
    public static String removeSpecialCharacters(String valor) {
        return valor.replaceAll("[^0-9]", "");
    }

}
