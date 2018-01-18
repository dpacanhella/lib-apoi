package br.com.apoi.enums;

public enum EnumValidations {

    MESSAGE_CPF_REQUIRED("CPF não preenchido"), 
    MESSAGE_CPF_INVALID("CPF inválido"), 
    MESSAGE_NAME_REQUIRED("Nome não preenchido"), 
    MESSAGE_GENERO_REQUIRED("Gênero não preenchido"),
    MESSAGE_GENERO_INVALID("Gênero inválido"),
    MESSAGE_DATA_NASCIMENTO_REQUIRED("Data de nascimento não preenchido"),
    MESSAGE_DATA_NASCIMENTO_INVALID("Data de nascimento inválida"),
    MESSAGE_RG_INVALID("RG inválido"),
    MESSAGE_CEP_REQUIRED("CEP não preenchido"),
    MESSAGE_CEP_INVALID("CEP inválido"),
    MESSAGE_EMAIL_REQUIRED("EMAIL não preenchido"),
    MESSAGE_EMAIL_INVALID("EMAIL inválido"),
    MESSAGE_DDD_REQUIRED("DDD não preenchido"),
    MESSAGE_DDD_INVALID("DDD inválido"),
    MESSAGE_TEL_REQUIRED("Telefone não preenchido"),
    MESSAGE_TEL_INVALID("Telefone inválido"),
    MESSAGE_CNPJ_REQUIRED("CNPJ não preenchido"),
    MESSAGE_CNPJ_INVALID("CNPJ inválido"),
    MESSAGE_STATUS_REQUIRED("Status não preenchido"),
    ESSAGE_STATUS_INVALID("Status inválido");
    
    String text;
    
    EnumValidations(String t) {
        text = t;
    }
    
    public String getText() {
        return this.text;
    }
}