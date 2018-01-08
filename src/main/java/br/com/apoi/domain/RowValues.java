package br.com.apoi.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import br.com.caelum.stella.validation.CPFValidator;
import lombok.Data;

@Data
public class RowValues {

    DataFormatter formatter = new DataFormatter();
    private final static int POSITION_VALUE = 5;
    private final static int POSITION_CPF = 2;
    private final static int POSITION_TEL = 4;
    private final static int POSITION_EMAIL = 3;
    private final static int POSITION_NAME = 1;
    private final static int POSITION_ID = 0;

    private final static String MESSAGE_TEL_REQUIRED = "Telefone obrigatório";
    private final static String MESSAGE_TEL_INVALID = "Telefone incorreto";
    private final static String MESSAGE_NAME_REQUIRED = "Nome obrigatório";
    private final static String MESSAGE_CPF_REQUIRED = "CPF obrigatório";
    private final static String MESSAGE_CPF_INVALID = "CPF inválido";
    private final static String MESSAGE_EMAIL_REQUIRED = "Email obrigatório";
    private final static String MESSAGE_EMAIL_INVALID = "Email inválido";
    private final static String MESSAGE_VALUE_REQUIRED = "Valor obrigatório";
    private final static String MESSAGE_VALUE_INVALID = "Valor inválido";

    private String id;
    private String name;
    private String cpf;
    private String email;
    private String tel;
    private String value;
    private Boolean invalid = false;
    private List<Integer> positions = new ArrayList<Integer>();
    private String message;
    private List<String> messages = new ArrayList<String>();

    public RowValues(Row currentRow) {
        this.id = getId(currentRow);
        this.name = getName(currentRow);
        this.cpf = getCPF(currentRow);
        this.email = getEmail(currentRow);
        this.tel = getTel(currentRow);
        this.value = getValue(currentRow);
        this.positions = getPositions();

        // VALIDAÇÃO PARA NOME
        validationName();

        // VALIDAÇÃO PARA CPF
        validationCPF();

        // VALIDAÇÃO PARA EMAIL
        validationEmail();

        // VALIDAÇÃO PARA TELEFONE/CELULAR
        validationTel();

        // VALIDAÇÃO PARA VALOR
        validationValue();

        message = messages.stream().collect(Collectors.joining(" / "));

    }

    private void validationValue() {
        if (value.length() == 0) {
            addMessageAndPositionWhenInvalid(MESSAGE_VALUE_REQUIRED, POSITION_VALUE);
        }

        if (!validValue(value) && value.length() != 0) {
            addMessageAndPositionWhenInvalid(MESSAGE_VALUE_INVALID, POSITION_VALUE);
        }
    }

    private boolean validValue(String value) {
        double parseDouble = 0.0;

        try {
            parseDouble = Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validationEmail() {
        if (email.length() == 0) {
            addMessageAndPositionWhenInvalid(MESSAGE_EMAIL_REQUIRED, POSITION_EMAIL);
        }

        if (!validEmail(email) && email.length() != 0) {
            addMessageAndPositionWhenInvalid(MESSAGE_EMAIL_INVALID, POSITION_EMAIL);
        }

    }

    private void validationCPF() {

        if (cpf.length() == 0) {
            addMessageAndPositionWhenInvalid(MESSAGE_CPF_REQUIRED, POSITION_CPF);
        }

        if (!validCPF(cpf) && cpf.length() != 0) {
            addMessageAndPositionWhenInvalid(MESSAGE_CPF_INVALID, POSITION_CPF);
        }

    }

    private void validationName() {
        if (name.length() == 0) {
            addMessageAndPositionWhenInvalid(MESSAGE_NAME_REQUIRED, POSITION_NAME);
        }

    }

    private void validationTel() {
        if (!(tel.length() == 10 || tel.length() == 11) && tel.length() != 0) {
            addMessageAndPositionWhenInvalid(MESSAGE_TEL_INVALID, POSITION_TEL);
        }

        if (tel.length() == 0) {
            addMessageAndPositionWhenInvalid(MESSAGE_TEL_REQUIRED, POSITION_TEL);
        }
    }

    public void addMessageAndPositionWhenInvalid(String message, int position) {
        messages.add(message);
        positions.add(position);
        invalid = true;
    }

    private boolean validCPF(String cpfString) {
        CPFValidator cpfValidator = new CPFValidator();
        return cpfValidator.isEligible(cpfString);
    }

    private boolean validEmail(String emailString) {
        return EmailValidator.getInstance().isValid(emailString);
    }

    private String getValue(Row currentRow) {
        return formatter.formatCellValue(currentRow.getCell(POSITION_VALUE));
    }

    private String getCPF(Row currentRow) {
        return formatter.formatCellValue(currentRow.getCell(POSITION_CPF));
    }

    private String getTel(Row currentRow) {
        return formatter.formatCellValue(currentRow.getCell(POSITION_TEL));
    }

    private String getEmail(Row currentRow) {
        return formatter.formatCellValue(currentRow.getCell(POSITION_EMAIL));
    }

    private String getName(Row currentRow) {
        return formatter.formatCellValue(currentRow.getCell(POSITION_NAME));
    }

    private String getId(Row currentRow) {
        return formatter.formatCellValue(currentRow.getCell(POSITION_ID));
    }
}
