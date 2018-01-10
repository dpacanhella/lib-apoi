package br.com.apoi.domain;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import br.com.apoi.context.Celula;
import br.com.apoi.context.Linha;
import br.com.caelum.stella.validation.CPFValidator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CpfCelula implements Celula{

    private final static String MESSAGE_CPF_REQUIRED = "CPF não preenchido";
    private final static String MESSAGE_CPF_INVALID = "CPF inválido";

    private Cell cell;
    private String valor;
    private boolean error;
    private String message;;

    @Override
    public int index() {
        return 2;
    }

    @Override
    public boolean validate(Linha linha) {
        if (valor.length() == 0) {
            this.message = MESSAGE_CPF_REQUIRED;
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        
        if (!validCPF(valor) && valor.length() != 0) {
            this.message = MESSAGE_CPF_INVALID;
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        return true;
    }

    @Override
    public String extrairValor(Row currentRow) {
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(currentRow.getCell(index()));
    }

    @Override
    public Celula copy(String string) {
        CpfCelula nomeCelula = new CpfCelula();
        nomeCelula.setValor(string);
        return nomeCelula;
    }

    //TODO esse cara deve conhecer só o CELL nao o currentRow
    @Override
    public void tint(Row currentRow, CellStyle styleYellow) {
        Cell cell = currentRow.getCell(index());
        cell.setCellStyle(styleYellow);
    }
    
    private boolean validCPF(String cpfString) {
        CPFValidator cpfValidator = new CPFValidator();
        return cpfValidator.isEligible(cpfString);
    }
}
