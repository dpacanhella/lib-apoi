package br.com.apoi.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tabela {

    private XSSFWorkbook workbook;
    private XSSFSheet content;
    private List<Linha> linhas = new ArrayList<>();
    private Row currentRow;

    public Tabela(InputStream inputStream, Linha templateLinha) {
        try {
            workbook = new XSSFWorkbook(inputStream);
            content = workbook.getSheetAt(0);
            Iterator<Row> iterator = content.iterator();
            iterator.next();

            while (iterator.hasNext()) {
                currentRow = iterator.next();

                linhas.add(Linha.createNewLine(currentRow, templateLinha));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void validateFields() {
        CellStyle styleYellow = workbook.createCellStyle();
        styleYellow.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        styleYellow.setFillPattern(CellStyle.ALIGN_CENTER);

        for (Linha linha : linhas) {
            for (Celula celula : linha.getCelulas()) {
                boolean validate = celula.validate(linha);
                if (!validate) {
                    CellAdapter.tint(linha.getRow(), styleYellow, celula.index());
                }
            }
            escreverMessageDeErro(linha.getRow(), linha.getErrors());
        }

    }

    private void escreverMessageDeErro(Row row, List<String> errors) {

        short lastCell = row.getLastCellNum();
        Cell cell = row.createCell(lastCell);

        String message = "Detalhes: " + errors.stream().collect(Collectors.joining(" / "));

        if (message.length() != 0) {
            cell.setCellValue(message);
        }
    }

}
