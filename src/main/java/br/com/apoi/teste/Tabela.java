package br.com.apoi.teste;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
public class Tabela {

    private XSSFWorkbook workbook;
    private XSSFSheet content;
    private List<Linha> linhas = new ArrayList<>();

    public Tabela(InputStream inputStream, Linha templateLinha) {
        try {
            workbook = new XSSFWorkbook(inputStream);
            content = workbook.getSheetAt(0);
            Iterator<Row> iterator = content.iterator();
            iterator.next();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                linhas.add(Linha.criarNovaLinah(currentRow, templateLinha));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void validateFields(){
        CellStyle styleYellow = workbook.createCellStyle();
        styleYellow.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        styleYellow.setFillPattern(CellStyle.ALIGN_CENTER);

        for (Linha linha : linhas) {
            for (Celula celula : linha.getCelulas()) {
                boolean validate = celula.validate(linha);
                if(!validate){
                    celula.tint(linha.getRow(), styleYellow);
                    escreverMessageDeErro(celula.getMessage());
                }
            }

        }

    }

    private void escreverMessageDeErro(String message) {

    }


}
