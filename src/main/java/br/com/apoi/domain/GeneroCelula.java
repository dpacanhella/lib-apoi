package br.com.apoi.domain;

import org.apache.poi.ss.usermodel.Cell;

import br.com.apoi.context.Celula;
import br.com.apoi.context.Linha;
import br.com.apoi.enums.EnumValidations;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneroCelula implements Celula{
    
    private static final String MASCULINO = "M";
    private static final String FEMININO = "F";
    private static final String NEUTRO = "N";

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
            this.message = EnumValidations.MESSAGE_GENERO_REQUIRED.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        
        if (!valor.equals(MASCULINO) || !valor.equals(FEMININO) || !valor.equals(NEUTRO)) {
            this.message = EnumValidations.MESSAGE_GENERO_INVALID.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        
        return true;

    }

    @Override
    public Celula copy(String string) {
        GeneroCelula nomeCelula = new GeneroCelula();
        nomeCelula.setValor(string);
        return nomeCelula;
    }

}
