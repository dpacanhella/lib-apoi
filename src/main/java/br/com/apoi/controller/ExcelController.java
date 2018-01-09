package br.com.apoi.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import br.com.apoi.teste.Linha;
import br.com.apoi.teste.NomeCelula;
import br.com.apoi.teste.Tabela;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.apoi.domain.RowValues;
import sun.tools.jconsole.Tab;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    private static Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

    private static final String NEW_FILE_NAME = "/Users/infra/Desktop/planilhas/";

    @PostMapping("/validate")
    public String importExcel(@RequestParam(value = "file", required = true) MultipartFile file,
            HttpServletResponse response) throws IOException {
        Iterator<Row> iterator = null;

        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;

        UUID uuid = UUID.randomUUID();
        String myRandom = uuid.toString().substring(0, 20);

        try {

            workbook = new XSSFWorkbook(file.getInputStream());
            sheet = workbook.getSheetAt(0);

            iterator = sheet.iterator();
            iterator.next();

        } catch (Exception e) {
            e.printStackTrace();
        }




        Linha templateLinha = new Linha();
        templateLinha.getCelulas().add(new NomeCelula());
//        templateLinha.getCelulas().add(new CpfCelula());

        Tabela tabela = new Tabela(file.getInputStream(), templateLinha);

        while (iterator.hasNext()) {

            Row currentRow = iterator.next();

            try {
                RowValues row = new RowValues(currentRow);

                if (row.getInvalid()) {
                    
                    // Aplicando estilos vermelhos para campos inválidos
                    applyingStyle(workbook, currentRow, row);

                } 
                
                // Inserindo mensagem de campo inválido ou obrigat
                insertMessageInCell(currentRow, row);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        try {
            FileOutputStream fileOut = new FileOutputStream(NEW_FILE_NAME + myRandom + ".xlsx");
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myRandom;
    }

    private void insertMessageInCell(Row currentRow, RowValues row) {
        // Pegando a última coluna da linha
        short lastCellNum = currentRow.getLastCellNum();
        Cell cell = currentRow.createCell(lastCellNum);

        // Mensagem de erro
        String message = "Detalhes: " + row.getMessage();
        if (row.getMessage().length() != 0) {
            cell.setCellValue(message);
        }
    }

    private void applyingStyle(XSSFWorkbook workbook, Row currentRow, RowValues row) {
        CellStyle styleYellow = workbook.createCellStyle();
        styleYellow.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        styleYellow.setFillPattern(CellStyle.ALIGN_CENTER);

        for (Integer positionCell : row.getPositions()) {
            Cell cell = currentRow.getCell(positionCell);
            cell.setCellStyle(styleYellow);
        }
    }

    @GetMapping("/download/{nomeArquivo}")
    public void downloadFile(@PathVariable(value = "nomeArquivo", required = true) String nomeArquivo,
            HttpServletResponse response) {

        File file = new File(NEW_FILE_NAME + nomeArquivo + ".xlsx");

        try {

            byte[] result = null;
            result = FileUtils.readFileToByteArray(file);

            final InputStream is = new ByteArrayInputStream(result);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            response.getOutputStream().write(IOUtils.toByteArray(is));
            response.getOutputStream().flush();
            is.close();

        } catch (Exception e) {
            LOGGER.error("download arquivo preview", e);
        } finally {
            if (file.exists()) {
                try {
                    FileUtils.forceDelete(file);
                } catch (IOException e) {
                    LOGGER.error("force delete", e);
                }
            }
        }
    }

}
