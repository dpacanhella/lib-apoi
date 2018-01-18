package br.com.apoi.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.apoi.context.Celula;
import br.com.apoi.context.Linha;
import br.com.apoi.context.Tabela;
import br.com.apoi.domain.CEPCelula;
import br.com.apoi.domain.CnpjCelula;
import br.com.apoi.domain.CpfCelula;
import br.com.apoi.domain.EmailCelula;
import br.com.apoi.domain.GeneroCelula;
import br.com.apoi.domain.NomeCelula;
import br.com.apoi.domain.StatusCelula;
import br.com.apoi.domain.TelefoneCelula;
import br.com.apoi.domain.DDDCelula;
import br.com.apoi.domain.DataNascimentoCelula;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    private static Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

    private static final String NEW_FILE_NAME = "/Users/infra/Desktop/planilhas/";

    @PostMapping("/validate")
    public String importExcel(@RequestParam(value = "file", required = true) MultipartFile file,
            HttpServletResponse response) throws IOException {

        UUID uuid = UUID.randomUUID();
        String myRandom = uuid.toString().substring(0, 20);

        List<Celula> celulas = new ArrayList<Celula>();
        celulas.add(new CnpjCelula());
        celulas.add(new NomeCelula());
        celulas.add(new GeneroCelula());
        celulas.add(new DataNascimentoCelula());
        celulas.add(new CpfCelula());
        celulas.add(new CEPCelula());
        celulas.add(new EmailCelula());
        celulas.add(new DDDCelula());
        celulas.add(new TelefoneCelula());
        celulas.add(new StatusCelula());

        Linha templateLinha = new Linha();
        templateLinha.getCelulas().addAll(celulas);

        Tabela tabela = new Tabela(file.getInputStream(), templateLinha);
        tabela.validateFields();

        try {
            FileOutputStream fileOut = new FileOutputStream(NEW_FILE_NAME + myRandom + ".xlsx");
            tabela.getWorkbook().write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myRandom;
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
