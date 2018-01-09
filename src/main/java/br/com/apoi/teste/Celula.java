package br.com.apoi.teste;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

public interface Celula {


    int index();
    void tint(Row currentRow, CellStyle styleYellow);
    boolean validate(Linha linha);
    void setValor(String valor);
    String getMessage();

    String extrairValor(Row currentRow);

    Celula copy(String string);
}
