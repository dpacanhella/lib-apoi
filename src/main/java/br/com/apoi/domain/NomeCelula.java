package br.com.apoi.domain;

import org.apache.poi.ss.usermodel.Cell;

import br.com.apoi.context.Celula;
import br.com.apoi.context.Linha;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NomeCelula implements Celula{

    private final static String MESSAGE_NAME_REQUIRED = "Nome não preenchido";

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
            this.message = MESSAGE_NAME_REQUIRED;
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        return true;

    }

    @Override
    public Celula copy(String string) {
        NomeCelula nomeCelula = new NomeCelula();
        nomeCelula.setValor(string);
        return nomeCelula;
    }

}
