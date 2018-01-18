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
public class CnpjCelula implements Celula{

    private Cell cell;
    private String valor;
    private boolean error;
    private String message;

    @Override
    public int index() {
        return 1;
    }

    @Override
    public boolean validate(Linha linha) {
        if (valor.length() == 0) {
            this.message = EnumValidations.MESSAGE_CNPJ_REQUIRED.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        } else {
            valor = ValidationUtils.removeSpecialCharacters(valor);
        }
        
        if (!ValidationUtils.validCNPJ(valor) && valor.length() != 0) {
            this.message = EnumValidations.MESSAGE_CNPJ_INVALID.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        return true;
    }

    @Override
    public Celula copy(String string) {
        CnpjCelula nomeCelula = new CnpjCelula();
        nomeCelula.setValor(string);
        return nomeCelula;
    }
    
}
