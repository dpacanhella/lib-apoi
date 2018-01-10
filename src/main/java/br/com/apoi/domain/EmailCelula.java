package br.com.apoi.domain;

import org.apache.poi.ss.usermodel.Cell;

import br.com.apoi.context.Celula;
import br.com.apoi.context.Linha;
import br.com.apoi.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCelula implements Celula{

    private final static String MESSAGE_EMAIL_REQUIRED = "Email não preenchido";
    private final static String MESSAGE_EMAIL_INVALID = "Email inválido";

    private Cell cell;
    private String valor;
    private boolean error;
    private String message;

    @Override
    public int index() {
        return 3;
    }

    @Override
    public boolean validate(Linha linha) {
        if (valor.length() == 0) {
            this.message = MESSAGE_EMAIL_REQUIRED;
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        
        if (!ValidationUtils.validEmail(valor) && valor.length() != 0) {
            this.message = MESSAGE_EMAIL_INVALID;
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        return true;
    }

    @Override
    public Celula copy(String string) {
        EmailCelula nomeCelula = new EmailCelula();
        nomeCelula.setValor(string);
        return nomeCelula;
    }
    
    
}
