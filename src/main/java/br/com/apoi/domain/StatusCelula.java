package br.com.apoi.domain;

import org.apache.poi.ss.usermodel.Cell;

import br.com.apoi.context.Celula;
import br.com.apoi.context.Linha;
import br.com.apoi.enums.EnumValidations;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusCelula implements Celula{

    private Cell cell;
    private String valor;
    private boolean error;
    private String message;

    @Override
    public int index() {
        return 14;
    }

    @Override
    public boolean validate(Linha linha) {
        if (valor.length() == 0) {
            this.message = EnumValidations.MESSAGE_STATUS_REQUIRED.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        
        if (Integer.valueOf(valor) < 0 || Integer.valueOf(valor) > 1) {
            this.message = EnumValidations.MESSAGE_STATUS_REQUIRED.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        return true;

    }

    @Override
    public Celula copy(String string) {
        StatusCelula nomeCelula = new StatusCelula();
        nomeCelula.setValor(string);
        return nomeCelula;
    }

}
