package br.com.apoi.domain;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import br.com.apoi.context.Celula;
import br.com.apoi.context.Linha;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelefoneCelula implements Celula{

    private final static String MESSAGE_TEL_REQUIRED = "Telefone não preenchido";
    private final static String MESSAGE_TEL_INVALID = "Telefone incorreto";

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
            this.message = MESSAGE_TEL_REQUIRED;
            this.error = true;
            linha.setHasError(true);
            linha.getErrors().add(this.message);
            return false;
        }
        
        if (!(valor.length() == 10 || valor.length() == 11) && valor.length() != 0) {
            this.message = MESSAGE_TEL_INVALID;
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
        TelefoneCelula nomeCelula = new TelefoneCelula();
        nomeCelula.setValor(string);
        return nomeCelula;
    }

    //TODO esse cara deve conhecer só o CELL nao o currentRow
    @Override
    public void tint(Row currentRow, CellStyle styleYellow) {
        Cell cell = currentRow.getCell(index());
        cell.setCellStyle(styleYellow);
    }
    
}
