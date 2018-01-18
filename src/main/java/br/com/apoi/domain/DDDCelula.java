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
public class DDDCelula implements Celula {

    private Cell cell;
    private String valor;
    private boolean error;
    private String message;
    private double parseDouble = 0.0;

    @Override
    public int index() {
        return 12;
    }

    @Override
    public boolean validate(Linha linha) {
        valor = ValidationUtils.removeSpecialCharacters(valor);

        if (valor.length() == 0) {
            this.message = EnumValidations.MESSAGE_DDD_REQUIRED.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        
        if (valor.length() != 2 || valor.length() != 3) {
            this.message = EnumValidations.MESSAGE_DDD_INVALID.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }

        return true;

    }

    @Override
    public Celula copy(String string) {
        DDDCelula nomeCelula = new DDDCelula();
        nomeCelula.setValor(string);
        return nomeCelula;
    }

}
