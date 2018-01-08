//package br.com.apoi.domain;
//
//import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.ss.usermodel.Row;
//
//import lombok.Data;
//
//@Data
//public class UsuarioValidator {
//    
//    DataFormatter formatter = new DataFormatter();
//    private RowValues value;
//    
//    public UsuarioValidator(RowValues value){
//           this.value = value;
//    }
//    
//    public boolean hasNameRequired(){
//        return true;
//    }
//    
//    public String validateName() throws Exception{
//        if(this.hasNameRequired() && value.getNome().length() > 0){
//            throw new Exception();
//        }else{
//            return this.getValue().getNome();
//        }
//    }
//    
//}
