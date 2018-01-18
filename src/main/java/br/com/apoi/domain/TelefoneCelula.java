package br.com.apoi.domain;

import org.apache.poi.ss.usermodel.Cell;

import br.com.apoi.context.Celula;
import br.com.apoi.context.Linha;
import br.com.apoi.enums.EnumValidations;
import br.com.apoi.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelefoneCelula implements Celula{

    private Cell cell;
    private String valor;
    private boolean error;
    private String message;

    @Override
    public int index() {
        return 13;
    }

    @Override
    public boolean validate(Linha linha) {
        if (valor.length() == 0) {
            this.message = EnumValidations.MESSAGE_TEL_REQUIRED.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        } else {
            valor = ValidationUtils.removeSpecialCharacters(valor);
        }
        
        if (!(valor.length() == 8 || valor.length() == 9) && valor.length() != 0) {
            this.message = EnumValidations.MESSAGE_TEL_INVALID.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        return true;
    }

    @Override
    public Celula copy(String string) {
        TelefoneCelula nomeCelula = new TelefoneCelula();
        nomeCelula.setValor(string);
        return nomeCelula;
    }
    
}
