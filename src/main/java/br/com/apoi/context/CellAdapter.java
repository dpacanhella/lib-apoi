package br.com.apoi.context;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import lombok.Data;

@Data
public abstract class CellAdapter {
   
    public static String getValor(Row currentRow, int index) {
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(currentRow.getCell(index));
    }

    public static void tint(Row currentRow, CellStyle styleYellow, int index) {
        Cell cell = currentRow.getCell(index);
        cell.setCellStyle(styleYellow);
    }

}
