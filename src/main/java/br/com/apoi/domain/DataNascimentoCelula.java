package br.com.apoi.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;

import br.com.apoi.context.Celula;
import br.com.apoi.context.Linha;
import br.com.apoi.enums.EnumValidations;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataNascimentoCelula implements Celula {

    private Cell cell;
    private String valor;
    private boolean error;
    private String message;

    @Override
    public int index() {
        return 4;
    }

    @Override
    public boolean validate(Linha linha) {
        if (valor.length() == 0) {
            this.message = EnumValidations.MESSAGE_DATA_NASCIMENTO_REQUIRED.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        
        try {
            df.parse(valor);
        } catch (Exception e) {
            this.message = EnumValidations.MESSAGE_DATA_NASCIMENTO_INVALID.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }

        return true;

    }

    @Override
    public Celula copy(String string) {
        DataNascimentoCelula nomeCelula = new DataNascimentoCelula();
        nomeCelula.setValor(string);
        return nomeCelula;
    }

}
