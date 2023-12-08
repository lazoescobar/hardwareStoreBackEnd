package com.hardwareStore.backEnd.moduleSystem.services.util;

import java.util.ArrayList;
import java.util.List;

public class OutBaseService {
    public Long contError = 0L;
    public List<String> messageError = new ArrayList<>();

    public void addContError (Long contErr){
        contError += contErr;
    }

    /*TEST*/
    public static void main (String [] args){
        OutBaseService out = new OutBaseService();
        Long contError = 1L;
        String meesage = "Error";

        out.addContError(contError);
        out.messageError.add(meesage);

        System.out.println(out.toString());
    }
}



