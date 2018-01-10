package br.com.apoi.context;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

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

    public static Linha criarNovaLinah(Row currentRow, Linha template){
        Linha linha = new Linha();
        for (Celula celula : template.getCelulas()) {
            Celula novaCelula = celula.copy(celula.extrairValor(currentRow));
            linha.row = currentRow;
            linha.getCelulas().add(novaCelula);
        }
        return linha;
    }

}
