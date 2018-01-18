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
public class CpfCelula implements Celula{

    private Cell cell;
    private String valor;
    private boolean error;
    private String message;;

    @Override
    public int index() {
        return 6;
    }

    @Override
    public boolean validate(Linha linha) {
        if (valor.length() == 0) {
            this.message = EnumValidations.MESSAGE_CPF_REQUIRED.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        
        if (!ValidationUtils.validCPF(valor) && valor.length() != 0) {
            this.message = EnumValidations.MESSAGE_CPF_INVALID.getText();
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        return true;
    }

    @Override
    public Celula copy(String string) {
        CpfCelula nomeCelula = new CpfCelula();
        nomeCelula.setValor(string);
        return nomeCelula;
    }
    
}
