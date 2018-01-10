package br.com.apoi.context;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Linha {

    private Row row;
    private List<Celula> celulas = new ArrayList<>();
    private boolean hasError = false;
    private List<String> errors = new ArrayList<String>();

    public void validateFields(){
        for (Celula celula : celulas) {
            celula.validate(this);
        }
    }

    public static Linha createNewLine(Row currentRow, Linha template){
        
        Linha linha = new Linha();
        for (Celula celula : template.getCelulas()) {
            String valor = CellAdapter.getValor(currentRow, celula.index());
            Celula novaCelula = celula.copy(valor);
            linha.row = currentRow;
            linha.getCelulas().add(novaCelula);
        }
        return linha;
    }

}
