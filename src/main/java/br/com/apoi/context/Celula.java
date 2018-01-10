package br.com.apoi.context;

public interface Celula {

    int index();
    boolean validate(Linha linha);
    void setValor(String valor);
    String getMessage();
    Celula copy(String string);
}
