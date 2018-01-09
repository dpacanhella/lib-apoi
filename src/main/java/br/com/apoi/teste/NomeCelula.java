package br.com.apoi.teste;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;

@Getter
@Setter
public class NomeCelula implements Celula{


    private Cell cell;
    private String valor;
    private boolean error;
    private String message;

//
//    public NomeCelula(Linha linha){
//
//    }

    @Override
    public int index() {
        return 1;
    }

//    public String getContent(){
//        DataFormatter formatter = new DataFormatter();
//        formatter.formatCellValue(currentRow.getCell(get));
//    }

    @Override
    public boolean validate(Linha linha) {
        if (valor.length() == 0) {
            this.message = "Nome não preenchido";
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
        NomeCelula nomeCelula = new NomeCelula();
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
